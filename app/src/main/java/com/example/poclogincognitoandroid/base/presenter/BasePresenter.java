package com.example.poclogincognitoandroid.base.presenter;

import android.content.Context;

import com.example.poclogincognitoandroid.base.view.IBaseView;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import com.example.poclogincognitoandroid.core.User;
import com.example.poclogincognitoandroid.core.factory.RetrofitFactory;
import com.example.poclogincognitoandroid.network.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

abstract public class BasePresenter implements IBasePresenter {
    protected IBaseView iBaseView;
    protected AuthService authService;

    public BasePresenter(Context context, IBaseView iBaseView) {
        final Retrofit retrofit = RetrofitFactory.make(context);
        this.authService = retrofit.create(AuthService.class);
        this.iBaseView = iBaseView;
    }

    @Override
    public void authUser() {
        authService.authUser(new User()).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NotNull Call<Map<String, String>> call, @NotNull Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    final Map<String, String> body = response.body();
                    assert body != null;
                    final String urlRedirect = body.get("urlRedirect");
                    iBaseView.onUserAuthSuccess(urlRedirect);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Map<String, String>> call, @NotNull Throwable t) {
                iBaseView.onUserAuthFailed(t.getMessage());
            }
        });
    }
}
