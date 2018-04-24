package com.example.sravanreddy.flopkart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WishListFragmentAdapter extends RecyclerView.Adapter<WishListFragmentAdapter.MyViewHolder> {
    public ArrayList<ProductDetails> productDetailsList;
    private Context context;

    public WishListFragmentAdapter(ArrayList<ProductDetails> productDetailsList, Context context) {
        this.productDetailsList = productDetailsList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_list_recylcerview_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.get().load(productDetailsList.get(position).getImage()).into(holder.itemImage);
        holder.itemName.setText(productDetailsList.get(position).getName());
        holder.itemPrice.setText("$"+productDetailsList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView itemName, itemPrice;
    ImageView itemImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.productName_wishlist);
            itemPrice=itemView.findViewById(R.id.productPrice_wishlist);
            itemImage=itemView.findViewById(R.id.image_wishlist);
        }
    }
}
