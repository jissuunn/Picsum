package com.example.blast_asiaexam_jayson.Repositories;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.blast_asiaexam_jayson.Api.ItemsService;
import com.example.blast_asiaexam_jayson.Database.DbHelper;
import com.example.blast_asiaexam_jayson.Model.Items;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsRepository {

    private static final String API_URL = "https://picsum.photos/";
    private final ItemsService.ItemsListService itemsListService;
    private final MutableLiveData<List<Items>> itemsLiveData;
    private final MutableLiveData<Boolean> isLoading;
    private final Context context;

    public ItemsRepository(Context context){
        this.context = context;
        isLoading = new MutableLiveData<>();
        isLoading.setValue(true);
        itemsLiveData = new MutableLiveData<>();
        itemsListService = new retrofit2.Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ItemsService.ItemsListService.class);

    }

    public void itemsPaging(int page, int limit){
        DbHelper db = new DbHelper(context);
        itemsListService.itemsListPaging(page, limit)
                .enqueue(new Callback<List<Items>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Items>> call, @NonNull Response<List<Items>> response) {
                        if (response.body() != null) {
                            itemsLiveData.postValue(response.body());
                            db.insert_items(response.body());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<Items>> call, @NonNull Throwable t) {
                            List<Items> items;
                            items = db.getData(page, limit);
                            itemsLiveData.postValue(items);
                    }
                });
        isLoading.setValue(false);
    }

    public LiveData<List<Items>> getItemsLiveData() {
        return itemsLiveData;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

}
