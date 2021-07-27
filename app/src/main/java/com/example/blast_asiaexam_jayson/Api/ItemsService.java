package com.example.blast_asiaexam_jayson.Api;

import com.example.blast_asiaexam_jayson.Model.Items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ItemsService {

    public interface ItemsListService {
        @GET("/v2/list")
        Call<List<Items>> itemsListPaging(
                @Query("page") int page,
                @Query("limit") int limit
        );
    }
}
