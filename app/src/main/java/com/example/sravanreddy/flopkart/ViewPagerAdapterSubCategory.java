package com.example.sravanreddy.flopkart;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sravanreddy on 4/12/18.
 */

public class ViewPagerAdapterSubCategory extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<SubCategoryClass> catogories;
    public ViewPagerAdapterSubCategory(Context context, ArrayList<SubCategoryClass> catogories) {
        this.context = context;
        this.catogories=catogories;
    }

    @Override
    public int getCount() {
        return catogories.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_photoslide, null);
        ImageView imageView=view.findViewById(R.id.imageView2);
        TextView description=view.findViewById(R.id.description);
        description.setText(catogories.get(position).getScname());
        Picasso.get().load(catogories.get(position).getScimageurl()).placeholder(R.drawable.no_preview).into(imageView);
        ViewPager vp= (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
