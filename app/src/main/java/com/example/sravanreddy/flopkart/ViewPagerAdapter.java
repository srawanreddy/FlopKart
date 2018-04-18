package com.example.sravanreddy.flopkart;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by sravanreddy on 4/11/18.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<Catogories> catogories;
    public ViewPagerAdapter(Context context, ArrayList<Catogories> catogories) {
        this.context = context;
        this.catogories=catogories;
    }

    @Override
    public int getCount() {
        return catogories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_photoslide, null);
        ImageView imageView=view.findViewById(R.id.imageView2);
        TextView description=view.findViewById(R.id.description);
        description.setText(catogories.get(position).getCname());
        Picasso.get().load(catogories.get(position).getCimagerl()).placeholder(R.drawable.no_preview).into(imageView);
        ViewPager vp= (ViewPager) container;
        vp.addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), catogories.get(position).getCname(), Toast.LENGTH_SHORT).show();

                Eventclass sendCid=new Eventclass(catogories.get(position).getCid());
                EventBus.getDefault().post(sendCid);

            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp= (ViewPager) container;
        View view= (View) object;
        vp.removeView(view);
    }
}
