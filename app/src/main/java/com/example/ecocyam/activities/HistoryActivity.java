package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.example.ecocyam.R;

import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.localdatabase.DatabaseHelperSingleton;
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


        db.deleteProductByRefId(String.valueOf(1)); //en attendant on delete
        db.createProdcut(product1);
        db.createProdcut(product2);
        db.createProdcut(product3);
        //FIN DU DUR


        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        Cursor cursor = db.getAllScannedProduct();

        while (cursor.moveToNext()){
            ScannedProduct newProduct = new ScannedProduct(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
            cursor.getString(3),cursor.getString(4),cursor.getFloat(5));
            productList.add(newProduct);
        }
/*
        //adding some items to our list
        productList.add(
                new ProductHistory(
                        1,
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "APPLE",
                        8.1,
                        "21/12/2019",
                        R.drawable.icon_phone));

        productList.add(
                new ProductHistory(
                        1,
                        "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                        "DELL",
                        4.3,
                        "01/02/2020",
                        R.drawable.icon_electrique));

        productList.add(
                new ProductHistory(
                        1,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "MICROSOFT",
                        6.8,
                        "22/01/2020",
                        R.drawable.icon_computer));

 */

        //creating recyclerview adapter
        ProductHistoryAdapter adapter = new ProductHistoryAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
