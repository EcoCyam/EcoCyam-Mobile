package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecocyam.R;

public class ApplicationEcoCyam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_eco_cyam);

        Intent intent = getIntent();
        // get user id
       // int value = Integer.parseInt(intent.getStringExtra("id"));

    }
}
