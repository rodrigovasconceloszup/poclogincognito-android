package com.example.poclogincognitoandroid.ui.itau;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;

import java.util.Objects;

public class ItauActivity extends AppCompatActivity {

    ImageView expandIcon;
    TextView expandOcultTextView;
    LinearLayout cardLinearLayout;
    LinearLayout moreTextLayout;

    boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        actionBar.setTitle(Html.fromHtml("<font color=\"#726A64\">" + getString(R.string.iupp) + "</font>"));
        setContentView(R.layout.activity_itau);
        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        cardLinearLayout = findViewById(R.id.cardLinearLayout);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        expandIcon.setOnClickListener(v -> {
            isExpanded = !isExpanded;
            if (v.equals(expandIcon)) {
                if (!isExpanded) {
                    expandOcultTextView.setText("ocultar");
                    moreTextLayout.setVisibility(View.VISIBLE);
                } else {
                    expandOcultTextView.setText("expandir");
                    moreTextLayout.setVisibility(View.GONE);
                }
            }
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