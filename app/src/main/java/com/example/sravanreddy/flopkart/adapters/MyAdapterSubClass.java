package com.example.sravanreddy.flopkart.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.events.EventFromSubCatToProduct;
import com.example.sravanreddy.flopkart.R;
import com.example.sravanreddy.flopkart.model.SubCategoryClass;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by sravanreddy on 4/12/18.
 */

public class MyAdapterSubClass extends RecyclerView.Adapter {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private Handler handler;
    private int page = 0;
    ArrayList<SubCategoryClass> myList;
    Context context;

    public MyAdapterSubClass(ArrayList<SubCategoryClass> myList, Context context) {
        this.myList = myList;
        this.context = context;
        handler = new Handler();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View bannerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_for_photoslide, null);
            return new MyAdapterSubClass.BannerViewHolder(bannerLayout);
        } else {
            View headerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_layout, null);
            return new MyAdapterSubClass.MyViewHolder(headerLayout, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyAdapterSubClass.MyViewHolder) {
            final SubCategoryClass catogories = myList.get(position - 1);
            ((MyAdapterSubClass.MyViewHolder) holder).mainText2.setText(catogories.getScname());
            Picasso.get().load(catogories.getScimageurl()).placeholder(R.drawable.no_preview).into(((MyAdapterSubClass.MyViewHolder) holder).imageView2);
            ((MyViewHolder) holder).sCID=catogories.getScid();
            if (position % 3 == 0) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                holder.itemView.setLayoutParams(layoutParams);
            }
            ((MyAdapterSubClass.MyViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventFromSubCatToProduct sendCid=new EventFromSubCatToProduct(((MyViewHolder) holder).sCID);
                    EventBus.getDefault().post(sendCid);
                }
            });
        } else if (holder instanceof MyAdapterSubClass.BannerViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            ((MyAdapterSubClass.BannerViewHolder) holder).viewPager.setAdapter(((MyAdapterSubClass.BannerViewHolder) holder).viewPagerAdapter);
            startLoop(((MyAdapterSubClass.BannerViewHolder) holder).viewPagerAdapter, ((MyAdapterSubClass.BannerViewHolder) holder).viewPager);
        }

    }

    public void startLoop(final ViewPagerAdapterSubCategory viewPagerAdapter, final ViewPager viewPager) {


        Runnable runnable = new Runnable() {
            public void run() {
                if (viewPagerAdapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                viewPager.setCurrentItem(page, true);
                handler.postDelayed(this, 5000);
            }
        };
    }

    @Override
    public int getItemCount() {
        return myList.size() + 1;
//        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEAD;
        else
            return TYPE_LIST;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int viewType;
        TextView mainText2;
        ConstraintLayout constraintLayout;
        ImageView imageView2;
        String sCID;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            mainText2 = itemView.findViewById(R.id.main_textView);
            imageView2 = itemView.findViewById(R.id.imageView);
            constraintLayout = itemView.findViewById(R.id.list_layout);
            this.viewType = viewType;

        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerAdapterSubCategory viewPagerAdapter;

        public BannerViewHolder(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            viewPagerAdapter = new ViewPagerAdapterSubCategory(context, myList);
        }
    }
}
