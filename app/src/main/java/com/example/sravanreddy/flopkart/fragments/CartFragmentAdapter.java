package com.example.sravanreddy.flopkart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.data.MyDBHelper;
import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CartFragmentAdapter extends RecyclerView.Adapter<CartFragmentAdapter.MyViewholder>{
public ArrayList<ProductDetails> cartList;
Context context;
int quant;
private MyDBHelper myDBHelper;
private SQLiteDatabase sqLiteDatabase;
private SharedPreferences sharedPreferences;

    public CartFragmentAdapter(ArrayList<ProductDetails> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        myDBHelper= MyDBHelper.getInstance(context.getApplicationContext());
        sqLiteDatabase=myDBHelper.getWritableDatabase();
        sharedPreferences=context.getSharedPreferences("user_local", Context.MODE_PRIVATE);
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerlist_layout, null);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {
    holder.productName.setText(cartList.get(position).getName());
    holder.productPrice.setText("$"+cartList.get(position).getPrice());
        Picasso.get().load(cartList.get(position).getImage()).into(holder.productImage);
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant=Integer.parseInt(holder.quantityTv.getText().toString());
                quant++;
                holder.quantityTv.setText(quant+"");
                String pQuery= "UPDATE "+ MyDBHelper.TABLE_NAME+" SET "+
                        MyDBHelper.QUANTITY+ " = ? WHERE "+ MyDBHelper.UserID+" = " +Integer.parseInt(sharedPreferences.getString("ID", "")) +" AND "+ MyDBHelper.ID+ " = "+Integer.parseInt(cartList.get(position).getId());

                sqLiteDatabase.execSQL(pQuery, new String[]{holder.quantityTv.getText().toString()});
                EventBus.getDefault().post("Updated");
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quant>1) {
                    quant = Integer.parseInt(holder.quantityTv.getText().toString());
                    quant--;
                    holder.quantityTv.setText(quant + "");
                    String pQuery= "UPDATE "+ MyDBHelper.TABLE_NAME+" SET "+
                            MyDBHelper.QUANTITY+ " = ? WHERE "+ MyDBHelper.UserID+" = " +Integer.parseInt(sharedPreferences.getString("ID", "")) +" AND "+ MyDBHelper.ID+ " = "+Integer.parseInt(cartList.get(position).getId());

                    sqLiteDatabase.execSQL(pQuery, new String[]{holder.quantityTv.getText().toString()});
                    EventBus.getDefault().post(cartList.get(position).getId());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
    TextView productPrice, productName, quantityTv;
    ImageView productImage;
    ImageButton increase, decrease;
        public MyViewholder(View itemView) {
            super(itemView);
            productPrice=itemView.findViewById(R.id.productPrice_cart_recyclerList);
            productName=itemView.findViewById(R.id.productName_cart_recyclerList);
            productImage=itemView.findViewById(R.id.image_cart_recylerList);
            increase=itemView.findViewById(R.id.increase_quantity);
            decrease=itemView.findViewById(R.id.decrease_quantity);
            quantityTv=itemView.findViewById(R.id.quantity);
        }
    }
}
