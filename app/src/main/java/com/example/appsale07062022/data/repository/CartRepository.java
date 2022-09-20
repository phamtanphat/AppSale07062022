package com.example.appsale07062022.data.repository;

import android.content.Context;

import com.example.appsale07062022.data.remote.ApiService;
import com.example.appsale07062022.data.remote.RetrofitClient;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.CartDTO;
import com.example.appsale07062022.data.remote.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by pphat on 9/20/2022.
 */
public class CartRepository {
    private ApiService apiService;

    public CartRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResponse<CartDTO>> getCart() {
        return apiService.getCart();
    }

    public Call<AppResponse<CartDTO>> addCart(String idProduct) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id_product", idProduct);
        return apiService.addToCart(hashMap);
    }
}
