package com.example.ecocyam.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.AllLanguageImpl;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.interfaces.AllLanguage;
import com.example.ecocyam.language_decorator.FrenchLanguage;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.AlertDialogGenerator;
import com.example.ecocyam.utility.ConnectionTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class SettingsActivity extends AppCompatActivity {
    /* default */private ListView listViewSettings;
    /* default */private DatabaseHelperSingleton db;
    /* default */private TextView displayEmail;
    /* default */private User actualUser;
    /* default */private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_settings);
        showListSettings();

        context = getApplicationContext();

        db = DatabaseHelperSingleton.getInstance(this);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        actualUser = db.getUserByEmail(email);

        //Affichage info user
        displayEmail = findViewById(R.id.textView_actual_email);
        TextView displayfName = findViewById(R.id.textView_actual_fname);
        TextView displaylName = findViewById(R.id.textView_actual_lname);

        displayEmail.setText(actualUser.getEmail());
        displayfName.setText(actualUser.getFirstName());
        displaylName.setText(actualUser.getLastName());

        listViewSettings.setOnItemClickListener((adapterView, view, position, id) -> actionItemClicked(position));
    }

    //rajouter quand on aura autre action
    public void actionItemClicked(int position) {
        switch (position) {
            case 1:
                changeUserInfo(displayEmail.getText().toString());
                recreate();
                break;
            case 2:
                showChangeLangageDialog();
                break;
            case 3:
                logOutUser();
                break;
            case 4:
                deleteUserAccount(actualUser.getId());
                break;
            default:
                break;
        }
    }

    public void deleteUserAccount(int id) {
        AlertDialog.Builder builder = AlertDialogGenerator.createAlertDialog("Delete Account",
                "Delete your account ?", "Cancel", this);
        builder.setPositiveButton("Delete account", (dialog, which) -> {
            if (db.deleteUser(String.valueOf(id)) > 0) {
                Intent intent = new Intent("CLOSE_ALL");
                context.sendBroadcast(intent);
                ConnectionTo.switchActivity(context, LoginActivity.class);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void logOutUser() {
        Intent intent = new Intent("CLOSE_ALL");
        this.sendBroadcast(intent);
        ConnectionTo.switchActivity(this, LoginActivity.class);
    }

    public void changeUserInfo(String email) {
        ConnectionTo.switchActivityWithStringExtra(this, ModifyUserInformation.class, email);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public List<String> fillSettingsList() {
        List<String> settings = new ArrayList<>();
        settings.add("Change password");
        settings.add("Change user's info");
        settings.add("Change language");
        settings.add("Log out");
        settings.add("Delete Account");
        return settings;
    }

    public void showListSettings() {
        //settings list
        listViewSettings = findViewById(R.id.listView_settings);
        List<String> itemSettings = fillSettingsList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemSettings);
        listViewSettings.setAdapter(arrayAdapter);

        //fin settings list

        //account.setOnClickListener(v -> showChangeLangageDialog());
    }


    public void showChangeLangageDialog() {
        //simplement modifier ici les nouvelles langues en faisant new langue
        AllLanguage allLanguage = new FrenchLanguage(new AllLanguageImpl());
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
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", language);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);
    }
}
