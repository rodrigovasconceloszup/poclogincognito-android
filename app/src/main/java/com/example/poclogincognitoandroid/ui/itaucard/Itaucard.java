package com.example.poclogincognitoandroid.ui.itaucard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.ui.itaucard.Presenter.ItaucardPresenter;
import com.example.poclogincognitoandroid.ui.itaucard.View.IItaucardView;
import com.example.poclogincognitoandroid.ui.iupp.IuppActivity;
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

import core.Config;

public class Itaucard extends AppCompatActivity implements IItaucardView, View.OnClickListener {

    ImageView expandIcon;
    TextView expandOcultTextView;
    TextView seeMoreText;
    TextView qtdPointsTv;
    LinearLayout moreTextLayout;
    ProgressBar screenLoadingIndicator;

    boolean isExpanded = false;
    boolean isLoading = false;

    ItaucardPresenter presenter;

    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itaucard);
        presenter = new ItaucardPresenter(this, this, Config.getConfigValue(this, "defaultPoints"));

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        seeMoreText = findViewById(R.id.seeMoreText);
        qtdPointsTv = findViewById(R.id.qtdPointsTv);
        screenLoadingIndicator = findViewById(R.id.screenLoadingIndicator);
        screenLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.iuppSecondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);

        findViewById(R.id.iupp_banner_1).setOnClickListener(v -> {
            handleClickBanner();
        });
        findViewById(R.id.iupp_banner_2).setOnClickListener(v -> {
            handleClickBanner();
        });
        seeMoreText.setOnClickListener(v -> navigateToIuppActivity(false));
        expandIcon.setOnClickListener(this);
        expandOcultTextView.setOnClickListener(this);

        init();
    }

    private void init() {
        if (isLoading) return;
        new AnimationLoading().execute();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.onFetchPoints("00000000000");
            }
        }, 2000);
    }

    private void handleClickBanner() {
        presenter.authUser();
        new AnimationLoading().execute();
    }

    private void navigateToIuppActivity(boolean autoAuth) {
        if (isLoading) return;
        Intent intent = new Intent(this, IuppActivity.class);
        intent.putExtra("autoAuth", autoAuth);
        intent.putExtra("points", Config.getConfigValue(Itaucard.this, "defaultPoints"));
        startActivity(intent);
        setResult(Activity.RESULT_OK);
    }

    @Override
    public String onPointsFetch(String points) {
        Log.i("TESTE", "onPointsFetch");
        finishAnimation();
        return points;
    }

    @Override
    public void onClick(View v) {
        if (isLoading) return;
        isExpanded = !isExpanded;
        expandOcultTextView.setText(isExpanded ? R.string.iupp_ocultar : R.string.iupp_expandir);
        moreTextLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        expandIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), !isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up, null));
    }

    @Override
    public void onUserAuthSuccess(String urlRedirect) {
        finishAnimation();
        Intent intent = new Intent(Itaucard.this, MyWebviewActivity.class);
        intent.putExtra("urlRedirect", urlRedirect);
        intent.putExtra("points", Config.getConfigValue(Itaucard.this, "defaultPoints"));
        startActivity(intent);
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onUserAuthFailed(String error) {

    }

    private void finishAnimation() {
        Log.i("TESTE", "Finish animation");
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        isLoading = false;
    }

    private class AnimationLoading extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading = true;
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}