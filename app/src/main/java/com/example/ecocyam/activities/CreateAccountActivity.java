package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ecocyam.R;
import com.example.ecocyam.utility.ConnectionTo;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        LinearLayout linearLayout = findViewById(R.id.gradient_id);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        CardView cardViewCreate = findViewById(R.id.cardViewLogin);
        cardViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTo.switchActivity(getApplicationContext(), MainActivity.class);
                finish();
            }
        });
    }

}
