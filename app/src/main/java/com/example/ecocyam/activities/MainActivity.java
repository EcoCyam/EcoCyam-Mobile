package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ecocyam.R;
import com.example.ecocyam.utility.ConnectionTo;


public final class MainActivity extends AppCompatActivity {
    /* default */ ImageView imageViewScan, imageViewLogo, imageViewAccount, imageViewSearchButton;
    /* default */ public static TextView textViewScan; //a modifier plus tard tkt
    /* default */ Animation atg, atgtwo, animationSearchBarDown, animationSearchBarUp;
    /* default */ boolean isSearchViewdeployed = false;
    /* default */ SearchView searchView;
    /* default */ LinearLayout linearLayoutSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------Animation à l'ouverture de la page--------
        imageViewScan = findViewById(R.id.imageViewScan);
        textViewScan = findViewById(R.id.textViewScan);
        searchView = findViewById(R.id.searchViewMain);
        searchView.setEnabled(false);
        imageViewLogo = findViewById(R.id.imageViewLogo);
        linearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayoutSearch);

        atg = AnimationUtils.loadAnimation(this,R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this,R.anim.atgtwo);
        animationSearchBarDown = AnimationUtils.loadAnimation(this,R.anim.animation_searchbar_down);
        animationSearchBarUp = AnimationUtils.loadAnimation(this,R.anim.animation_searchbar_up);

        imageViewScan.startAnimation(atg);
        textViewScan.startAnimation(atgtwo);
        //---------Fin animation---------------------------


        imageViewScan.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
            startActivity(intent);
        });

        imageViewLogo.setOnClickListener(v -> ConnectionTo.switchActivity(getApplicationContext(), AboutUsActivity.class));

        Intent currentIntent = getIntent();
        String emailUser = currentIntent.getStringExtra("email");
        ImageView imageViewAccount = findViewById(R.id.imageViewAccount);
        imageViewAccount.setOnClickListener(v -> ConnectionTo.switchActivityWithStringExtra(getApplicationContext(),
                SettingsActivity.class,emailUser));

        // CLIC SUR ICONE HISTORIQUE
        ImageView imgHistory = (ImageView) findViewById(R.id.imageViewMainHistory);
        imgHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intent);
        });

        //CLIC SUR ICONE RECHERCHE
        ImageView imgResearch = (ImageView) findViewById(R.id.imageViewMainRecherche);
        imgResearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayoutSearch.getLayoutParams();
                if(!isSearchViewdeployed) {

                    params.setMargins(0, 450, 0, 0); //substitute parameters for left, top, right, bottom
                    linearLayoutSearch.startAnimation(animationSearchBarDown);
                    imageViewScan.startAnimation(animationSearchBarDown);
                    textViewScan.startAnimation(animationSearchBarDown);
                    linearLayoutSearch.setLayoutParams(params);
                    searchView.setIconified(false);
                    searchView.setEnabled(true);
                    imageViewSearchButton.setEnabled(true);

                    isSearchViewdeployed = true;
                    System.out.println("sensei");
                }
                else{

                    params.setMargins(0, 50, 0, 0); //substitute parameters for left, top, right, bottom
                    linearLayoutSearch.startAnimation(animationSearchBarUp);

                    imageViewScan.startAnimation(animationSearchBarUp);
                    textViewScan.startAnimation(animationSearchBarUp);
                    linearLayoutSearch.setLayoutParams(params);
                    imageViewSearchButton.setEnabled(false);
                    searchView.setEnabled(false);

                    isSearchViewdeployed = false;
                }

            }
        });

        // CLIC SUR LA LOUPE DANS L'ONGLET RECHERCHE
        imageViewSearchButton = findViewById(R.id.imageViewMainResearchButton);
        imageViewSearchButton.setEnabled(false);
        imageViewSearchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                startActivity(intent);
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
