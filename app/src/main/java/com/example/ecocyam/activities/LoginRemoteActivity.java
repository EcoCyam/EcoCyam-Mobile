package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.utility.CustomRequest;
import com.example.ecocyam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginRemoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_remote);

        EditText emailBox, passwordBox;
        Button loginButton;
        TextView registerLink;
        String URL = "https://ecocyam-web.cfapps.io/api/users/userExist";

        emailBox = (EditText) findViewById(R.id.emailBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerLink = (TextView) findViewById(R.id.registerLink);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("!!!!!!!!!!!!!!!!");
                JSONObject requestJsonObject = new JSONObject();
                try {

                    requestJsonObject.put("email", emailBox.getText().toString());
                    requestJsonObject.put("password", passwordBox.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString() + " success");
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error: " + error.getMessage());
                            }
                        });
                Volley.newRequestQueue(LoginRemoteActivity.this).add(jsonObjReq);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRemoteActivity.this, RegisterRemoteActivity.class));
            }
        });
    }
}



