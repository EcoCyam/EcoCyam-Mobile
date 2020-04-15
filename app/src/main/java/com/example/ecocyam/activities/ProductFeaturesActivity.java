package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.Product;
import com.example.ecocyam.utility.FeaturesListAdapter;
import java.util.ArrayList;

public class ProductFeaturesActivity extends AppCompatActivity {
    /* default */ ListView list;
    /* default */ ArrayList<String> itemsComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_features);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        //Switch CASE computer, etc..

        list = (ListView) findViewById(R.id.listViewPF);
        itemsComputer = new ArrayList<String>();
        itemsComputer.add(product.getTitle());
        itemsComputer.add(product.getMarque());
        itemsComputer.add("Consommation");
        FeaturesListAdapter adp = new FeaturesListAdapter(this, R.layout.features_item, itemsComputer);

        list.setAdapter(adp);

    }

}