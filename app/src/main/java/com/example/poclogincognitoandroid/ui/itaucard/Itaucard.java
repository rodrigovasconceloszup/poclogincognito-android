package com.example.poclogincognitoandroid.ui.itaucard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

        presenter = new ItaucardPresenter(this, Config.getConfigValue(this, "defaultPoints"));

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        seeMoreText = findViewById(R.id.seeMoreText);
        qtdPointsTv = findViewById(R.id.qtdPointsTv);
        screenLoadingIndicator = findViewById(R.id.screenLoadingIndicator);
        screenLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.iuppSecondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);

        findViewById(R.id.iupp_banner_1).setOnClickListener(v -> navigateTo(IuppActivity.class));
        findViewById(R.id.iupp_banner_2).setOnClickListener(v -> navigateTo(IuppActivity.class));
        seeMoreText.setOnClickListener(v -> navigateTo(IuppActivity.class));
        expandIcon.setOnClickListener(this);
        expandOcultTextView.setOnClickListener(this);

        new LoadingPoints().execute();
    }

    private void navigateTo(Class<?> cls) {
        if (isLoading) return;
        Intent intent = new Intent(this, cls);
        intent.putExtra("points", Config.getConfigValue(Itaucard.this, "defaultPoints"));
        startActivity(intent);
        setResult(Activity.RESULT_OK);
    }

    @Override
    public String onPointsFetch(String points) {
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


    private class LoadingPoints extends AsyncTask<Void, Void, Void> {

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
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            isLoading = false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            presenter.onFetchPoints("00000000000");
            return null;
        }
    }
}