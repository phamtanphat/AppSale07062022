package com.example.appsale07062022.data.repository;

import android.content.Context;

import com.example.appsale07062022.data.remote.ApiService;
import com.example.appsale07062022.data.remote.RetrofitClient;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by pphat on 9/15/2022.
 */
public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResponse<UserDTO>> signIn(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return apiService.signIn(map);
    }

    public Call<AppResponse<UserDTO>> signUp(String email, String password, String name, String phone, String address) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("name", name);
        map.put("phone", phone);
        map.put("address", address);
        return apiService.signUp(map);
    }
}
