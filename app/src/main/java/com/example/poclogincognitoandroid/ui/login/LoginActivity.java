package com.example.poclogincognitoandroid.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.databinding.ActivityLoginBinding;
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final boolean allFieldsCompleted = allFieldsCompleted(username, password);

                if (allFieldsCompleted) {
                    loginButton.setEnabled(false);
                    Drawable roundDrawable = getResources().getDrawable(R.drawable.rounded_button);
                    roundDrawable.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                    loginButton.setBackground(roundDrawable);
                    loginButton.setTextColor(getResources().getColor(R.color.mainColor));
                }
                loginButton.setEnabled(allFieldsCompleted);
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);

            final String username = usernameEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            String payload = "{\"username\": " + "\"" + username + "\"" +", \"password\": "+ "\"" + password + "\"" +"}";

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

                        Intent intent = new Intent(LoginActivity.this, MyWebviewActivity.class);
                        intent.putExtra("urlRedirect", urlRedirect);
                        startActivity(intent);
                        setResult(Activity.RESULT_OK);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private boolean allFieldsCompleted(String username, String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}