package com.example.poclogincognitoandroid.ui.iupp.View;

public interface IIuppView {
    void onUserAuthSuccess(String urlRedirect);

    void onUserAuthFailed(String error);
}
