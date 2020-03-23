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

    //the list values in the List of type hero
    List<String> items;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public FeaturesListAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    //this will return the ListView Item as a View

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);


        Button buttonArrow = (Button) view.findViewById(R.id.buttonArrow);
        TextView textViewTitleFeatureItem = (TextView) view.findViewById(R.id.textViewTitleFeatureItem);
        CardView cardView  = view.findViewById(R.id.cardviewFI);

        textViewTitleFeatureItem.setText(items.get(position));

        final String name = items.get(position);

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
        //finally returning the view
        return view;
    }
}