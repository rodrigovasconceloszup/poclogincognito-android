package com.example.poclogincognitoandroid.network;

import java.util.Map;

import com.example.poclogincognitoandroid.core.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("identity-cognito/api/v1/auth/login")
    Call<Map<String, String>> authUser(@Body User user);
}
