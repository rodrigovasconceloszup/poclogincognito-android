package com.example.poclogincognitoandroid.ui.itaucard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.poclogincognitoandroid.R;
import com.example.poclogincognitoandroid.ui.itaucard.Presenter.ItaucardPresenter;
import com.example.poclogincognitoandroid.ui.itaucard.View.IItaucardView;
import com.example.poclogincognitoandroid.ui.iupp.IuppActivity;
import com.google.android.material.card.MaterialCardView;

import core.Config;

public class Itaucard extends AppCompatActivity implements IItaucardView {

    ImageView expandIcon;
    TextView expandOcultTextView;
    TextView seeMoreText;
    TextView qtdPointsTv;
    LinearLayout cardLinearLayout;
    MaterialCardView cardView;
    LinearLayout moreTextLayout;
    ProgressBar pointsLoadingIndicator;

    boolean isExpanded = false;

    ItaucardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itaucard);

        presenter = new ItaucardPresenter(this, Config.getConfigValue(this, "defaultPoints"));

        expandIcon = findViewById(R.id.expandIcon);
        expandOcultTextView = findViewById(R.id.expandOcultTextView);
        moreTextLayout = findViewById(R.id.moreTextLayout);
        cardLinearLayout = findViewById(R.id.cardLinearLayout);
        cardView = findViewById(R.id.cardView);
        seeMoreText = findViewById(R.id.seeMoreText);
        qtdPointsTv = findViewById(R.id.qtdPointsTv);
        pointsLoadingIndicator = findViewById(R.id.pointsLoadingIndicator);
        pointsLoadingIndicator.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.iuppSecondaryColor), android.graphics.PorterDuff.Mode.SRC_IN);

        seeMoreText.setOnClickListener(v -> {
            Intent intent = new Intent(this, IuppActivity.class);
            intent.putExtra("points", Config.getConfigValue(Itaucard.this, "defaultPoints"));
            startActivity(intent);
            setResult(Activity.RESULT_OK);
        });

        View.OnClickListener onClick = v -> {
            isExpanded = !isExpanded;
            if (isExpanded) {
                cardLinearLayout.setMinimumHeight(400);
                cardView.setMinimumHeight(400);
                expandOcultTextView.setText(R.string.iupp_ocultar);
                moreTextLayout.setVisibility(View.VISIBLE);
            } else {
                cardLinearLayout.setMinimumHeight(361);
                cardView.setMinimumHeight(361);
                expandOcultTextView.setText(R.string.iupp_expandir);
                moreTextLayout.setVisibility(View.GONE);
            }
            expandIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), !isExpanded ? R.drawable.arrow_down : R.drawable.arrow_up, null));
        };
        expandIcon.setOnClickListener(onClick);
        expandOcultTextView.setOnClickListener(onClick);

        presenter.onFetchPoints("00000000000");
    }

    @Override
    public String onPointsFetch(String points) {
        pointsLoadingIndicator.setVisibility(View.GONE);
        qtdPointsTv.setVisibility(View.VISIBLE);
        return points;
    }
}