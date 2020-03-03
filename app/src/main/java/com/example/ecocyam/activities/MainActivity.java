package com.example.ecocyam.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecocyam.HistoryActivity;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.AllLanguageImpl;
import com.example.ecocyam.interfaces.AllLanguage;
import com.example.ecocyam.language_decorator.FrenchLanguage;
import com.example.ecocyam.utility.ConnectionTo;

import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    /* default */ ImageView imageViewScan;
    /* default */ public static TextView textViewScan; //a modifier plus tard tkt
    /* default */ Animation atg, atgtwo, atgthree, atgfour;
    /* default */ boolean isSearchViewdeployed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
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
                ConnectionTo.switchActivity(getApplicationContext(), AboutUsActivity.class);
            }
        });

        ImageView account = findViewById(R.id.imageViewAccount);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangageDialog();
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

            }
        });
    }

    public void showChangeLangageDialog() {
        //simplement modifier ici les nouvelles langues en faisant new langue
        AllLanguage allLanguage =new FrenchLanguage(new AllLanguageImpl());
        Map<String, String> allSupportedLanguage = allLanguage.addSupportedLanguage();


        final String[] lstLanguagesCode = new String[allSupportedLanguage.size()];
        final String[] lstLanguagesDisplay = new String[allSupportedLanguage.size()];

        final int[] i = {0};
        allSupportedLanguage.forEach((k, v) -> {
            lstLanguagesCode[i[0]] = k;
            lstLanguagesDisplay[i[0]] = v;
            i[0] = i[0] + 1;
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Select language");
        alertDialogBuilder.setSingleChoiceItems(lstLanguagesDisplay, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setLocale(lstLanguagesCode[which]);
                recreate();
                dialog.dismiss();
            }
        });

        AlertDialog alerDialog = alertDialogBuilder.create();
        alerDialog.show();
    }

    public void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

       Resources resources = getBaseContext().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        getBaseContext().createConfigurationContext(configuration);
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_lang",language);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(language);
    }

}
