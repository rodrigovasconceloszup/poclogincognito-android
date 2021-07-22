package com.example.poclogincognitoandroid.ui.itaucard.Presenter;

import com.example.poclogincognitoandroid.ui.itaucard.View.IItaucardView;

public class ItaucardPresenter implements IItaucardPresenter {
    IItaucardView itaucardView;
    String defaultPoints;

    public ItaucardPresenter(IItaucardView itaucardView, String defaultPoints) {
        this.itaucardView = itaucardView;
        this.defaultPoints = defaultPoints;
    }

    @Override
    public String onFetchPoints(String cpf) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itaucardView.onPointsFetch(defaultPoints);
    }
}
