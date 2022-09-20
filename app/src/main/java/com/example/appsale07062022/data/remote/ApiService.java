package com.example.appsale07062022.data.remote;

import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.CartDTO;
import com.example.appsale07062022.data.remote.dto.ProductDTO;
import com.example.appsale07062022.data.remote.dto.UserDTO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pphat on 9/13/2022.
 */
public interface ApiService {

    @POST("user/sign-in")
    Call<AppResponse<UserDTO>> signIn(@Body HashMap<String, Object> body);

    @POST("user/sign-up")
    Call<AppResponse<UserDTO>> signUp(@Body HashMap<String, Object> body);

    @GET("product")
    Call<AppResponse<List<ProductDTO>>> getProducts();

    @GET("cart")
    Call<AppResponse<CartDTO>> getCart();
}
