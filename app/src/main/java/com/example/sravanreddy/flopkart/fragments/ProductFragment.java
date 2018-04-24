package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sravanreddy.flopkart.network.AppController;
import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.adapters.ProductDetailsAdapter;
import com.example.sravanreddy.flopkart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sravanreddy on 4/13/18.
 */

public class ProductFragment extends Fragment{
   private RecyclerView recyclerView;
   private SharedPreferences msharedPreferences;
    private String url;
    private String cID, sCID, userId, appapiKey;
    private ArrayList<ProductDetails> productDetailsList;
    private LayoutAnimationController animation;
    private TextView toolBarTextView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBarTextView=getActivity().findViewById(R.id.toobar_textView);
        toolBarTextView.setText("Products");
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.recyclerview_animation);
        productDetailsList=new ArrayList<>();
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
        cID=msharedPreferences.getString("CID","");
        sCID=msharedPreferences.getString("SCID", "");
        userId=msharedPreferences.getString("ID", "");
        appapiKey=msharedPreferences.getString("api_key", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.product_fragment_layout, null);
        recyclerView=view.findViewById(R.id.recyclerView_productDetails);
        jsonRequest();
    return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        productDetailsList.clear();
    }

    private void jsonRequest() {
        url="http://rjtmobile.com/ansari/shopingcart/androidapp/product_details.php?cid="+cID+"&scid="+sCID+"&api_key="+appapiKey+"&user_id="+userId;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("products");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String pname=jsonObject.getString("pname");
                        String quantity=jsonObject.getString("quantity");
                        String prize=jsonObject.getString("prize");
                        String discription=jsonObject.getString("discription");
                        String image=jsonObject.getString("image");
                        productDetailsList.add(new ProductDetails(id, pname, quantity, prize,discription,image));
                    }

                    ProductDetailsAdapter myAdapter=new ProductDetailsAdapter(productDetailsList, getActivity() );
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutAnimation(animation);
                    gaggeredGridLayoutManager=new StaggeredGridLayoutManager(2,1);
                    recyclerView.setLayoutManager(gaggeredGridLayoutManager);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();

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
