package com.example.sravanreddy.flopkart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderConfirmationAdapter extends RecyclerView.Adapter<OrderConfirmationAdapter.MyViewHolder>{
    ArrayList<OrderConfirmation> myList;
    Context context;

    public OrderConfirmationAdapter(ArrayList<OrderConfirmation> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_confirmation_recyclerview_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.orderId.setText("Order ID: "+myList.get(position).getOrderid());
        holder.itemName.setText("Product Name: "+myList.get(position).getItemname());
        holder.amountPait.setText("$"+myList.get(position).getPaidprice());
        holder.placedOn.setText("Date of Order: "+myList.get(position).getPlacedon());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView orderId, itemName, amountPait, placedOn;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.order_id_confirmation);
            itemName=itemView.findViewById(R.id.itemName_confirmation);
            amountPait=itemView.findViewById(R.id.amount_paid_confirmation);
            placedOn=itemView.findViewById(R.id.placedOn_confirmation);
        }
    }
}
