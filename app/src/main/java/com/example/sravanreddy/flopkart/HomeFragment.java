package com.example.sravanreddy.flopkart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sravanreddy on 4/11/18.
 */

public class HomeFragment extends Fragment {
ViewPager photoSlider;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.home_fragment, null);
        photoSlider=view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity());
        photoSlider.setAdapter(viewPagerAdapter);
        return view;
    }
}
