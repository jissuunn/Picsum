package com.example.blast_asiaexam_jayson.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.blast_asiaexam_jayson.Adapters.ItemsAdapter;
import com.example.blast_asiaexam_jayson.Model.Items;
import com.example.blast_asiaexam_jayson.R;
import com.example.blast_asiaexam_jayson.Utils;
import com.example.blast_asiaexam_jayson.ViewModel.ItemsViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class MainFragment extends Fragment {

    private View rootView;
    private RecyclerView itemListRecView;
    private SwipeRefreshLayout pullToRefresh;
    private ItemsViewModel itemsViewModel;
    private ItemsAdapter itemsAdapter;
    private int page = 1;
    private final int limit = 10;
    private boolean isRefresh = false;
    private List<Items> itemsList = new ArrayList<>();

    public static Fragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter    = new ItemsAdapter(getActivity());
        itemsViewModel  = new ViewModelProvider(this).get(ItemsViewModel.class);
        itemsViewModel.init();
        itemsViewModel.itemsPaging(1, limit);
        itemsViewModel.getItemsLiveData().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items != null && items.size()!=0) {
                    if(isRefresh){
                        itemsList.clear();
                        isRefresh = false;
                    }
                    if(itemsList.size() == 0){
                        itemsList = items;
                        itemsAdapter.setResults(itemsList);
                        page = 1;
                    }
                    if(itemsList.size() != 0 && itemsList != items){
                        itemsList.addAll(items);
                        itemsAdapter.setResults(itemsList);
                    }
                }
                if(itemsList.size() != 0) {
                    itemListRecView.setVisibility(View.VISIBLE);
                }else{
                    itemListRecView.setVisibility(View.INVISIBLE);
                }
            }
        });
        itemsViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefresh.setRefreshing(false);
                        }
                    }, 1000);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.main_fragment, container, false);
            itemListRecView = rootView.findViewById(R.id.itemList);
            pullToRefresh = rootView.findViewById(R.id.refresh);
            Utils.swipeRefreshStyle(pullToRefresh, getActivity());
        }
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemListRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemListRecView.setAdapter(itemsAdapter);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Utils.isConnectingToInternet(getContext())){
                    isRefresh = true;
                    itemsViewModel.itemsPaging(1, limit);
                }else{
                    pullToRefresh.setRefreshing(false);
                }
            }
        });

        itemListRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == itemsAdapter.getItemCount() - 1) {
                    pullToRefresh.setRefreshing(true);
                    itemsViewModel.itemsPaging(page = page + 1, limit);
                }
            }
        });
    }
}
