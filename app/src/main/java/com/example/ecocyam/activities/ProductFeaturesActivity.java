package com.example.ecocyam.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.FeaturesListAdapter;
import com.example.ecocyam.utility.PictureFormatting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Logger;

public class ProductFeaturesActivity extends AppCompatActivity {
    /* default */ ListView list;
    /* default */ ArrayList<String> itemsComputer;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/evaluations/";
    /* default */static final Logger log = Logger.getLogger(ProductFeaturesActivity.class.getName());

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

        Button buttonAddComment = findViewById(R.id.button_add_comment);
        buttonAddComment.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductFeaturesActivity.this);
            LayoutInflater inflater = this.getLayoutInflater();
            View addCommentLayout = inflater.inflate(R.layout.activity_send_customer_comment, null);
            TextView scoreUser = (TextView) addCommentLayout.findViewById(R.id.custommer_score);
            TextView commentUser = (TextView) addCommentLayout.findViewById(R.id.custommer_coment);

            alertDialog.setView(addCommentLayout);

            alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    JSONObject requestJsonObject = new JSONObject();
                    try {
                        requestJsonObject.put("itemId", product.getRefProductMariaDb());
                        requestJsonObject.put("score", scoreUser.getText());
                        requestJsonObject.put("comment", commentUser.getText());

                    } catch (JSONException e) {
                        log.fine(e.getMessage());
                    }

                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            log.fine(response.toString() + " connection success");
                            dialog.dismiss();
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    log.fine("Error: " + error.getMessage());
                                }
                            });
                    Volley.newRequestQueue(getBaseContext()).add(jsonObjReq);
                }
            });

            alertDialog.show();
        });

        itemsComputer = new ArrayList<String>();

        itemsComputer.add("Ecologique");
        itemsComputer.add("Durabilité");
        itemsComputer.add("Note utilisateurs");
        FeaturesListAdapter adp = new FeaturesListAdapter(this, R.layout.features_item, itemsComputer,product);

        list.setAdapter(adp);

    }

}