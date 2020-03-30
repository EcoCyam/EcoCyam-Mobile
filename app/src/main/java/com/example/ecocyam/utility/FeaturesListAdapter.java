package com.example.ecocyam.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.ecocyam.R;

import java.util.List;

public class FeaturesListAdapter extends ArrayAdapter<String> {

    /* default */List<String> items;
    /* default */Context context;
    /* default */int resource;

    public FeaturesListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

        Button buttonArrow = (Button) view.findViewById(R.id.buttonArrow);
        TextView textViewTitleFeatureItem = (TextView) view.findViewById(R.id.textViewTitleFeatureItem);
        CardView cardView  = view.findViewById(R.id.cardviewFI);

        textViewTitleFeatureItem.setText(items.get(position));

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
}