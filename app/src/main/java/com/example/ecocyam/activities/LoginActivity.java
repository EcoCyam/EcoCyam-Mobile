package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecocyam.R;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;

public final class LoginActivity extends AppCompatActivity {

    /* default */ DatabaseHelperSingleton myDB;
    /* default */private AnimationDrawable animationDrawable;

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        LinearLayout linearLayout = findViewById(R.id.gradient_id);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);

        myDB = DatabaseHelperSingleton.getInstance(this);

        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);

        CardView cardViewConnect = findViewById(R.id.cardViewLogin);
        cardViewConnect.setOnClickListener(v -> connectToApp(email, password));


        CardView cardViewNotRegister = findViewById(R.id.cardViewNotRegisterYet);
        cardViewNotRegister.setOnClickListener(v -> {
            ConnectionTo.switchActivity(getApplicationContext(), CreateAccountActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });


        //   openAlertDialogMoche();
        //  openAlertDialogLogin();
        //  selectUser();

    }

    public void connectToApp(EditText email, EditText password) {
        if (ConnectionTo.verifyEmptyFields(email, "Email can't be empty") ||
                ConnectionTo.verifyEmptyFields(password, "Password can't be empty")) {
            Toast.makeText(this, "No empty fields", Toast.LENGTH_LONG).show();
        } else {
            if (myDB.verifyUserInfo(email.getText().toString(), password.getText().toString())) {
                ConnectionTo.switchActivityWithStringExtra(this, MainActivity.class, email.getText().toString());
                finish();
            } else {
                Toast.makeText(this, "Error wrong email or password", Toast.LENGTH_LONG).show();
            }
        }
    }
}
