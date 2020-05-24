package com.example.ecocyam.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.PictureFormatting;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Logger;

public class AddProductFormActivity extends AppCompatActivity {

    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/items";
    /* default */static final Logger log = Logger.getLogger(AddProductFormActivity.class.getName());
    /* default */private static final int GALLERY_REQUEST = 2;

    /* default */EditText editTextAddProductName;
    /* default */EditText editTextProductBar;
    /* default */EditText editTextEcologyRating;
    /* default */EditText editTextDuabilityRating;
    /* default */EditText editTextUserRating;
    /* default */byte[] imageByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_form);

        Spinner spin = (Spinner) findViewById(R.id.spinnerCategorieProduitAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        CardView cardViewCreate = findViewById(R.id.cardViewAddProduct);

        editTextAddProductName = findViewById(R.id.editTextAddProductName);
        editTextProductBar = findViewById(R.id.editTextProductBar);
        editTextEcologyRating = findViewById(R.id.editTextEcologyRating);
        editTextDuabilityRating = findViewById(R.id.editTextDuabilityRating);
        editTextUserRating = findViewById(R.id.editTextUserRating);
        Button btnSetimage = findViewById(R.id.btn_ImageProduct);

        cardViewCreate.setOnClickListener(v -> {
            addProductRemoteDB();
        });

        btnSetimage.setOnClickListener(v -> {
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, GALLERY_REQUEST);
        });
    }

    public void addProductRemoteDB() {
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("name", editTextAddProductName.getText().toString());
            requestJsonObject.put("barcode", editTextProductBar.getText().toString());
            requestJsonObject.put("image", Base64.getEncoder().encodeToString(imageByte));
            requestJsonObject.put("categoryId", 8);
            requestJsonObject.put("note1", editTextEcologyRating.getText().toString());
            requestJsonObject.put("note2", editTextDuabilityRating.getText().toString());
            requestJsonObject.put("note3", editTextUserRating.getText().toString());
        } catch (JSONException e) {
            log.fine(e.getMessage());
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                log.info(response.toString() + "add success");
                Toast.makeText(AddProductFormActivity.this.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                ConnectionTo.switchActivity(AddProductFormActivity.this.getApplicationContext(), MainActivity.class);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log.info("Error: " + error.getMessage());
                        Toast.makeText(AddProductFormActivity.this.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0,0,0));
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                imageByte = PictureFormatting.getByteArray(selectedImage, "png");
                log.info("Image Byte: " + imageByte);

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
