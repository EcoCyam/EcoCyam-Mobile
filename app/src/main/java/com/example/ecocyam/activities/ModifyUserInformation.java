package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;

public class ModifyUserInformation extends AppCompatActivity {

    /* default */private DatabaseHelperSingleton db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_information);

        db = DatabaseHelperSingleton.getInstance(this);
        Intent intent = getIntent();
        String emailUser = intent.getStringExtra("email");
        User actualUser = db.getUserByEmail(emailUser);

        EditText fName = findViewById(R.id.editTextFirstName);
        EditText lName = findViewById(R.id.editTextLastName);
        EditText email = findViewById(R.id.editTextEmail);
        CardView saveModification = findViewById(R.id.cardViewSave);

        if (actualUser != null) {
            fName.setText(actualUser.getFirstName());
            lName.setText(actualUser.getLastName());
            email.setText(actualUser.getEmail());
        }

        saveModification.setOnClickListener(v -> {
            if (saveModification(actualUser,fName,lName,email)) {
                ConnectionTo.switchActivityWithStringExtra(this, SettingsActivity.class, email.getText().toString());
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }

    public boolean saveModification(User user, EditText fName, EditText lName, EditText email) {
        if (ConnectionTo.verifyEmptyFields(fName,"No empty fName") ||
                ConnectionTo.verifyEmptyFields(lName,"No empty lName") ||
                ConnectionTo.verifyEmptyFields(email,"No empty mail")){
            Toast.makeText(this,"Care to empty fields",Toast.LENGTH_LONG).show();
        }
        else {
            if(ConnectionTo.validateFormatWithRegex(email.getText().toString())){
                user.setFirstName(fName.getText().toString());
                user.setLastName(lName.getText().toString());
                user.setEmail(email.getText().toString());
                db.updateUser(user);
                return true;
            }
            else
                email.setError("Incorrect email format");
        }
        return false;
    }
}
