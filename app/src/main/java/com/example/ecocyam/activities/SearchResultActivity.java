package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;
import com.example.ecocyam.utility.PictureFormatting;
import com.example.ecocyam.utility.ProductSearchResultAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class SearchResultActivity extends AppCompatActivity {
    //a list to store all the products
    /* default */ List<ScannedProduct> productList;
    /* default */ProductSearchResultAdapter adapter;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/items/search";
    /* default */static final Logger log = Logger.getLogger(SearchResultActivity.class.getName());

    //the recyclerview
    /* default */ RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //getting the recyclerview from xml
        Intent intent = getIntent();
        String query = intent.getStringExtra("email");

        productList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchResult);

        searchItemByApi(query, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this.getApplicationContext()));

                //creating recyclerview adapter
                adapter = new ProductSearchResultAdapter(SearchResultActivity.this.getApplicationContext(), productList);

                //setting adapter to recyclerview
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionTo.switchActivity(SearchResultActivity.this.getApplicationContext(),AddProductFormActivity.class);
            }
        });
    }

    public void searchItemByApi(String query, final VolleyCallBack callBack) {

        JSONObject requestJsonObject = new JSONObject();
        try {

            requestJsonObject.put("barcode", "");
            requestJsonObject.put("keyword", query);

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
                        int itemId = jsonobject.getInt("itemId");
                        String title = jsonobject.getString("name");
                        double rating = Double.parseDouble(jsonobject.getString("overallScore"));
                        //byte[] productImageBin = Base64.getDecoder().decode(jsonobject.getString("image"));
                        //final Bitmap productImage = PictureFormatting.getBitmap(productImageBin);

                        ScannedProduct product = new ScannedProduct(title,(float)rating,null);
                        product.setSerializeImage(jsonobject.getString("image"));
                        product.setRefProductMariaDb(itemId);
                        log.info(product.getTitle());
                        productList.add(product);
                        log.info("size en remplissage" + productList.size());
                    } catch (JSONException e) {
                        log.info(e.getMessage());
                    }
                }
                callBack.onSuccess();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log.fine("Error: " + error.getMessage());
                        ConnectionTo.switchActivity(SearchResultActivity.this.getApplicationContext(),AddProductFormActivity.class);
                        Toast.makeText(SearchResultActivity.this.getApplicationContext(), "no product found", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjReq);
        log.info("return" + productList.size());
    }
}
