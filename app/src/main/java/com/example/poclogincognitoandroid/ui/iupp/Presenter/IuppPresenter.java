package com.example.poclogincognitoandroid.ui.iupp.Presenter;

import com.example.poclogincognitoandroid.ui.iupp.View.IIuppView;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import core.User;
import network.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IuppPresenter implements  IIuppPresenter {
    IIuppView iuppView;
    AuthService authService;

    public IuppPresenter(IIuppView iuppView, AuthService authService) {
        this.iuppView = iuppView;
        this.authService = authService;
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
                    iuppView.onUserAuthSuccess(urlRedirect);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Map<String, String>> call, @NotNull Throwable t) {
                iuppView.onUserAuthFailed(t.getMessage());
            }
        });
    }
}
