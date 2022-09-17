package com.example.appsale07062022.presentation.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.Product;
import com.example.appsale07062022.data.model.User;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.ProductDTO;
import com.example.appsale07062022.data.repository.ProductRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 9/17/2022.
 */
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<AppResource<List<Product>>> resourceListProducts = new MutableLiveData<>();
    private ProductRepository repository;

    public HomeViewModel(Context context) {
        repository = new ProductRepository(context);
    }

    public LiveData<AppResource<List<Product>>> getResourceListProducts() {
        return resourceListProducts;
    }

    public void getProducts() {
        resourceListProducts.setValue(new AppResource.Loading(null));
        repository.getProducts()
                .enqueue(new Callback<AppResponse<List<ProductDTO>>>() {
                    @Override
                    public void onResponse(Call<AppResponse<List<ProductDTO>>> call, Response<AppResponse<List<ProductDTO>>> response) {
                        if (response.isSuccessful()) {
                            AppResponse<List<ProductDTO>> responseListProducts = response.body();
                            List<ProductDTO> listProductDTO = responseListProducts.data;
                            List<Product> listProduct = new ArrayList<>();
                            for (ProductDTO e : listProductDTO) {
                                listProduct.add(new Product(
                                        e.getId(),
                                        e.getName(),
                                        e.getAddress(),
                                        e.getPrice(),
                                        e.getImg(),
                                        e.getQuantity(),
                                        e.getGallery())
                                );
                            }
                            resourceListProducts.setValue(new AppResource.Success<>(listProduct));
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                resourceListProducts.setValue(new AppResource.Error<>(message));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResponse<List<ProductDTO>>> call, Throwable t) {
                        resourceListProducts.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }
}
