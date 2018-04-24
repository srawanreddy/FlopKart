package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sravanreddy.flopkart.data.MyDBHelper;
import com.example.sravanreddy.flopkart.data.MyWishListDBHeliper;
import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

public class ProductDescriptionFragment extends Fragment implements View.OnClickListener {
private TextView productName, productPrice, productQuantity, toolBarTextView;
private ImageView productImage;
private ProductDetails productDetails;
private Button add_to_cart;
private ImageButton wishList;
private ExpandableTextView eptv;
private SQLiteDatabase sqLiteDatabase, sqLiteDatabase2;
 private MyDBHelper myDBHelper;
 private MyWishListDBHeliper myWishListDBHeliper;
SharedPreferences mSharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBarTextView=getActivity().findViewById(R.id.toobar_textView);
        toolBarTextView.setText("Products");
        mSharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.product_description_fragment_layout, null);
        productName=view.findViewById(R.id.product_name);
        productPrice=view.findViewById(R.id.product_price);
        productQuantity=view.findViewById(R.id.product_quantity);
        add_to_cart=view.findViewById(R.id.add_to_cart);
        wishList=view.findViewById(R.id.wishlist_button);
        eptv=view.findViewById(R.id.expand_text_view);
        productImage=view.findViewById(R.id.product_image);
        productDetails=getArguments().getParcelable("Product Details");
        productName.setText(productDetails.getName());
        productPrice.setText("$"+productDetails.getPrice());
        productQuantity.setText("Hurry Up! Only "+productDetails.getQuantity()+" left");
        eptv.setText(productDetails.getDiscription());
        Picasso.get().load(productDetails.getImage()).into(productImage);
        //myDBHelper=MyDBHelper.getInstance(getActivity().getApplicationContext());
        myDBHelper=new MyDBHelper(getActivity().getApplicationContext());
        myWishListDBHeliper=new MyWishListDBHeliper(getActivity().getApplicationContext());
        sqLiteDatabase=myDBHelper.getWritableDatabase();
        sqLiteDatabase2=myWishListDBHeliper.getWritableDatabase();
        add_to_cart.setOnClickListener(this);
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values=new ContentValues();
                values.put(MyWishListDBHeliper.ID, Integer.parseInt(productDetails.getId()));
                values.put(MyWishListDBHeliper.UserID, Integer.parseInt(mSharedPreferences.getString("ID","") ));
                values.put(MyWishListDBHeliper.NAME, productDetails.getName());
                values.put(MyWishListDBHeliper.QUANTITY,"1");
                values.put(MyWishListDBHeliper.PRICE, productDetails.getPrice());
                values.put(MyWishListDBHeliper.DESCRIPTION,productDetails.getDiscription());
                values.put(MyWishListDBHeliper.IMAGE, productDetails.getImage());
                sqLiteDatabase2.insert(MyWishListDBHeliper.TABLE_NAME, null, values);
                Toast.makeText(getActivity(), "Item added to Wish List", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {

        ContentValues values=new ContentValues();
        values.put(MyDBHelper.ID, Integer.parseInt(productDetails.getId()));
        values.put(MyDBHelper.UserID, Integer.parseInt(mSharedPreferences.getString("ID","") ));
        values.put(MyDBHelper.NAME, productDetails.getName());
        values.put(MyDBHelper.QUANTITY,"1");
        values.put(MyDBHelper.PRICE, productDetails.getPrice());
        values.put(MyDBHelper.DESCRIPTION,productDetails.getDiscription());
        values.put(MyDBHelper.IMAGE, productDetails.getImage());
        sqLiteDatabase.insert(MyDBHelper.TABLE_NAME, null, values);
        Toast.makeText(getActivity(), "Item added to Cart", Toast.LENGTH_SHORT).show();
    }
}
