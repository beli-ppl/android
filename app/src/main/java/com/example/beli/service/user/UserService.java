package com.example.beli.service.user;

import com.example.beli.data.db.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/user/google")
    Call<UserResponse> googleSignIn(@Body User user);
}
