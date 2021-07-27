package com.example.blast_asiaexam_jayson.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.blast_asiaexam_jayson.Model.Items;
import com.example.blast_asiaexam_jayson.R;
import com.example.blast_asiaexam_jayson.Utils;

import okhttp3.internal.Util;

public class DetailsFragment extends Fragment {

    private View rootView;
    private ImageView img, expand;
    private TextView txtAuthorName, txtUrl, txtDownloadUrl;
    private LinearLayout info;
    private Items items = new Items();

    public static Fragment newInstance(Items items){
        DetailsFragment fragment = new DetailsFragment();
        fragment.items = items;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.description_fragment, container, false);
            img = rootView.findViewById(R.id.img);
            txtAuthorName = rootView.findViewById(R.id.txtAuthorName);
            expand = rootView.findViewById(R.id.expand);
            txtUrl = rootView.findViewById(R.id.txtUrl);
            txtDownloadUrl = rootView.findViewById(R.id.txtdownloadUrl);
            info = rootView.findViewById(R.id.info);
        }
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtAuthorName.setText(items.getAuthor());
        txtUrl.setText(items.getUrl());
        txtDownloadUrl.setText(items.getDownload_url());
        Glide.with(getContext())
                .load(items.getDownload_url())
                .placeholder(R.drawable.placeholder)
                .into(img);

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info.getVisibility() == View.VISIBLE){
                    info.setVisibility(View.INVISIBLE);
                }else{
                    info.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
