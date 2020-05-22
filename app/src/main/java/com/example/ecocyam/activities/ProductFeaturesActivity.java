package com.example.ecocyam.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.entities.User;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;
import com.example.ecocyam.utility.FeaturesListAdapter;
import com.example.ecocyam.utility.PictureFormatting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Logger;

public class ProductFeaturesActivity extends AppCompatActivity {
    /* default */ ListView list;
    /* default */ ArrayList<String> itemsComputer;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/evaluations/";
    /* default */static final Logger log = Logger.getLogger(ProductFeaturesActivity.class.getName());
    /* default */ScannedProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_features);

        Intent intent = getIntent();
        product = (ScannedProduct) intent.getSerializableExtra("product");

        list = (ListView) findViewById(R.id.listViewPF);
        TextView textViewProductName = (TextView) findViewById(R.id.textViewProductNamePF);
        TextView textViewProductRating = (TextView) findViewById(R.id.textViewProductRatingPF);
        TextView textViewProductMarque = (TextView) findViewById(R.id.textViewProductMarquePF);
        ImageView imageViewProductPF = findViewById(R.id.imageViewProductPF);

        textViewProductName.setText(product.getTitle());
        DecimalFormat df = new DecimalFormat("####.##");
        textViewProductRating.setText(String.valueOf(df.format(product.getRating())) + "/5");
        log.info("SCANNED PRODUCT OVERALLSCORE ::: " + String.valueOf(df.format(product.getRating())));
        Drawable imgRating = null;
        if(product.getRating() > 4){
            imgRating = this.getApplicationContext().getResources().getDrawable(R.drawable.color_circle_rating_good);
        } else if(product.getRating() < 2.9){
            imgRating = this.getApplicationContext().getResources().getDrawable(R.drawable.color_circle_rating_bad);
        }else{
            imgRating = this.getApplicationContext().getResources().getDrawable(R.drawable.color_circle_rating_neutral);
        }
        textViewProductRating.setCompoundDrawablesRelativeWithIntrinsicBounds(imgRating,null,null,null);

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
                            refreshData(dialog);
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

    private void refreshData(DialogInterface dialog){
        this.resetProduct(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                dialog.dismiss();
                Toast.makeText(ProductFeaturesActivity.this.getApplicationContext(), "Votre avis a été envoyé avec succès", Toast.LENGTH_LONG).show();
                finish();
                getIntent().removeExtra("product");
                ConnectionTo.switchActivityWithObejctExtra(getApplicationContext(),ProductFeaturesActivity.class, product);
            }
        });
        //this.recreate();
    }

    private void resetProduct(final VolleyCallBack callBack){
        String URL3 = "https://ecocyam-web.cfapps.io/api/items";
        String URL_with_id = URL3.concat("/").concat(String.valueOf(product.getRefProductMariaDb()));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(URL_with_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int itemId = response.getInt("itemId");
                    String title = response.getString("name");
                    double rating = Double.parseDouble(response.getString("overallScore"));
                    log.info("SCANNED PRODUCT OVERALLSCORE ACTUEL ::: " + String.valueOf(rating));

                    product = new ScannedProduct(title,(float)rating,null);
                    product.setSerializeImage(response.getString("image"));
                    product.setRefProductMariaDb(itemId);
                } catch (JSONException e) {
                    log.info("error getting response");
                }
                callBack.onSuccess();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log.fine("Error: " + error.getMessage());
                    }
                });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0,0,0));
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
    }

}