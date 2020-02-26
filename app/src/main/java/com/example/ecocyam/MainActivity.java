package com.example.ecocyam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    ImageView imageViewScan;
    public static TextView textViewScan; //a modifier plus tard tkt
    Animation atg, atgtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------Animation à l'ouverture de la page--------
        imageViewScan = findViewById(R.id.imageViewScan);
        textViewScan = findViewById(R.id.textViewScan);
        atg = AnimationUtils.loadAnimation(this,R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this,R.anim.atgtwo);
        imageViewScan.startAnimation(atg);
        textViewScan.startAnimation(atgtwo);
        //---------Fin animation---------------------------

        ImageView img = (ImageView) findViewById(R.id.imageViewScan);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);
            }
        });
    }


}
