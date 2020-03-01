package com.example.ecocyam.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecocyam.R;
import com.example.ecocyam.entities.ProductHistory;

import java.util.List;

public class ProductHistoryAdapter extends RecyclerView.Adapter<ProductHistoryAdapter.ProductViewHolder> {


    /* default */private Context mCtx;
    /* default */private List<ProductHistory> productList;

    public ProductHistoryAdapter(Context mCtx, List<ProductHistory> productList) {
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
        ProductHistory product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitleProductHistory.setText(product.getTitle());
        holder.textViewProductHistoryMarque.setText(product.getMarque());
        holder.textViewRatingProductHistory.setText(String.valueOf(product.getRating()));
        holder.textViewProductDateScanHistory.setText(String.valueOf(product.getDateScan()));

        holder.imageViewProductHistory.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

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