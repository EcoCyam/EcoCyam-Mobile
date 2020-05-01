package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public final class CreateAccountActivity extends AppCompatActivity {

    /* default */private DatabaseHelperSingleton db;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/users";
    /* default */static final Logger log = Logger.getLogger(CreateAccountActivity.class.getName());
    /* default */private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        db = DatabaseHelperSingleton.getInstance(this);

        LinearLayout linearLayout = findViewById(R.id.gradient_id);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        CardView cardViewCreate = findViewById(R.id.cardViewLogin);
        cardViewCreate.setOnClickListener(v -> {
            EditText email = findViewById(R.id.editTextEmail);
            EditText firstName = findViewById(R.id.editTextFirstName);
            EditText lastName = findViewById(R.id.editTextLastName);
            EditText password = findViewById(R.id.editTextPassword);

            createAccount(email, firstName, lastName, password);
        });
    }

    public void createAccount(EditText email, EditText firstName, EditText lastName, EditText password) {

        if (ConnectionTo.verifyEmptyFields(email, "Email cannot be empty") ||
                ConnectionTo.verifyEmptyFields(firstName, "First Name cannot be empty") ||
                ConnectionTo.verifyEmptyFields(lastName, "Last name cannot be empty") ||
                ConnectionTo.verifyEmptyFields(password, "Password cannot be empty")) {
            Toast.makeText(this, "No empty fields", Toast.LENGTH_LONG).show();
        } else {

            if (ConnectionTo.validateFormatWithRegex(email.getText().toString())) {

                newUser = new User(email.getText().toString(), firstName.getText().toString(),
                        lastName.getText().toString(), password.getText().toString());

                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put("firstName", newUser.getFirstName());
                    requestJsonObject.put("lastName", newUser.getLastName());
                    requestJsonObject.put("email", newUser.getEmail());
                    requestJsonObject.put("password", newUser.getPassword());

                } catch (JSONException e) {
                    log.fine(e.getMessage());
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        log.fine(response.toString() + " connection success");
                        try {
                            int id = response.getInt("id");
                            User finalUser = getNewUser();
                            finalUser.setId(id);
                            getDb().createUserWithId(finalUser);
                            ConnectionTo.switchActivityWithStringExtra(CreateAccountActivity.this.getApplicationContext(), MainActivity.class, finalUser.getEmail());
                        } catch (JSONException e) {
                            log.info("error getting response");
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                log.fine("Error: " + error.getMessage());
                                Toast.makeText(CreateAccountActivity.this.getApplicationContext(), "Error email already exist", Toast.LENGTH_LONG).show();
                            }
                        });
                Volley.newRequestQueue(this).add(jsonObjReq);

            } else {
                email.setError("Incorrect email format");
            }
        }

    }

    public User getNewUser() {
        return newUser;
    }

    public DatabaseHelperSingleton getDb() {
        return db;
    }
}
