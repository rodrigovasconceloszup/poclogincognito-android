package com.example.poclogincognitoandroid.ui.itaucard.Presenter;

import android.content.Context;

import com.example.poclogincognitoandroid.base.presenter.BasePresenter;
import com.example.poclogincognitoandroid.ui.itaucard.View.IItaucardView;

public class ItaucardPresenter extends BasePresenter implements IItaucardPresenter {
    IItaucardView itaucardView;
    String defaultPoints;

    public ItaucardPresenter(Context context, IItaucardView itaucardView, String defaultPoints) {
        super(context, itaucardView);
        this.itaucardView = itaucardView;
        this.defaultPoints = defaultPoints;
    }

    @Override
    public String onFetchPoints(String cpf) {
        return itaucardView.onPointsFetch(defaultPoints);
    }
}
