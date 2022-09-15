package com.example.appsale07062022.data.repository;

import android.content.Context;

import com.example.appsale07062022.data.remote.ApiService;
import com.example.appsale07062022.data.remote.RetrofitClient;
import com.example.appsale07062022.data.remote.dto.AppResource;
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

    public Call<AppResource<UserDTO>> signIn(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return apiService.signIn(map);
    }
}
