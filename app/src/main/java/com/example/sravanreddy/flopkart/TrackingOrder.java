package com.example.sravanreddy.flopkart;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackingOrder extends AppCompatActivity {
TextView orderStatus, shipmentID, billingAddress, itemName;
Button close;
ImageView tracking_image;
String shipmentStatus;
SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_order);
        mSharedPreferences=getSharedPreferences("user_local", MODE_PRIVATE);
        orderStatus=findViewById(R.id.order_status);
        shipmentID=findViewById(R.id.shipment_id);
        billingAddress=findViewById(R.id.billing_address);
        itemName=findViewById(R.id.item_name);
        tracking_image=findViewById(R.id.tracking_image);
        close=findViewById(R.id.close_tracking_button);
        billingAddress.setText("Billing Address: "+getIntent().getExtras().getString("Billing Address").toString());
        itemName.setText("Item Name: "+getIntent().getExtras().getString("Item Name").toString());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jsonRequest();
    }

    private void jsonRequest() {
        String url="http://rjtmobile.com/aamir/e-commerce/android-app/shipment_track.php?api_key="+mSharedPreferences.getString("api_key", "")+"&user_id="+mSharedPreferences.getString("ID","")+"&order_id="+getIntent().getExtras().getString("Order ID").toString();
        Log.i("URL", url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject temp=response;
                try {
                    JSONArray jsonArray=temp.getJSONArray("Shipment track");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        shipmentID.setText(jsonObject.getString("shipmentid"));
                        shipmentStatus=jsonObject.getString("shipmentstatus");
                    }

                    if(shipmentStatus.equals("1")){
                        tracking_image.setImageResource(R.drawable.order_confirmed);
                        orderStatus.setText("Order Confirmed");
                    }
                    else if((shipmentStatus.equals("2"))){
                        tracking_image.setImageResource(R.drawable.dispatched);
                        orderStatus.setText("Order Dispatched");
                    }
                    else if((shipmentStatus.equals("3"))){
                        tracking_image.setImageResource(R.drawable.on_the_way);
                        orderStatus.setText("On the way..");
                    }
                    else if((shipmentStatus.equals("4"))){
                        tracking_image.setImageResource(R.drawable.delivered);
                        orderStatus.setText("Delivered");
                    }
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
