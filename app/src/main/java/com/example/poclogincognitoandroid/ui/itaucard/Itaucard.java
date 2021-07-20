package com.example.poclogincognitoandroid.ui.itaucard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.ui.iupp.IuppActivity;
import com.example.poclogincognitoandroid.ui.webview.MyWebviewActivity;

public class Itaucard extends AppCompatActivity {

    ImageView expandIcon;
    TextView expandOcultTextView;
    TextView seeMoreText;
    LinearLayout cardLinearLayout;
    LinearLayout moreTextLayout;

    boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itaucard);
        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        cardLinearLayout = findViewById(R.id.cardLinearLayout);
        seeMoreText = findViewById(R.id.seeMoreText);
        seeMoreText.setOnClickListener(v -> {
            Intent intent = new Intent(this, IuppActivity.class);
            intent.putExtra("points", "50000");
            startActivity(intent);
            setResult(Activity.RESULT_OK);
        });
        expandIcon.setOnClickListener(v -> {
            isExpanded = !isExpanded;
            if (v.equals(expandIcon)) {
                expandIcon.setImageDrawable(getResources().getDrawable(isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up));
                if (!isExpanded) {
                    cardLinearLayout.setMinimumHeight(400);
                    expandOcultTextView.setText("ocultar");
                    moreTextLayout.setVisibility(View.VISIBLE);
                } else {
                    expandOcultTextView.setText("expandir");
                    moreTextLayout.setVisibility(View.GONE);
                }
            }
        });
    }
}