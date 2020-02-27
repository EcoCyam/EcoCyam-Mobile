package com.example.ecocyam.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecocyam.MainActivity;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.utility.AlertDialogGenerator;
import com.example.ecocyam.localdatabase.DatabaseHelper;
import com.example.ecocyam.utility.ConnectionTo;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    /* default */ DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ConstraintLayout constraintLayout = findViewById(R.id.gradient_id);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        CardView cardViewConnect = findViewById(R.id.cardViewLogin);
        cardViewConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTo.switchActivity(getApplicationContext(), MainActivity.class);
            }
        });


        CardView cardViewNotRegister = findViewById(R.id.cardViewNotRegisterYet);
        cardViewNotRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTo.switchActivity(getApplicationContext(),CreateAccountActivity.class);
                finish();
            }
        });


        /* default */ myDB = new DatabaseHelper(this);

        //   openAlertDialogMoche();
      //  openAlertDialogLogin();
      //  selectUser();

    }


    public void openAlertDialogLogin() {
        LayoutInflater li = LayoutInflater.from(this);
        final View createUser = li.inflate(R.layout.createuser, null);

        final EditText email = createUser.findViewById(R.id.EditEmail);
        final EditText firstName = createUser.findViewById(R.id.EditFirstName);
        final EditText lastName = createUser.findViewById(R.id.EditLastName);
        final EditText password = createUser.findViewById(R.id.createPassword);

        AlertDialog.Builder alertDialog = AlertDialogGenerator.createAlertDialog("test", "test", "Cancel", this);

        alertDialog.setView(createUser);
        alertDialog.setPositiveButton("Sign up", null);
        AlertDialog alert = alertDialog.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean closeLoginAlert;

                        List<String> requiredFields = new ArrayList<>();
                        requiredFields.add(email.getText().toString());
                        requiredFields.add(firstName.getText().toString());
                        requiredFields.add(lastName.getText().toString());
                        requiredFields.add(password.getText().toString());

                        closeLoginAlert = createUserAlertDialog(requiredFields);

                        if (closeLoginAlert) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        alert.show();
    }


    public boolean createUserAlertDialog(List<String> fields) {

        boolean verifyEmptyRequiredFields = verifyEmptyRequiredFields(fields);
        boolean verifyUniqueEmail = myDB.isUserUnique(fields.get(0));

        if (verifyEmptyRequiredFields) {
            Toast.makeText(this, "No Empty fields", Toast.LENGTH_LONG).show();
            return false;

        } else if (!verifyUniqueEmail) {
            Toast.makeText(this, "Email must be unique", Toast.LENGTH_LONG).show();
            return false;

        } else {
            User user = new User(fields.get(0), fields.get(1),
                    fields.get(2), fields.get(3));
            boolean testInsert = myDB.createUser(user);

            if (testInsert)
                Toast.makeText(this, "Creation successfull", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Error creation", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    public boolean verifyEmptyRequiredFields(List<String> requiredFields) {
        return (requiredFields.contains(null) || requiredFields.contains(""));
    }


/*
    public void openAlertDialogMoche() {
        LayoutInflater li = LayoutInflater.from(this);
        View createUser = li.inflate(R.layout.createuser, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(createUser);

        final EditText firstName = (EditText) createUser.findViewById(R.id.EditFirstName);
        final EditText lastName = (EditText) createUser.findViewById(R.id.EditLastName);
        final EditText createPassword = (EditText) createUser.findViewById(R.id.createPassword);

// créer alertDialog avec un builder et/ou avec une méthode
        alertDialogBuilder
                .setCancelable(false)
                .setNeutralButton("Create user",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (firstName.getText().toString().isEmpty() || createPassword.getText().toString().isEmpty()) {
                                    Toast.makeText(LoginActivity.this, "No Empty fields", Toast.LENGTH_LONG).show();

                                } else {
                                    boolean testCreate = myDB.isUserExist(firstName.getText().toString());
                                    if (testCreate) {
                                        boolean testInsert = myDB.createUser(firstName.getText().toString(), createPassword.getText().toString());
                                        if (testInsert)
                                            Toast.makeText(LoginActivity.this, "Creation successfull", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(LoginActivity.this, "Error creation", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(LoginActivity.this, "User name already exists", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Connection",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                boolean testCo = myDB.testConnectionInfo(firstName.getText().toString(), createPassword.getText().toString());
                                if (testCo) {
                                    //open new activity
                                    Toast.makeText(LoginActivity.this, "Connection ready", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, ApplicationEcoCyam.class);
                                    // intent.putExtra("id",id);
                                    LoginActivity.this.startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();

                            }
                        })
        ;

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectUser() {
        selectB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.get_data();
                        if (res.getCount() == 0) {
                            Toast.makeText(LoginActivity.this, "insert successfull", Toast.LENGTH_LONG).show();
                            trace("error", "nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id : ").append(res.getString(0)).append("\n");
                            buffer.append("EMAIL : ").append(res.getString(1)).append("\n");
                            buffer.append("FIRSTNAME : ").append(res.getString(2)).append("\n");
                            buffer.append("LASTNAME : ").append(res.getString(3)).append("\n");
                            buffer.append("Password : ").append(res.getString(4)).append("\n\n");
                        }
                        trace("Data", buffer.toString());
                    }
                }
        );
    }
*/
    public void trace(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
