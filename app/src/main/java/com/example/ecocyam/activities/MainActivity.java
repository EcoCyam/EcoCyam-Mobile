package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecocyam.HistoryActivity;
import com.example.ecocyam.R;
import com.example.ecocyam.utility.ConnectionTo;

public final class MainActivity extends AppCompatActivity {
    /* default */ ImageView imageViewScan;
    /* default */ public static TextView textViewScan; //a modifier plus tard tkt
    /* default */ Animation atg, atgtwo, atgthree, atgfour;
    /* default */ boolean isSearchViewdeployed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------Animation à l'ouverture de la page--------
        imageViewScan = findViewById(R.id.imageViewScan);
        textViewScan = findViewById(R.id.textViewScan);
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);
        atgfour = AnimationUtils.loadAnimation(this, R.anim.atgfour);
        imageViewScan.startAnimation(atg);
        textViewScan.startAnimation(atgtwo);
        //---------Fin animation---------------------------

        ImageView imgScan = (ImageView) findViewById(R.id.imageViewScan);
        imgScan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
            startActivity(intent);
        });

        ImageView logo = findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(v -> ConnectionTo.switchActivity(getApplicationContext(), AboutUsActivity.class));

        Intent currentIntent = getIntent();
        String emailUser = currentIntent.getStringExtra("email");
        ImageView account = findViewById(R.id.imageViewAccount);
        account.setOnClickListener(v -> ConnectionTo.switchActivityWithStringExtra(getApplicationContext(),
                SettingsActivity.class,emailUser));

        ImageView imgHistory = (ImageView) findViewById(R.id.imageViewMainHistory);
        imgHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intent);
        });


        ImageView imgResearch = (ImageView) findViewById(R.id.imageViewMainRecherche);
        imgResearch.setOnClickListener(v -> {
            if (!isSearchViewdeployed) {

                LinearLayout linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayoutSearch.getLayoutParams();
                params.setMargins(0, 450, 0, 0); //substitute parameters for left, top, right, bottom
                linearLayoutSearch.startAnimation(atgthree);
                imageViewScan.startAnimation(atgthree);
                textViewScan.startAnimation(atgthree);
                linearLayoutSearch.setLayoutParams(params);


                isSearchViewdeployed = true;
            } else {
                LinearLayout linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayoutSearch.getLayoutParams();
                params.setMargins(0, 50, 0, 0); //substitute parameters for left, top, right, bottom
                linearLayoutSearch.startAnimation(atgfour);

                imageViewScan.startAnimation(atgfour);
                textViewScan.startAnimation(atgfour);
                linearLayoutSearch.setLayoutParams(params);

                isSearchViewdeployed = false;
            }

        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CLOSE_ALL");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
