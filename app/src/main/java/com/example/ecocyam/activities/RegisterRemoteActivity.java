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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterRemoteActivity extends AppCompatActivity {
    EditText emailBox, passwordBox, firstNameBox, lastNameBox;
    Button registerButton;
    TextView loginLink;
    String URL = "https://ecocyam-web.cfapps.io/api/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_remote);

        lastNameBox = (EditText)findViewById(R.id.lastNameBox);
        firstNameBox = (EditText)findViewById(R.id.firstNameBox);
        emailBox = (EditText)findViewById(R.id.emailBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        registerButton = (Button)findViewById(R.id.registerButton);
        loginLink = (TextView)findViewById(R.id.loginLink);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("!!!!!!!!!!!!!!!!");
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put("firstName", firstNameBox.getText().toString());
                    requestJsonObject.put("lastName", lastNameBox.getText().toString());
                    requestJsonObject.put("email", emailBox.getText().toString());
                    requestJsonObject.put("password", passwordBox.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString() + " success");
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("Error: " + error.getMessage());
                            }
                        });
                Volley.newRequestQueue(RegisterRemoteActivity.this).add(jsonObjReq);

            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterRemoteActivity.this, LoginRemoteActivity.class));
            }
        });
    }
}