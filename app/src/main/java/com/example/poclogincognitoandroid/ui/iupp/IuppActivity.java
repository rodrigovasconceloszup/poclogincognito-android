package com.example.poclogincognitoandroid.ui.iupp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Space;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IuppActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();

    ImageView expandIcon;
    TextView expandOcultTextView;
    LinearLayout cardLinearLayout;
    LinearLayout moreTextLayout;
    Button goToIuppBtn;

    boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String points = getIntent().getStringExtra("points");

        ActionBar actionBar = getSupportActionBar();
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

        View.OnClickListener onClick = v -> {
            isExpanded = !isExpanded;
            if (isExpanded) {
                cardLinearLayout.setMinimumHeight(400);
                expandOcultTextView.setText("ocultar");
                moreTextLayout.setVisibility(View.VISIBLE);
            } else {
                expandOcultTextView.setText("expandir");
                moreTextLayout.setVisibility(View.GONE);
            }
            expandIcon.setImageDrawable(getResources().getDrawable(!isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up));
        };
        expandIcon.setOnClickListener(onClick);
        expandOcultTextView.setOnClickListener(onClick);

        goToIuppBtn = (Button) findViewById(R.id.goToIuppBtn);
        goToIuppBtn.setOnClickListener(v -> {
            final String username = "marco";
            final String password = "Iupp@123456";
            final String name = "Marco";
            final String email = "marco@gmail.com";
            final String phoneNumber = "+55DDDTELEFONE";

            String payload = "{\"username\": " + "\"" + username + "\"" +
                    ", \"name\": " + "\"" + name + "\"" +
                    ", \"email\": " + "\"" + email + "\"" +
                    ", \"phoneNumber\": " + "\"" + phoneNumber + "\"" +
                    ", \"password\": "+ "\"" + password + "\"" +"}";

            RequestBody body = RequestBody.create(JSON, payload);

            Request request = new Request.Builder()
                    .url("http://192.168.5.87:8085/identity-cognito/api/v1/auth/login")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e);
                    System.out.println(call);
                    System.out.println(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    try {
                        JSONObject json = new JSONObject(body);
                        final String urlRedirect = json.getString("urlRedirect");
                        System.out.println(urlRedirect);

                        Intent intent = new Intent(IuppActivity.this, MyWebviewActivity.class);
                        intent.putExtra("urlRedirect", urlRedirect);
                        intent.putExtra("points", points);
                        startActivity(intent);
                        setResult(Activity.RESULT_OK);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}