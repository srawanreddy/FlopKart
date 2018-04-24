package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sravanreddy.flopkart.data.MyDBHelper;
import com.example.sravanreddy.flopkart.data.MyWishListDBHeliper;
import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.R;
import com.example.sravanreddy.flopkart.util.SwipeController2;
import com.example.sravanreddy.flopkart.util.SwipeControllerAction;
import com.example.sravanreddy.flopkart.adapters.WishListFragmentAdapter;

import java.util.ArrayList;

public class WishListFragment extends Fragment{
    private ArrayList<ProductDetails> productDetailsList=new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase, sqLiteDatabase2;
    private MyWishListDBHeliper myDBHelper;
    private MyDBHelper myDBHelper2;
    private Button closeWishList;
    private SharedPreferences msharedPreferences;

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wish_list_fragment_layout, null);

        closeWishList=view.findViewById(R.id.wishlist_close_button);
        closeWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<getFragmentManager().getBackStackEntryCount();i++)
                getFragmentManager().popBackStack();
            }
        });
        recyclerView=view.findViewById(R.id.wishList_recylerview);
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
        int user_id=Integer.parseInt(msharedPreferences.getString("ID", ""));
        myDBHelper = MyWishListDBHeliper.getInstance(getActivity().getApplicationContext());
        sqLiteDatabase = myDBHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyWishListDBHeliper.TABLE_NAME + " WHERE " + MyWishListDBHeliper.UserID+" = "+user_id, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
            do {
                ProductDetails productDetails=new ProductDetails(cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.ID))+"",
                        cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.NAME)),
                        cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.PRICE)),
                        cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(MyWishListDBHeliper.IMAGE))
                );
                productDetailsList.add(productDetails);
            } while (cursor.moveToNext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final WishListFragmentAdapter wishListFragmentAdapter=new WishListFragmentAdapter(productDetailsList,getActivity());
        recyclerView.setAdapter(wishListFragmentAdapter);
        final SwipeController2 swipeController = new SwipeController2(new SwipeControllerAction() {
            @Override
            public void onRightClicked(int position) {


                myDBHelper2= MyDBHelper.getInstance(getActivity().getApplicationContext());
                sqLiteDatabase2=myDBHelper2.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put(MyDBHelper.ID, Integer.parseInt(productDetailsList.get(position).getId()));
                values.put(MyDBHelper.UserID, Integer.parseInt(msharedPreferences.getString("ID","") ));
                values.put(MyDBHelper.NAME, productDetailsList.get(position).getName());
                values.put(MyDBHelper.QUANTITY,"1");
                values.put(MyDBHelper.PRICE, productDetailsList.get(position).getPrice());
                values.put(MyDBHelper.DESCRIPTION,productDetailsList.get(position).getDiscription());
                values.put(MyDBHelper.IMAGE, productDetailsList.get(position).getImage());
                sqLiteDatabase2.insert(MyDBHelper.TABLE_NAME, null, values);
                sqLiteDatabase.delete(MyWishListDBHeliper.TABLE_NAME, MyWishListDBHeliper.ID+"="+productDetailsList.get(position).getId(),null);
                wishListFragmentAdapter.productDetailsList.remove(position);
                wishListFragmentAdapter.notifyItemRemoved(position);
                wishListFragmentAdapter.notifyItemRangeChanged(position, wishListFragmentAdapter.getItemCount());
                Toast.makeText(getActivity(), "Item added to Cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftClicked(int position) {
                sqLiteDatabase.delete(MyWishListDBHeliper.TABLE_NAME, MyWishListDBHeliper.ID+"="+productDetailsList.get(position).getId(),null);
                wishListFragmentAdapter.productDetailsList.remove(position);
                wishListFragmentAdapter.notifyItemRemoved(position);
                wishListFragmentAdapter.notifyItemRangeChanged(position, wishListFragmentAdapter.getItemCount());
            }
        });


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);


        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
        return view;
    }
}
