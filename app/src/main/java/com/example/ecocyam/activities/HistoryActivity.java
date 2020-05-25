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
import java.util.Base64;
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

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        Cursor cursor = db.getAllScannedProduct(actualUser.getId());

        while (cursor.moveToNext()){

            ScannedProduct newProduct = new ScannedProduct(cursor.getString(1),(float)cursor.getDouble(3),
            cursor.getInt(6), null);
            newProduct.setRefProductMariaDb(cursor.getInt(7));
            if(cursor.getBlob(5)!= null)
                newProduct.setSerializeImage(Base64.getEncoder().encodeToString(cursor.getBlob(5)));

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
