package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class AddProductFormActivity extends AppCompatActivity {

    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/items";
    /* default */static final Logger log = Logger.getLogger(AddProductFormActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_form);

        Spinner spin = (Spinner) findViewById(R.id.spinnerCategorieProduitAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        CardView cardViewCreate = findViewById(R.id.cardViewAddProduct);

        EditText editTextAddProductName = findViewById(R.id.editTextAddProductName);
        EditText editTextProductBar = findViewById(R.id.editTextProductBar);
        EditText editTextEcologyRating = findViewById(R.id.editTextEcologyRating);
        EditText editTextDuabilityRating = findViewById(R.id.editTextDuabilityRating);
        EditText editTextUserRating = findViewById(R.id.editTextUserRating);

        cardViewCreate.setOnClickListener(v -> {
            addProductRemoteDB(editTextAddProductName.getText().toString(), editTextProductBar.getText().toString(), spin.getSelectedItem().toString(), editTextEcologyRating.getText().toString(),
                    editTextDuabilityRating.getText().toString(), editTextUserRating.getText().toString(), new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            ConnectionTo.switchActivity(AddProductFormActivity.this.getApplicationContext(), MainActivity.class);
                        }
                    });

        });
    }

    public void addProductRemoteDB(String productName, String productBarCode, String productCategory, String productEcologyRating, String productDurabilityRating, String productUserRating, final VolleyCallBack volleyCallBack) {
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("name", productName);
            requestJsonObject.put("barcode", productBarCode);
            //requestJsonObject.put("image", image);
            requestJsonObject.put("categoryId", 8);
            requestJsonObject.put("note1", productEcologyRating);
            requestJsonObject.put("note2", productDurabilityRating);
            requestJsonObject.put("note3", productUserRating);
        } catch (JSONException e) {
            log.fine(e.getMessage());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                log.info(response.toString() + "add success");
                Toast.makeText(AddProductFormActivity.this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                volleyCallBack.onSuccess();
                //ConnectionTo.switchActivity(AddProductFormActivity.this.getApplicationContext(), MainActivity.class);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log.info("Error: " + error.getMessage());
                        Toast.makeText(AddProductFormActivity.this.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(jsonObjReq);
        String toto = "toto";
    }
}
