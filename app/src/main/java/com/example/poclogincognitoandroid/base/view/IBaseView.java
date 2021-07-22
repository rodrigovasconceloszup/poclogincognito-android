package com.example.poclogincognitoandroid.base.view;

public interface IBaseView {
    void onUserAuthSuccess(String urlRedirect);

    void onUserAuthFailed(String error);
}
