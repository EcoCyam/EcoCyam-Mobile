package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.ecocyam.R;

import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
import com.example.ecocyam.utility.ProductHistoryAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HistoryActivity extends AppCompatActivity {
    //db singleton
    /* default */private DatabaseHelperSingleton db = DatabaseHelperSingleton.getInstance(this);

    //a list to store all the products
    /* default */ List<ScannedProduct> productList;

    //the recyclerview
    /* default */ RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        User actualUser = db.getUserByEmail(email);

        //EN DUR POUR LE TEST
        ScannedProduct product1 = new ScannedProduct("Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra))",
                8.0f, actualUser.getId(),null);

        ScannedProduct product2 = new ScannedProduct("Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                9.8f, actualUser.getId(),null);

        ScannedProduct product3 = new ScannedProduct("Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                10.9f, actualUser.getId(),null);

        //db.deleteProductByRefId(String.valueOf(actualUser.getId())); //en attendant on delete à chaque fois les anciennes versions
        db.createProductForHistory(product1);
        db.createProductForHistory(product2);
        db.createProductForHistory(product3);
        //FIN DU DUR

        //test à adapter selon le besoin
        //manipulation des images avec SQlite database : practise
       // ConnectionTo.switchActivityWithStringExtra(this,SelectPictureProduct.class,"1");
        //fin de manipulation

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        Cursor cursor = db.getAllScannedProduct(actualUser.getId());

        while (cursor.moveToNext()){
            ScannedProduct newProduct = new ScannedProduct(cursor.getString(1),(float)cursor.getDouble(3),
            cursor.getInt(6),null);
            productList.add(newProduct);
        }
        //pour avoir les plus gros id devant donc les plus récents
        Collections.reverse(productList);
        //creating recyclerview adapter
        ProductHistoryAdapter adapter = new ProductHistoryAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
