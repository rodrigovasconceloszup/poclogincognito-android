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
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

import core.Config;
import core.User;
import network.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IuppActivity extends AppCompatActivity {

    ImageView expandIcon;
    TextView expandOcultTextView;
    LinearLayout cardLinearLayout;
    LinearLayout moreTextLayout;
    Button goToIuppBtn;
    ProgressBar goToIuppBtnLoading;

    boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String points = getIntent().getStringExtra("points");

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
            if (isExpanded) {
                cardLinearLayout.setMinimumHeight(400);
                expandOcultTextView.setText(R.string.iupp_ocultar);
                moreTextLayout.setVisibility(View.VISIBLE);
            } else {
                expandOcultTextView.setText(R.string.iupp_expandir);
                moreTextLayout.setVisibility(View.GONE);
            }
            expandIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), !isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up, null));
        };
        expandIcon.setOnClickListener(onClick);
        expandOcultTextView.setOnClickListener(onClick);

        goToIuppBtn = (Button) findViewById(R.id.goToIuppBtn);
        goToIuppBtn.setOnClickListener(v -> {
            final User user = new User();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Objects.requireNonNull(Config.getConfigValue(IuppActivity.this, "baseUrl")))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthService service = retrofit.create(AuthService.class);

            setIsLoading(true);
            service.authUser(user).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(@NotNull Call<Map<String, String>> call, @NotNull Response<Map<String, String>> response) {
                    if (response.isSuccessful()) {
                        final Map<String, String> body = response.body();
                        assert body != null;
                        final String urlRedirect = body.get("urlRedirect");

                        Intent intent = new Intent(IuppActivity.this, MyWebviewActivity.class);
                        intent.putExtra("urlRedirect", urlRedirect);
                        intent.putExtra("points", points);
                        startActivity(intent);
                        setResult(Activity.RESULT_OK);
                    }
                    setIsLoading(false);
                }

                @Override
                public void onFailure(@NotNull Call<Map<String, String>> call, @NotNull Throwable t) {
                    System.out.println(call);
                    System.out.println(t.getMessage());
                    setIsLoading(false);
                }
            });
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
}