package com.example.ecocyam.utility;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecocyam.R;
import com.example.ecocyam.activities.ProductFeaturesActivity;
import com.example.ecocyam.entities.ScannedProduct;

import java.util.List;

public final class ProductHistoryAdapter extends RecyclerView.Adapter<ProductHistoryAdapter.ProductViewHolder> {


    /* default */private Context mCtx;
    /* default */private List<ScannedProduct> productList;

    public ProductHistoryAdapter(Context mCtx, List<ScannedProduct> productList) {
        super();
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.history_products_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        ScannedProduct product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitleProductHistory.setText(product.getTitle());
        holder.textViewProductHistoryMarque.setText(product.getMarque());
        holder.textViewRatingProductHistory.setText(String.valueOf(product.getRating()));
        holder.textViewProductDateScanHistory.setText(String.valueOf(product.getLocalDate()));

       // holder.imageViewProductHistory.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), ProductFeaturesActivity.class);
                mCtx.startActivity(intent);
                // product.getId(); //aller chercher en base et passer les elements nécessaires à l'activity
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    /* default */class ProductViewHolder extends RecyclerView.ViewHolder {

        /* default */ TextView textViewTitleProductHistory, textViewProductHistoryMarque, textViewRatingProductHistory, textViewProductDateScanHistory;
        /* default */ ImageView imageViewProductHistory;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitleProductHistory = itemView.findViewById(R.id.textViewTitleProductHistory);
            textViewProductHistoryMarque = itemView.findViewById(R.id.textViewProductHistoryMarque);
            textViewRatingProductHistory = itemView.findViewById(R.id.textViewRatingProductHistory);
            textViewProductDateScanHistory = itemView.findViewById(R.id.textViewProductDateScanHistory);
            imageViewProductHistory = itemView.findViewById(R.id.imageViewProductHistory);
        }
    }
}