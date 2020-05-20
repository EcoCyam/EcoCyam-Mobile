package com.example.ecocyam.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.R;
import com.example.ecocyam.activities.AddProductFormActivity;
import com.example.ecocyam.activities.SearchResultActivity;
import com.example.ecocyam.entities.EvaluationScore;
import com.example.ecocyam.entities.Product;
import com.example.ecocyam.entities.ScannedProduct;
import com.example.ecocyam.interfaces.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        Button buttonArrow = (Button) view.findViewById(R.id.buttonArrow);
        TextView textViewTitleFeatureItem = (TextView) view.findViewById(R.id.textViewTitleFeatureItem);
        TextView textViewRatingResultFeatureItem = view.findViewById(R.id.textViewRatingResultFeatureItem);
        int refProductMariaDb = product.getRefProductMariaDb();
        EvaluationScore score = getScore(refProductMariaDb, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                textViewTitleFeatureItem.setText(items.get(position));
                textViewRatingResultFeatureItem.setText(String.valueOf(scores.get(position+1)));
            }
        });

        CardView cardView  = view.findViewById(R.id.cardviewFI);

        // String name = items.get(position);

        buttonArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.VISIBLE);
                    buttonArrow.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.GONE);
                    buttonArrow.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
        return view;
    }

    public EvaluationScore getScore(int refProductMariaDb, final VolleyCallBack callBack) {
        String URL_with_id = URL.concat("/").concat(String.valueOf(refProductMariaDb));

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.GET, URL_with_id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                log.fine(response.toString() + "connection success");
                for (int i = 0; i < response.length(); i++) {
                    try {
                       JSONObject jsonobject = response.getJSONObject(i);
                        scores.put(jsonobject.getJSONObject("criteria").getInt("criteriaId"), jsonobject.getDouble("score"));
                        log.info("good" + jsonobject);
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
        log.info("nice");
        return null;
    }
}