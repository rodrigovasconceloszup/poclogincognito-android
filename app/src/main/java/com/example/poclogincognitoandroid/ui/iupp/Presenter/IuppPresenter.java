package com.example.poclogincognitoandroid.ui.iupp.Presenter;

import android.content.Context;

import com.example.poclogincognitoandroid.base.presenter.BasePresenter;
import com.example.poclogincognitoandroid.ui.iupp.View.IIuppView;

import network.AuthService;

public class IuppPresenter extends BasePresenter {

    public IuppPresenter(Context context, IIuppView iuppView, AuthService authService) {
        super(context, iuppView);
        this.iBaseView = iuppView;
        this.authService = authService;
    }
}
