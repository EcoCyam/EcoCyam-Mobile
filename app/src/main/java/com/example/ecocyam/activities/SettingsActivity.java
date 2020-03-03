package com.example.ecocyam.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.AllLanguageImpl;
import com.example.ecocyam.interfaces.AllLanguage;
import com.example.ecocyam.language_decorator.FrenchLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class SettingsActivity extends AppCompatActivity {
    /* default */private ListView listViewSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_settings);
        showListSettings();

        listViewSettings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                actionItemClicked(position);
            }
        });
    }

    public void actionItemClicked(int position) {
        switch (position){
            case 2 :
              showChangeLangageDialog();
              default:
                  return;
        }
    }

    public List<String> fillSettingsList() {
        List<String> settings = new ArrayList<>();
        settings.add("Change password");
        settings.add("Change email");
        settings.add("Change language");
        return  settings;
    }

    public void showListSettings() {
        //settings list
        listViewSettings = findViewById(R.id.listView_settings);
        List<String> itemSettings = fillSettingsList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,itemSettings);
        listViewSettings.setAdapter(arrayAdapter);

        //fin settings list

        //account.setOnClickListener(v -> showChangeLangageDialog());
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
