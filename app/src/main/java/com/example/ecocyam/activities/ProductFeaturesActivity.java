package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ecocyam.R;
import com.example.ecocyam.utility.FeaturesListAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ProductFeaturesActivity extends AppCompatActivity {
    ListView list;
    ArrayList<String> itemsComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_features);

        //Switch CASE computer, etc..

        list = (ListView) findViewById(R.id.listViewPF);
        itemsComputer = new ArrayList<String>();
        itemsComputer.add("Note impact écologique");
        itemsComputer.add("Durabilité");
        itemsComputer.add("Consommation");
        FeaturesListAdapter adp = new FeaturesListAdapter(this, R.layout.features_item, itemsComputer);

        list.setAdapter(adp);

    }

}