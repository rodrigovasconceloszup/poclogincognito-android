package com.example.poclogincognitoandroid.core.factory;

import android.content.Context;

import java.util.Objects;

import com.example.poclogincognitoandroid.core.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit make(Context context) {
        return new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(Config.getConfigValue(context, "baseUrl")))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
