package com.example.ecocyam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ecocyam.R;

public class AddProductFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_form);

        Spinner spin = (Spinner) findViewById(R.id.spinnerCategorieProduitAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.item_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        CardView cardViewCreate = findViewById(R.id.cardViewAddProduct);
        cardViewCreate.setOnClickListener(v -> {
            //Appel API

        });
    }
}
