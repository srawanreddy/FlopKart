package com.example.sravanreddy.flopkart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class OrderConfirmationFragment extends Fragment{
private RecyclerView recyclerView;
private ArrayList<OrderConfirmation> list;
Button close;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.order_confirmation_fragment_layout, null);
        recyclerView=view.findViewById(R.id.recyclerView_order_confirmation);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        close=view.findViewById(R.id.close_orderConfirmation);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<getFragmentManager().getBackStackEntryCount();i++)
                    getFragmentManager().popBackStack();
            }
        });
        list=getArguments().getParcelableArrayList("Order confirmation list");
        OrderConfirmationAdapter orderConfirmationAdapter=new OrderConfirmationAdapter(list, getActivity());
        recyclerView.setAdapter(orderConfirmationAdapter);
        return view;
    }
}
