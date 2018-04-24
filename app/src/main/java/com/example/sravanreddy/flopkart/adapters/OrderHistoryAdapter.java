package com.example.sravanreddy.flopkart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.model.OrderDescription;
import com.example.sravanreddy.flopkart.R;
import com.example.sravanreddy.flopkart.activity.TrackingOrder;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {
    ArrayList<OrderDescription> orderList;
    Context context;

    public OrderHistoryAdapter(ArrayList<OrderDescription> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_recyclerview_layout, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    final OrderDescription orderDescription=orderList.get(position);
    holder.orderId.setText("Order ID"+orderDescription.getOrderid());
    holder.orderName.setText(orderDescription.getName());
    holder.orderPrice.setText("$"+orderDescription.getPaidprice());
    holder.date.setText("Placed on: " +orderDescription.getPlacedon());
    holder.track.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent trackingActivity=new Intent(context, TrackingOrder.class);
        trackingActivity.putExtra("Item Name", orderDescription.getName());
        trackingActivity.putExtra("Billing Address", orderDescription.getBillingadd());
        trackingActivity.putExtra("Order ID", orderDescription.getOrderid());
        context.startActivity(trackingActivity);
        }
    });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView orderId, orderName, orderPrice, date;
    Button track;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.order_id_tv);
            orderName=itemView.findViewById(R.id.order_name_tv);
            orderPrice=itemView.findViewById(R.id.order_item_price_tv);
            date=itemView.findViewById(R.id.date);
            track=itemView.findViewById(R.id.track);
        }
    }
}
