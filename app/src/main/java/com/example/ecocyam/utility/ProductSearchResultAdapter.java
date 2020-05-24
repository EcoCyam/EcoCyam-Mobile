package com.example.ecocyam.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecocyam.R;
import com.example.ecocyam.activities.ProductFeaturesActivity;
import com.example.ecocyam.entities.ScannedProduct;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

public class ProductSearchResultAdapter extends RecyclerView.Adapter<ProductSearchResultAdapter.ProductViewHolder> {


    /* default */private Context mCtx;
    /* default */private List<ScannedProduct> productList;

    public ProductSearchResultAdapter(Context mCtx, List<ScannedProduct> productList) {
        super();
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.searchresult_products_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        ScannedProduct product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitleProductSearchResult.setText(product.getTitle());
        holder.textViewProductSearchResultMarque.setText(product.getMarque());
        DecimalFormat df = new DecimalFormat("####.##");
        holder.textViewRatingProductSearchResult.setText(String.valueOf(df.format(product.getRating())));

        byte[] productImageBin = Base64.getDecoder().decode(product.getSerializeImage());
        holder.imageViewProductSearchResult.setImageBitmap(PictureFormatting.getBitmap(productImageBin));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTo.switchActivityWithObejctExtra(getmCtx(),ProductFeaturesActivity.class, product);
               // product.getId(); //aller chercher en base et passer les elements nécessaires à l'activity
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    /* default */class ProductViewHolder extends RecyclerView.ViewHolder {

        /* default */ TextView textViewTitleProductSearchResult, textViewProductSearchResultMarque, textViewRatingProductSearchResult;
        /* default */ ImageView imageViewProductSearchResult;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitleProductSearchResult = itemView.findViewById(R.id.textViewTitleProductSearchResult);
            textViewProductSearchResultMarque = itemView.findViewById(R.id.textViewProductSearchResultMarque);
            textViewRatingProductSearchResult = itemView.findViewById(R.id.textViewRatingProductSearchResult);
            imageViewProductSearchResult = itemView.findViewById(R.id.imageViewProductSearchResult);
        }
    }


    public Context getmCtx() {
        return mCtx;
    }
}