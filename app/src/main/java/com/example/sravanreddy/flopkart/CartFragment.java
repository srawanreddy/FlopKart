package com.example.sravanreddy.flopkart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CartFragment extends Fragment implements View.OnClickListener {
private ArrayList<ProductDetails> productDetailsList=new ArrayList<>();
private ArrayList<OrderConfirmation> orderConfirmationList=new ArrayList<>();
private float totalPrice=0f, taxValue=0;
private TextView price, toolbarTextView, coupon_code, tax;
private SQLiteDatabase sqLiteDatabase;
private String clientToke;
HashMap<String, String> paramHash;
 private MyDBHelper myDBHelper;
 private Button apply_coupon_button;
 private SharedPreferences msharedPreferences;
 CartFragmentAdapter cartFragmentAdapter;
 RecyclerView recyclerView;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
 private Button checkout_button;
    private static final String GET_TOKEN_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/main.php?";
    private static final String SEND_PAYMENT_DETAIL_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/mycheckout.php?";
 int quant;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarTextView=getActivity().findViewById(R.id.toobar_textView);
        toolbarTextView.setText("Cart");
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onRecieveEvent(String s){

        int user_id=Integer.parseInt(msharedPreferences.getString("ID", ""));
        totalPrice=0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDBHelper.TABLE_NAME + " WHERE " + MyDBHelper.UserID+" = "+user_id, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
            do {
                ProductDetails productDetails=new ProductDetails(cursor.getString(cursor.getColumnIndex(MyDBHelper.ID))+"",
                        cursor.getString(cursor.getColumnIndex(MyDBHelper.NAME)),
                        cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(MyDBHelper.PRICE)),
                        cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(MyDBHelper.IMAGE))
                );
                totalPrice=totalPrice+(Integer.parseInt(productDetails.getPrice()) * Integer.parseInt(productDetails.getQuantity()));
                Toast.makeText(getActivity(), productDetails.getQuantity(), Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());

        taxValue=(totalPrice*10)/100;
        tax.setText(taxValue+"");
        price.setText("$"+(totalPrice+taxValue));
        cartFragmentAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.cart_fragment_layout, null);
        price=view.findViewById(R.id.price);
        tax=view.findViewById(R.id.tax);
        checkout_button=view.findViewById(R.id.checkout_button);
        totalPrice=0;
        coupon_code=view.findViewById(R.id.coupon_code);
        apply_coupon_button=view.findViewById(R.id.apply_coupon_button);
        apply_coupon_button.setOnClickListener(this);
        myDBHelper = MyDBHelper.getInstance(getActivity().getApplicationContext());
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        int user_id=Integer.parseInt(msharedPreferences.getString("ID", ""));
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDBHelper.TABLE_NAME + " WHERE " + MyDBHelper.UserID+" = "+user_id, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        do {
            ProductDetails productDetails=new ProductDetails(cursor.getString(cursor.getColumnIndex(MyDBHelper.ID))+"",
                    cursor.getString(cursor.getColumnIndex(MyDBHelper.NAME)),
                    cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(MyDBHelper.PRICE)),
                    cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(MyDBHelper.IMAGE))
                    );
            Log.i("OnCreate View", "Price: "+productDetails.getPrice()+" Quantity "+productDetails.getQuantity());
            totalPrice=totalPrice+(Integer.parseInt(productDetails.getPrice()) * Integer.parseInt(productDetails.getQuantity()));
            productDetailsList.add(productDetails);
        } while (cursor.moveToNext());
        Toast.makeText(getActivity(), totalPrice+"", Toast.LENGTH_SHORT).show();
        taxValue=(totalPrice*10)/100;
        tax.setText(taxValue+"");
        price.setText("$"+(totalPrice+taxValue));
        recyclerView=view.findViewById(R.id.cart_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
         cartFragmentAdapter=new CartFragmentAdapter(productDetailsList, getActivity());
        final SwipeController swipeController;

        recyclerView.setAdapter(cartFragmentAdapter);
        swipeController = new SwipeController(new SwipeControllerAction() {
            @Override
            public void onRightClicked(int position) {
                totalPrice=0;
                sqLiteDatabase.delete(MyDBHelper.TABLE_NAME, MyDBHelper.ID+"="+productDetailsList.get(position).getId(),null);
                cartFragmentAdapter.cartList.remove(position);
                cartFragmentAdapter.notifyItemRemoved(position);
                cartFragmentAdapter.notifyItemRangeChanged(position, cartFragmentAdapter.getItemCount());
                int user_id=Integer.parseInt(msharedPreferences.getString("ID", ""));
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyDBHelper.TABLE_NAME + " WHERE " + MyDBHelper.UserID+" = "+user_id, null);
                cursor.moveToFirst();
                if(cursor.getCount()>0)
                    do {
                        ProductDetails productDetails=new ProductDetails(cursor.getString(cursor.getColumnIndex(MyDBHelper.ID))+"",
                                cursor.getString(cursor.getColumnIndex(MyDBHelper.NAME)),
                                cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY)),
                                cursor.getString(cursor.getColumnIndex(MyDBHelper.PRICE)),
                                cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(MyDBHelper.IMAGE))
                        );
                        totalPrice=totalPrice+(Integer.parseInt(productDetails.getPrice()) * Integer.parseInt(productDetails.getQuantity()));
                    } while (cursor.moveToNext());
                taxValue= (totalPrice*10)/100;
                tax.setText(taxValue+"");
                price.setText("$"+(totalPrice+taxValue));
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        checkout_button.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.apply_coupon_button:
                jsonRequest();
                break;
            case R.id.checkout_button:
                getToken();
                break;
        }
    }



    private void jsonRequest() {
        String url="http://rjtmobile.com/aamir/e-commerce/android-app/coupon.php?api_key="+msharedPreferences.getString("api_key", "")+"&user_id="+msharedPreferences.getString("ID", "")+"&couponno="+coupon_code.getText().toString();
        JsonObjectRequest jsonObject=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            String discount;
            @Override
            public void onResponse(JSONObject response) {
            JSONObject temp=response;
                try {
                    JSONArray jsonArray=temp.getJSONArray("Coupon discount");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String code=obj.getString("couponno");
                         discount=obj.getString("discount");
                    }
                    apply_coupon_button.setClickable(false);
                    totalPrice=totalPrice-((totalPrice*Integer.parseInt(discount))/100);
                    taxValue=(totalPrice*10)/100;
                    tax.setText(taxValue+"");
                    price.setText("$"+(totalPrice+taxValue));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error response", error.toString());
                coupon_code.setText("Invalid Coupon");
                coupon_code.setBackgroundColor(getResources().getColor(R.color.error));
                coupon_code.setTextColor(getResources().getColor(R.color.icons));
                coupon_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        coupon_code.setText("");
                        coupon_code.setHint("Coupon Code");
                        coupon_code.setBackgroundColor(getResources().getColor(R.color.icons));
                        coupon_code.setTextColor(getResources().getColor(R.color.primaryText));
                    }
                });
            }
        });
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObject);
    }
    private void getToken() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, GET_TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("String Request Response", response.toString());
                clientToke=response.toString();
                DropInRequest dropInRequest= new DropInRequest().clientToken(clientToke);
                startActivityForResult(dropInRequest.getIntent(getActivity()), BRAINTREE_REQUEST_CODE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("String Request Error", error.toString());

            }
        });
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==BRAINTREE_REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){
                DropInResult result=data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                paramHash = new HashMap<>();
                paramHash.put("amount", totalPrice+"");
                paramHash.put("nonce", paymentNonce);
                //send payment infromation to braintree app server
                Log.i("Success from braintree", "Success");
                sendPaymentNonceToServer();
                sendOrderToServer();

            }
            else if(resultCode==Activity.RESULT_CANCELED){/*cancelled*/}
            else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    private void sendPaymentNonceToServer() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, SEND_PAYMENT_DETAIL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Successful")){
                    Log.i("Order Success", "Final Response: " + response);
                    sendOrderToServer();

                }
                else
                    Log.i("Order UnSuccess", "Final Response: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
        };
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void sendOrderToServer()
    {
        Iterator<ProductDetails> itr=productDetailsList.iterator();
        while (itr.hasNext()){
            ProductDetails productDetails=itr.next();
            String url="http://rjtmobile.com/aamir/e-commerce/android-app/orders.php?&item_id="+productDetails.getId()+"&item_names="+productDetails.getName()+"&item_quantity="+productDetails.getQuantity()+"&final_price="+productDetails.getPrice()+"&&api_key="+msharedPreferences.getString("api_key", "")+"&user_id="+msharedPreferences.getString("ID", "")+"&user_name="+msharedPreferences.getString("FullName", "")+"&billingadd="+msharedPreferences.getString("Address", "")+"&deliveryadd="+msharedPreferences.getString("Address", "")+"&mobile="+msharedPreferences.getString("Mobile", "")+"&email="+msharedPreferences.getString("Email", "");
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray=response.getJSONArray("Order confirmed");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            OrderConfirmation orderConfirmation=new OrderConfirmation(
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
                            );
                            orderConfirmationList.add(orderConfirmation);
                            Bundle bundle=new Bundle();
                            bundle.putParcelableArrayList("Order confirmation list", orderConfirmationList);
                            OrderConfirmationFragment orderConfirmationFragment=new OrderConfirmationFragment();
                            Toast.makeText(getActivity(), "Order Success", Toast.LENGTH_LONG).show();
                            sqLiteDatabase.delete(MyDBHelper.TABLE_NAME, null, null);
                            productDetailsList.clear();
                            cartFragmentAdapter.notifyDataSetChanged();
                            orderConfirmationFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment, orderConfirmationFragment).addToBackStack("").commit();
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

            AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }

    }


}
