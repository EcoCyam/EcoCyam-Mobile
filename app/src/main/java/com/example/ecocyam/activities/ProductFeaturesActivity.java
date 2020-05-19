package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.utility.FeaturesListAdapter;
import com.example.ecocyam.utility.PictureFormatting;

import java.util.ArrayList;
import java.util.Base64;

public class ProductFeaturesActivity extends AppCompatActivity {
    /* default */ ListView list;
    /* default */ ArrayList<String> itemsComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_features);

        Intent intent = getIntent();
        ScannedProduct product = (ScannedProduct) intent.getSerializableExtra("product");

        //Switch CASE computer, etc..

        list = (ListView) findViewById(R.id.listViewPF);
        TextView textViewProductName = (TextView) findViewById(R.id.textViewProductNamePF);
        TextView textViewProductRating = (TextView) findViewById(R.id.textViewProductRatingPF);
        TextView textViewProductMarque = (TextView) findViewById(R.id.textViewProductMarquePF);
        ImageView imageViewProductPF = findViewById(R.id.imageViewProductPF);

        textViewProductName.setText(product.getTitle());
        textViewProductRating.setText(String.valueOf(product.getRating()));
        textViewProductMarque.setText(product.getMarque());
        if(product.getSerializeImage() != null){
            imageViewProductPF.setImageBitmap(PictureFormatting.getBitmap(Base64.getDecoder().decode(product.getSerializeImage())));
        }

        itemsComputer = new ArrayList<String>();

        itemsComputer.add("Durabilite");
        itemsComputer.add("Fiabilite");
        itemsComputer.add("Consommation");
        FeaturesListAdapter adp = new FeaturesListAdapter(this, R.layout.features_item, itemsComputer);

        list.setAdapter(adp);

    }

}