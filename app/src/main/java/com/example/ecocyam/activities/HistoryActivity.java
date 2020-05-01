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
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.ProductHistoryAdapter;
import java.util.ArrayList;
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
        ScannedProduct product1 = new ScannedProduct(1,1,"Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                                            "APPLE",
                "05/03/2020",8.1f);

        ScannedProduct product2 = new ScannedProduct(2,1,"Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                "DELL",
                "05/03/2020",4.3f);

        ScannedProduct product3 = new ScannedProduct(3,1,"Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                "MICROSOFT",
                "05/03/2020",6.8f);

        db.deleteProductByRefId(String.valueOf(1)); //en attendant on delete à chaque fois les anciennes versions
        db.createProductWithId(product1);
        db.createProductWithId(product2);
        db.createProductWithId(product3);
        //FIN DU DUR

        //test à adapter selon le besoin
        //manipulation des images avec SQlite database : practise
        ConnectionTo.switchActivityWithStringExtra(this,SelectPictureProduct.class,"1");
        //fin de manipulation

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        Cursor cursor = db.getAllScannedProduct(actualUser.getId());

        while (cursor.moveToNext()){
            ScannedProduct newProduct = new ScannedProduct(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
            cursor.getString(3),cursor.getString(4),cursor.getFloat(5));
            productList.add(newProduct);
        }

        //creating recyclerview adapter
        ProductHistoryAdapter adapter = new ProductHistoryAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
