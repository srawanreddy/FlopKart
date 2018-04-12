package com.example.sravanreddy.flopkart;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sravanreddy on 4/11/18.
 */

public class HomeFragment extends Fragment {
ViewPager photoSlider;
Handler handler;
ViewPagerAdapter viewPagerAdapter;
private RecyclerView recyclerView;
private StaggeredGridLayoutManager gaggeredGridLayoutManager;
private ArrayList<Catogories> catogoriesList=new ArrayList<>();
private SharedPreferences msharedPreferences;
private String url;
private LayoutAnimationController animation;
private int page=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.home_fragment, null);
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.recyclerview_animation);
        msharedPreferences= getActivity().getSharedPreferences("user_local", MODE_PRIVATE);
        Log.i("Shared Preferrences", msharedPreferences.getString("api_key", ""));
        Log.i("Shared Preferrences", msharedPreferences.getString("ID", ""));
        populateCategoryData();
        photoSlider=view.findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getActivity());
        photoSlider.setAdapter(viewPagerAdapter);
        recyclerView=view.findViewById(R.id.recyclerView);
        handler=new Handler();
        return view;
    }

    private void populateCategoryData() {
        url="http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key="+msharedPreferences.getString("api_key", "")+"&user_id="+msharedPreferences.getString("ID", "");

        JsonObjectRequest jsonRequest= new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                JSONObject temp=(JSONObject)response;
                try {
                    JSONArray jsonArray=temp.getJSONArray("category");
                    if(jsonArray==null){
                        Log.i("category", temp.getString("msg"));
                    }else{
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            Catogories categories=new Catogories(
                                    jsonObject.getString("cid"),
                                    jsonObject.getString("cname"),
                                    jsonObject.getString("cdiscription"),
                                    jsonObject.getString("cimagerl"));
                            Log.i("category", categories.getCid());
                            catogoriesList.add(categories);

                        }

                        MyAdapter myAdapter=new MyAdapter(catogoriesList, getActivity());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutAnimation(animation);
                        gaggeredGridLayoutManager=new StaggeredGridLayoutManager(2,1);
                        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
                        recyclerView.setAdapter(myAdapter);
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
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonRequest);

    }

    Runnable runnable = new Runnable() {
        public void run() {
            if (viewPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            photoSlider.setCurrentItem(page, true);
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }
}
