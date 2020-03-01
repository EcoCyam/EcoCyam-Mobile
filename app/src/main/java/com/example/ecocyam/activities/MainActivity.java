package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecocyam.R;
import com.example.ecocyam.utility.ConnectionTo;


public class MainActivity extends AppCompatActivity {
    /* default */ImageView imageViewScan;
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
        atg = AnimationUtils.loadAnimation(this,R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this,R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this,R.anim.atgthree);
        atgfour = AnimationUtils.loadAnimation(this,R.anim.atgfour);
        imageViewScan.startAnimation(atg);
        textViewScan.startAnimation(atgtwo);
        //---------Fin animation---------------------------

        ImageView imgScan = (ImageView) findViewById(R.id.imageViewScan);
        imgScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);
            }
        });

        ImageView logo = findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTo.switchActivity(getApplicationContext(),AboutUsActivity.class);
            }
        });

        ImageView imgHistory = (ImageView) findViewById(R.id.imageViewMainHistory);
        imgHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });


        ImageView imgResearch = (ImageView) findViewById(R.id.imageViewMainRecherche);
        imgResearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!isSearchViewdeployed) {

                    LinearLayout linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayoutSearch.getLayoutParams();
                    params.setMargins(0, 450, 0, 0); //substitute parameters for left, top, right, bottom
                    linearLayoutSearch.startAnimation(atgthree);
                    imageViewScan.startAnimation(atgthree);
                    textViewScan.startAnimation(atgthree);
                    linearLayoutSearch.setLayoutParams(params);


                    isSearchViewdeployed = true;
                }
                else{
                    LinearLayout linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayoutSearch.getLayoutParams();
                    params.setMargins(0, 50, 0, 0); //substitute parameters for left, top, right, bottom
                    linearLayoutSearch.startAnimation(atgfour);

                    imageViewScan.startAnimation(atgfour);
                    textViewScan.startAnimation(atgfour);
                    linearLayoutSearch.setLayoutParams(params);

                    isSearchViewdeployed = false;
                }

            }
        });
    }



}
