package com.example.appsale07062022.presentation.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale07062022.data.model.AppResource;
import com.example.appsale07062022.data.model.User;
import com.example.appsale07062022.data.remote.dto.AppResponse;
import com.example.appsale07062022.data.remote.dto.UserDTO;
import com.example.appsale07062022.data.repository.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 9/15/2022.
 */
public class SignUpViewModel extends ViewModel {
    private final MutableLiveData<AppResource<User>> resourceUser = new MutableLiveData<>();
    private AuthenticationRepository repository;

    public SignUpViewModel(Context context) {
        repository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<User>> getResourceUser() {
        return resourceUser;
    }

    public void signUp(String email, String password, String name, String phone, String address) {
        resourceUser.setValue(new AppResource.Loading(null));
        repository.signUp(email, password, name, phone, address)
                .enqueue(new Callback<AppResponse<UserDTO>>() {
                    @Override
                    public void onResponse(Call<AppResponse<UserDTO>> call, Response<AppResponse<UserDTO>> response) {
                        if (response.isSuccessful()) {
                            AppResponse<UserDTO> resourceUserDTO = response.body();
                            if (resourceUserDTO != null) {
                                UserDTO userDTO = resourceUserDTO.data;
                                resourceUser.setValue(
                                        new AppResource.Success(
                                                new User(
                                                        userDTO.getEmail(),
                                                        userDTO.getName(),
                                                        userDTO.getPhone(),
                                                        userDTO.getToken())));
                            }
                        } else {
                            if (response.errorBody() != null) {
                                try {
                                    JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                                    resourceUser.setValue(new AppResource.Error(jsonObjectError.getString("message")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AppResponse<UserDTO>> call, Throwable t) {
                        resourceUser.setValue(new AppResource.Error<>(t.getMessage()));
                    }
                });
    }
}
