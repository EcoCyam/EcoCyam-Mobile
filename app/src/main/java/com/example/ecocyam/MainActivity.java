package com.example.ecocyam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecocyam.localDatabase.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("test yooo");
        myDB = new DatabaseHelper(this);

    }
}
