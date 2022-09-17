package com.example.appsale07062022.data.repository;

import android.content.Context;

import com.example.appsale07062022.data.remote.ApiService;
import com.example.appsale07062022.data.remote.RetrofitClient;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by pphat on 9/17/2022.
 */
public class ProductRepository {
    private ApiService apiService;

    public ProductRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResponse<List<ProductDTO>>> getProducts() {
        return apiService.getProducts();
    }

}
