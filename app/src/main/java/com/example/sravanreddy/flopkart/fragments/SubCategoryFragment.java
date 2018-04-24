package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.example.sravanreddy.flopkart.adapters.MyAdapterSubClass;
import com.example.sravanreddy.flopkart.R;
import com.example.sravanreddy.flopkart.model.SubCategoryClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sravanreddy on 4/12/18.
 */

public class SubCategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private ArrayList<SubCategoryClass> subCategoriesList=new ArrayList<>();
    private SharedPreferences msharedPreferences;
    private String url, cid, appapi_key, user_id;
    private LayoutAnimationController animation;
    private TextView toolbarTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarTextView=getActivity().findViewById(R.id.toobar_textView);
        toolbarTextView.setText("Categories");
        subCategoriesList.clear();
        cid=getArguments().getString("CID");
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        subCategoriesList.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.subcategory_fragment, null);
        user_id=msharedPreferences.getString("ID", "");
        appapi_key=msharedPreferences.getString("api_key","");
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.recyclerview_animation);
        recyclerView=view.findViewById(R.id.recyclerView_subcategory);
        requestJsonData();
        return view;
    }



    private void requestJsonData() {
        url="http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+cid+"&api_key="+appapi_key+"&user_id="+user_id;

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject temp=(JSONObject)response;

                JSONArray jsonArray= null;
                try {
                    jsonArray = temp.getJSONArray("subcategory");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject subCategory=jsonArray.getJSONObject(i);
                        String scid=subCategory.getString("scid");
                        String scname=subCategory.getString("scname");
                        String scdiscription=subCategory.getString("scdiscription");
                        String scimageurl=subCategory.getString("scimageurl");
                        subCategoriesList.add(new SubCategoryClass(scid,scname,scdiscription,scimageurl));
                    }


                    MyAdapterSubClass myAdapter=new MyAdapterSubClass(subCategoriesList, getActivity());
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
                Log.i("Error Response", error.toString());
            }
        });
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }


}
