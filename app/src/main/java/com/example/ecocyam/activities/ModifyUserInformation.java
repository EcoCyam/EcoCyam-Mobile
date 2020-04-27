package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class ModifyUserInformation extends AppCompatActivity {

    /* default */private DatabaseHelperSingleton db;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/users";
    /* default */static final Logger log = Logger.getLogger(ModifyUserInformation.class.getName());

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
        EditText password = findViewById(R.id.editTextPassword);
        CardView saveModification = findViewById(R.id.cardViewSave);

        if (actualUser != null) {
            fName.setText(actualUser.getFirstName());
            lName.setText(actualUser.getLastName());
            email.setText(actualUser.getEmail());
            password.setText(actualUser.getPassword());
        }

        saveModification.setOnClickListener(v -> {
            User updatedUser = saveModification(actualUser,fName,lName,email,password);
            if (updatedUser!=null) {
                updateUserByApi(updatedUser, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        ConnectionTo.switchActivityWithStringExtra(ModifyUserInformation.this.getApplicationContext(),
                                SettingsActivity.class, email.getText().toString());
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                    }
                });
            }
        });
    }

    public User saveModification(User user, EditText fName, EditText lName, EditText email,EditText password) {
        if (ConnectionTo.verifyEmptyFields(fName,"No empty fName") ||
                ConnectionTo.verifyEmptyFields(lName,"No empty lName") ||
                ConnectionTo.verifyEmptyFields(email,"No empty mail") ||
                ConnectionTo.verifyEmptyFields(password,"No empty password")){
            Toast.makeText(this,"Care to empty fields",Toast.LENGTH_LONG).show();
        }
        else {
            if(ConnectionTo.validateFormatWithRegex(email.getText().toString())){
                user.setFirstName(fName.getText().toString());
                user.setLastName(lName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                db.updateUser(user);
                return user;
            }
            else
                email.setError("Incorrect email format");
        }
        return null;
    }

    public void updateUserByApi(User user, final VolleyCallBack volleyCallBack){
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("firstName", user.getFirstName());
            requestJsonObject.put("lastName", user.getLastName());
            requestJsonObject.put("email", user.getEmail());
            requestJsonObject.put("password", user.getPassword());

        } catch (JSONException e) {
            log.fine(e.getMessage());
        }
        String URL_with_id = URL.concat("/").concat(String.valueOf(user.getId()));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT, URL_with_id, requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                log.fine(response.toString() + " update success");
                volleyCallBack.onSuccess();
            }
        },
                error -> {
                    log.fine("Error: " + error.getMessage());
                    Toast.makeText(this.getApplicationContext(), "Error update", Toast.LENGTH_LONG).show();
                });
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}
