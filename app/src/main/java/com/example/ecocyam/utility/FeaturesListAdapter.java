package com.example.ecocyam.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.interfaces.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class FeaturesListAdapter extends ArrayAdapter<String> {

    /* default */List<String> items;
    /* default */Context context;
    /* default */int resource;
    /* default */ScannedProduct product;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/evaluations/item";
    /* default */static final Logger log = Logger.getLogger(FeaturesListAdapter.class.getName());
    /* default */ HashMap<Integer, Double> scores = new HashMap<Integer, Double>();

    public FeaturesListAdapter(Context context, int resource, List<String> items, ScannedProduct product) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.product = product;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

        TextView textViewTitleFeatureItem = (TextView) view.findViewById(R.id.textViewTitleFeatureItem);
        TextView textViewRatingResultFeatureItem = view.findViewById(R.id.textViewRatingResultFeatureItem);
        TextView textViewTextResultFeatureItem = view.findViewById(R.id.textViewTextResultFeatureItem);
        ImageView imageViewProductHistory = view.findViewById(R.id.imageViewProductHistory);
        TextView productRating = view.findViewById(R.id.ProductRating);
        int refProductMariaDb = product.getRefProductMariaDb();
        if(position == 0){
            imageViewProductHistory.setImageResource(R.drawable.ecology_rating);
        }else if (position == 1){
            imageViewProductHistory.setImageResource(R.drawable.durability_rating);
        }else {
            imageViewProductHistory.setImageResource(R.drawable.user_rating);
        }
        getScore(refProductMariaDb, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                textViewTitleFeatureItem.setText(items.get(position));
                DecimalFormat df = new DecimalFormat("####.#");
                productRating.setText(String.valueOf(df.format(scores.get(position+1))));

                Drawable imgRating = null;
                if(scores.get(position+1) >= 4.0){
                    imgRating = getContext().getResources().getDrawable(R.drawable.color_circle_rating_good);
                    textViewTextResultFeatureItem.setText("Bon");
                } else if(scores.get(position+1) <= 2.9){
                    imgRating = getContext().getResources().getDrawable(R.drawable.color_circle_rating_bad);
                    textViewTextResultFeatureItem.setText("Mauvais");
                }else{
                    imgRating = getContext().getResources().getDrawable(R.drawable.color_circle_rating_neutral);
                    textViewTextResultFeatureItem.setText("Moyen");
                }
                textViewRatingResultFeatureItem.setCompoundDrawablesRelativeWithIntrinsicBounds(imgRating,null,null,null);


            }
        });
        return view;
    }

    public void getScore(int refProductMariaDb, final VolleyCallBack callBack) {
        String URL_with_id = URL.concat("/").concat(String.valueOf(refProductMariaDb));

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.GET, URL_with_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                log.fine(response.toString() + "connection success");
                for (int i = 0; i < response.length(); i++) {
                    try {
                       JSONObject jsonobject = response.getJSONObject(i);
                        scores.put(jsonobject.getJSONObject("criteria").getInt("criteriaId"), jsonobject.getDouble("score"));
                    } catch (JSONException e) {
                        log.info(e.getMessage());
                    }
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
        Volley.newRequestQueue(context).add(jsonObjReq);
    }
}