package com.example.poclogincognitoandroid.ui.iupp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.ui.iupp.Presenter.IuppPresenter;
import com.example.poclogincognitoandroid.ui.iupp.View.IIuppView;
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

import core.factory.RetrofitFactory;
import network.AuthService;
import retrofit2.Retrofit;

public class IuppActivity extends AppCompatActivity implements IIuppView  {

    ImageView expandIcon;
    TextView expandOcultTextView;
    LinearLayout cardLinearLayout;
    LinearLayout moreTextLayout;
    Button goToIuppBtn;
    ProgressBar goToIuppBtnLoading;

    String points;
    boolean isExpanded = false;

    IuppPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        points = getIntent().getStringExtra("points");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back_small);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#726A64\">" + getString(R.string.iupp) + "</font>"));

        setContentView(R.layout.activity_iupp);
        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        cardLinearLayout = findViewById(R.id.cardLinearLayout);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        goToIuppBtnLoading = findViewById(R.id.goToIuppBtnLoading);

        goToIuppBtnLoading.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.iuppSecondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);

        View.OnClickListener onClick = v -> {
            isExpanded = !isExpanded;
            expandOcultTextView.setText(isExpanded ? R.string.iupp_ocultar : R.string.iupp_expandir);
            moreTextLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            expandIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), !isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up, null));
        };
        expandIcon.setOnClickListener(onClick);
        expandOcultTextView.setOnClickListener(onClick);

        final Retrofit retrofit = RetrofitFactory.make(IuppActivity.this);
        AuthService authService = retrofit.create(AuthService.class);
        presenter = new IuppPresenter(this, authService);

        goToIuppBtn = (Button) findViewById(R.id.goToIuppBtn);
        goToIuppBtn.setOnClickListener(v -> {
            setIsLoading(true);
            presenter.authUser();
        });
    }

    void setIsLoading(boolean isLoading) {
        goToIuppBtn.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        goToIuppBtnLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserAuthSuccess(String urlRedirect) {
        Intent intent = new Intent(IuppActivity.this, MyWebviewActivity.class);
        intent.putExtra("urlRedirect", urlRedirect);
        intent.putExtra("points", points);
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        setIsLoading(false);
    }

    @Override
    public void onUserAuthFailed(String error) {
        System.out.println(error);
        setIsLoading(false);
    }
}