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
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public final class LoginActivity extends AppCompatActivity {

    /* default */ DatabaseHelperSingleton myDB;
    /* default */private AnimationDrawable animationDrawable;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/users/userExist";
    /* default */static final Logger log = Logger.getLogger(LoginActivity.class.getName());

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

    }

    public void connectToApp(EditText email, EditText password) {
        ConnectionTo.switchActivityWithStringExtra(this, MainActivity.class, "yo@gmail.com");
        finish();

//        if (ConnectionTo.verifyEmptyFields(email, "Email can't be empty") ||
//                ConnectionTo.verifyEmptyFields(password, "Password can't be empty")) {
//            Toast.makeText(this, "No empty fields", Toast.LENGTH_LONG).show();
//        } else {
//            if (myDB.verifyUserInfo(email.getText().toString(), password.getText().toString())) {
//                ConnectionTo.switchActivityWithStringExtra(this, MainActivity.class, email.getText().toString());
//                finish();
//            } else {
//                loginRemoteDB(email.getText().toString(), password.getText().toString());
//            }
//        }
    }

    public void loginRemoteDB(String email, String password){
        JSONObject requestJsonObject = new JSONObject();
        try {

            requestJsonObject.put("email", email);
            requestJsonObject.put("password", password);

        } catch (JSONException e) {
            log.fine(e.getMessage());
        }

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                log.fine(response.toString() + "connection success");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonobject = response.getJSONObject(i);
                        int id = jsonobject.getInt("id");
                        String firstName = jsonobject.getString("firstName");
                        String email = jsonobject.getString("email");
                        String password = jsonobject.getString("password");
                        String lastName = jsonobject.getString("lastName");
                        User user = new User(id,email,firstName,lastName,password);
                        getMyDB().createUserWithId(user);
                        log.fine(email);
                    } catch (JSONException e) {
                        log.info(e.getMessage());
                    }
                }
                ConnectionTo.switchActivityWithStringExtra(LoginActivity.this.getApplicationContext(), MainActivity.class, email);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       log.fine("Error: " + error.getMessage());
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Error wrong email or password", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjReq);

    }

    public DatabaseHelperSingleton getMyDB() {
        return myDB;
    }
}
