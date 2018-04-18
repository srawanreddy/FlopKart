package com.example.sravanreddy.flopkart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewHolder>{

    ArrayList<ProductDetails> myList;
    Context context;
    SharedPreferences mSharedPreferences;

    public ProductDetailsAdapter(ArrayList<ProductDetails> myList, Context context) {
        this.myList = myList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mSharedPreferences=context.getSharedPreferences("user_local", Context.MODE_PRIVATE);
    final ProductDetails productDetails=myList.get(position);
    holder.name_productDetails.setText(productDetails.getName());
    Picasso.get().load(productDetails.getImage()).placeholder(R.drawable.no_preview).into(holder.imageView_productDetails);
    holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(productDetails);
        }
    });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView_productDetails;
        private TextView name_productDetails;
        private ConstraintLayout constraintLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_productDetails=itemView.findViewById(R.id.imageView);
            name_productDetails=itemView.findViewById(R.id.main_textView);
            constraintLayout =itemView.findViewById(R.id.list_layout);
        }
    }
}
