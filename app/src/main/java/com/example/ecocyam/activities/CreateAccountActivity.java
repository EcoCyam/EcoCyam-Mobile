package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;

public final class CreateAccountActivity extends AppCompatActivity {

    /* default */private DatabaseHelperSingleton db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        db = DatabaseHelperSingleton.getInstance(this);

        ConstraintLayout constraintLayout = findViewById(R.id.gradient_id);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        CardView cardViewCreate = findViewById(R.id.cardViewLogin);
        cardViewCreate.setOnClickListener(v -> {
            EditText email = findViewById(R.id.editTextEmail);
            EditText firstName = findViewById(R.id.editTextFirstName);
            EditText lastName = findViewById(R.id.editTextLastName);
            EditText password = findViewById(R.id.editTextPassword);

            createAccount(email,firstName,lastName,password);
        });
    }

    public void createAccount(EditText email, EditText firstName, EditText lastName, EditText password){

        if (ConnectionTo.verifyEmptyFields(email,"Email cannot be empty") ||
                ConnectionTo.verifyEmptyFields(firstName,"First Name cannot be empty") ||
                ConnectionTo.verifyEmptyFields(lastName,"Last name cannot be empty") ||
                ConnectionTo.verifyEmptyFields(password,"Password cannot be empty")){
            Toast.makeText(this, "No empty fields", Toast.LENGTH_LONG).show();
        }

        else {

            if (ConnectionTo.validateFormatWithRegex(email.getText().toString())) {

                User newUser = new User(email.getText().toString(), firstName.getText().toString(),
                        lastName.getText().toString(), password.getText().toString());
                if (db.isUserUnique(newUser)) {
                    if (db.createUser(newUser)) {
                        ConnectionTo.switchActivityWithStringExtra(this,MainActivity.class,email.getText().toString());
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Erreur connection", Toast.LENGTH_LONG).show();
                }
            }
            else {
                email.setError("Incorrect email format");
            }
        }

    }

}
