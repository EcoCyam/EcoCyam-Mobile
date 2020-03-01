package com.example.ecocyam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecocyam.entities.ProductHistory;
import com.example.ecocyam.utility.ProductHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    //a list to store all the products
    List<ProductHistory> productList;

    //the recyclerview
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


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

        //creating recyclerview adapter
        ProductHistoryAdapter adapter = new ProductHistoryAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
