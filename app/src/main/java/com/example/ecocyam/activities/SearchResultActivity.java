package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.Product;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;
import com.example.ecocyam.utility.ProductSearchResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchResultActivity extends AppCompatActivity {
    //a list to store all the products
    /* default */ List<Product> productList;
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


/*
        //initializing the productlist



        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "APPLE",
                        8.1,
                        R.drawable.icon_phone));

        productList.add(
                new Product(
                        1,
                        "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                        "DELL",
                        4.3,
                        R.drawable.icon_electrique));

        productList.add(
                new Product(
                        1,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "MICROSOFT",
                        6.8,
                        R.drawable.icon_computer));
*/

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
                        int itemId = Integer.parseInt(jsonobject.getString("itemId"));
                        String title = jsonobject.getString("name");
                      //  double rating = Double.parseDouble(jsonobject.getString("overallScore"));
                        Product product = new Product(itemId,title,"Apple",0.0d,0);
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
                        ConnectionTo.switchActivity(SearchResultActivity.this.getApplicationContext(),MainActivity.class);
                        Toast.makeText(SearchResultActivity.this.getApplicationContext(), "no product found", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjReq);
        log.info("return" + productList.size());
    }
}
