package com.example.ecocyam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.ecocyam.localDatabase.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("test yooo");
        myDB = new DatabaseHelper(this);

        openAlertDialogCreation();
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
                .setPositiveButton("Creer utilisateur",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDB.createUser(createUserName.getText().toString(), createPassword.getText().toString());
                            }
                        })
                .setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
