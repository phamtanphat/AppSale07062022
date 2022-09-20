package com.example.appsale07062022.presentation.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.Cart;
import com.example.appsale07062022.data.model.Product;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.CartDTO;
import com.example.appsale07062022.data.remote.dto.ProductDTO;
import com.example.appsale07062022.data.repository.CartRepository;
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
    private final MutableLiveData<AppResource<Cart>> resourceCart = new MutableLiveData<>();
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public HomeViewModel(Context context) {
        productRepository = new ProductRepository(context);
        cartRepository = new CartRepository(context);
    }

    public LiveData<AppResource<List<Product>>> getResourceListProducts() {
        return resourceListProducts;
    }

    public LiveData<AppResource<Cart>> getResourceCart() {
        return resourceCart;
    }

    public void getCart() {
        resourceCart.setValue(new AppResource.Loading(null));
        cartRepository.getCart()
                .enqueue(new Callback<AppResponse<CartDTO>>() {
                    @Override
                    public void onResponse(Call<AppResponse<CartDTO>> call, Response<AppResponse<CartDTO>> response) {
                        if (response.isSuccessful()) {
                            AppResponse<CartDTO> responseListProducts = response.body();
                            CartDTO cartDTO = responseListProducts.data;
                            List<Product> listProduct = new ArrayList<>();
                            for (ProductDTO e : cartDTO.getProducts()) {
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
                            resourceCart.setValue(
                                    new AppResource.Success<>(new Cart(
                                                cartDTO.getId(),
                                                listProduct,
                                                cartDTO.getIdUser(),
                                                cartDTO.getPrice())
                                    )
                            );
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                resourceCart.setValue(new AppResource.Error<>(message));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResponse<CartDTO>> call, Throwable t) {
                        resourceListProducts.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }

    public void addCart(String idProduct) {
        resourceCart.setValue(new AppResource.Loading(null));
        cartRepository.addCart(idProduct)
                .enqueue(new Callback<AppResponse<CartDTO>>() {
                    @Override
                    public void onResponse(Call<AppResponse<CartDTO>> call, Response<AppResponse<CartDTO>> response) {
                        if (response.isSuccessful()) {
                            AppResponse<CartDTO> responseListProducts = response.body();
                            CartDTO cartDTO = responseListProducts.data;
                            List<Product> listProduct = new ArrayList<>();
                            for (ProductDTO e : cartDTO.getProducts()) {
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
                            resourceCart.setValue(
                                    new AppResource.Success<>(new Cart(
                                            cartDTO.getId(),
                                            listProduct,
                                            cartDTO.getIdUser(),
                                            cartDTO.getPrice())
                                    )
                            );
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                resourceCart.setValue(new AppResource.Error<>(message));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResponse<CartDTO>> call, Throwable t) {
                        resourceListProducts.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }

    public void getProducts() {
        resourceListProducts.setValue(new AppResource.Loading(null));
        productRepository.getProducts()
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
