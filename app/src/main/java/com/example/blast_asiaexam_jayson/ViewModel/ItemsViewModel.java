package com.example.blast_asiaexam_jayson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.blast_asiaexam_jayson.Model.Items;
import com.example.blast_asiaexam_jayson.Repositories.ItemsRepository;

import java.util.List;

public class ItemsViewModel extends AndroidViewModel {

    private ItemsRepository itemsRepository;
    private LiveData<List<Items>> itemsLiveData;
    private LiveData<Boolean> isLoading;

    public ItemsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        itemsRepository = new ItemsRepository(getApplication());
        itemsLiveData = itemsRepository.getItemsLiveData();
        isLoading = itemsRepository.getIsLoading();
    }

    public void itemsPaging(int page, int limit) {
        itemsRepository.itemsPaging(page, limit);
    }

    public LiveData<List<Items>> getItemsLiveData() {
        return itemsLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

}
