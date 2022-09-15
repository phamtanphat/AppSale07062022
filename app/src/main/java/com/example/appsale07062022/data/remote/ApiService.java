package com.example.appsale07062022.data.remote;

import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by pphat on 9/13/2022.
 */
public interface ApiService {

    @POST("user/sign-in")
    Call<AppResponse<UserDTO>> signIn(@Body HashMap<String, Object> body);
}
