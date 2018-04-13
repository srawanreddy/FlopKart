package com.example.sravanreddy.flopkart;

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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sravanreddy on 4/11/18.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private Handler handler;
    private String cID;
    private int page=0;
    public static int span;
    ArrayList<Catogories> myList;
    Context context;

    public MyAdapter(ArrayList<Catogories> myList, Context context) {
        this.myList = myList;
        this.context = context;
        handler = new Handler();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View bannerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_for_photoslide, null);
            return new BannerViewHolder(bannerLayout);
        } else {
            View headerLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_layout, null);
            span = 2;
            return new MyViewHolder(headerLayout, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final Catogories catogories = myList.get(position-1);
            ((MyViewHolder) holder).mainText.setText(catogories.getCname());
            Picasso.get().load(catogories.getCimagerl()).into(((MyViewHolder) holder).imageView);
            cID=catogories.getCid();
            if(position%3==0){
                StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setFullSpan(true);
                holder.itemView.setLayoutParams(layoutParams);
            }
            ((MyViewHolder) holder).constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else if (holder instanceof BannerViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            holder.itemView.setLayoutParams(layoutParams);
            ((BannerViewHolder) holder).viewPager.setAdapter(((BannerViewHolder) holder).viewPagerAdapter);
            startLoop(((BannerViewHolder) holder).viewPagerAdapter, ((BannerViewHolder) holder).viewPager);
        }

    }

    @Override
    public int getItemCount() {
        return myList.size()+1;
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
        TextView mainText;
        ConstraintLayout constraintLayout;
        ImageView imageView;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            mainText = itemView.findViewById(R.id.main_textView);
            imageView = itemView.findViewById(R.id.imageView);
            constraintLayout = itemView.findViewById(R.id.list_layout);
            this.viewType = viewType;

        }
    }

    public void startLoop(final ViewPagerAdapter viewPagerAdapter, final ViewPager viewPager) {


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
    public class BannerViewHolder extends RecyclerView.ViewHolder{
        ViewPager viewPager;
        ViewPagerAdapter viewPagerAdapter;
        public BannerViewHolder(View itemView) {
            super(itemView);
            viewPager=itemView.findViewById(R.id.viewPager);
            viewPagerAdapter=new ViewPagerAdapter(context, myList);
        }
    }


}
