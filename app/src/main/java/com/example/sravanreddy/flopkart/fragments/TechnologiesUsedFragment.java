package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sravanreddy.flopkart.R;

public class TechnologiesUsedFragment extends Fragment {
    private Button close;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.technologies_used_fragment_layout, null);
        close=view.findViewById(R.id.close_technologiesUsed);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
