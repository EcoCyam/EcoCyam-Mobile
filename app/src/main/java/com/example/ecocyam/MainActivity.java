package com.example.ecocyam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecocyam.Activities.ApplicationEcoCyam;
import com.example.ecocyam.localDatabase.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    Button selectB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectB = (Button) findViewById(R.id.select);

        myDB = new DatabaseHelper(this);

        openAlertDialogCreation();
        selectUser();

    }

    public void openAlertDialogCreation() {
        LayoutInflater li = LayoutInflater.from(this);
        View createUser = li.inflate(R.layout.createuser, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(createUser);

        final EditText createUserName = (EditText) createUser.findViewById(R.id.createUserName);
        final EditText createPassword = (EditText) createUser.findViewById(R.id.createPassword);
// créer alertDialog avec un builder et/ou avec une méthode
        alertDialogBuilder
                .setCancelable(false)
                .setNeutralButton("Create user",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (createUserName.getText().toString().isEmpty() || createPassword.getText().toString().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "No Empty fields", Toast.LENGTH_LONG).show();

                                } else {
                                    boolean testCreate = myDB.isUserExist(createUserName.getText().toString());
                                    if (testCreate) {
                                        boolean testInsert = myDB.createUser(createUserName.getText().toString(), createPassword.getText().toString());
                                        if (testInsert)
                                            Toast.makeText(MainActivity.this, "Creation successfull", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(MainActivity.this, "Error creation", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(MainActivity.this, "User name already exists", Toast.LENGTH_LONG).show();
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
                                boolean testCo = myDB.testConnectionInfo(createUserName.getText().toString(), createPassword.getText().toString());
                                if (testCo) {
                                    //open new activity
                                    Toast.makeText(MainActivity.this, "Connection ready", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, ApplicationEcoCyam.class);
                                    // intent.putExtra("id",id);
                                    MainActivity.this.startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(MainActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();

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
                            Toast.makeText(MainActivity.this, "insert successfull", Toast.LENGTH_LONG).show();
                            trace("error", "nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id : ").append(res.getString(0)).append("\n");
                            buffer.append("Username : ").append(res.getString(1)).append("\n");
                            buffer.append("Password : ").append(res.getString(2)).append("\n\n");
                        }
                        trace("Data", buffer.toString());
                    }
                }
        );
    }

    public void trace(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
