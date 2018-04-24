package com.example.sravanreddy.flopkart.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sravanreddy.flopkart.network.AppController;
import com.example.sravanreddy.flopkart.fragments.HomeFragment;
import com.example.sravanreddy.flopkart.model.OrderDescription;
import com.example.sravanreddy.flopkart.adapters.OrderHistoryAdapter;
import com.example.sravanreddy.flopkart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrdersHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<OrderDescription> orderHistoryList;
    SharedPreferences msharedPreferences;
    Button close;
    TextView textView;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        textView=findViewById(R.id.order_history_toolbar_text);
        textView.setText("Order History");
        toolbar=findViewById(R.id.order_histoy_toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.order_history_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        close=findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment=new HomeFragment();
               finish();
            }
        });
        orderHistoryList=new ArrayList<>();
        msharedPreferences=getSharedPreferences("user_local", Context.MODE_PRIVATE);
        jsonResquest();
    }

    private void jsonResquest() {
        String url="http://rjtmobile.com/aamir/e-commerce/android-app/order_history.php?api_key="+msharedPreferences.getString("api_key", "")+"&user_id="+msharedPreferences.getString("ID", "")+"&mobile="+msharedPreferences.getString("Mobile", "");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray temp=response.getJSONArray("Order history");
                    for(int i=0;i<temp.length();i++){
                        JSONObject jsonObject=temp.getJSONObject(i);
                        orderHistoryList.add(new OrderDescription(
                                jsonObject.getString("orderid"),
                                jsonObject.getString("orderstatus"),
                                jsonObject.getString("name"),
                                jsonObject.getString("billingadd"),
                                jsonObject.getString("deliveryadd"),
                                jsonObject.getString("mobile"),
                                jsonObject.getString("email"),
                                jsonObject.getString("itemid"),
                                jsonObject.getString("itemname"),
                                jsonObject.getString("itemquantity"),
                                jsonObject.getString("totalprice"),
                                jsonObject.getString("paidprice"),
                                jsonObject.getString("placedon")
                        ));
                    }
                    OrderHistoryAdapter orderHistoryAdapter=new OrderHistoryAdapter(orderHistoryList, OrdersHistoryActivity.this);
                    recyclerView.setAdapter(orderHistoryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getmInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
