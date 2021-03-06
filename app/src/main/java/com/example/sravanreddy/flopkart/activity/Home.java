package com.example.sravanreddy.flopkart.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.fragments.CartFragment;
import com.example.sravanreddy.flopkart.events.EventFromSubCatToProduct;
import com.example.sravanreddy.flopkart.events.Eventclass;
import com.example.sravanreddy.flopkart.fragments.HomeFragment;
import com.example.sravanreddy.flopkart.fragments.ProductDescriptionFragment;
import com.example.sravanreddy.flopkart.model.ProductDetails;
import com.example.sravanreddy.flopkart.fragments.ProductFragment;
import com.example.sravanreddy.flopkart.fragments.ProfileFragment;
import com.example.sravanreddy.flopkart.R;
import com.example.sravanreddy.flopkart.fragments.SubCategoryFragment;
import com.example.sravanreddy.flopkart.fragments.TechnologiesUsedFragment;
import com.example.sravanreddy.flopkart.fragments.WishListFragment;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private android.support.v7.widget.Toolbar homeToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView username;
    private SharedPreferences msharedPreferences;
    private Eventclass eventclass;
    private EventFromSubCatToProduct eventFromSubCatToProduct;
    private CircleImageView profilePic;
    private final int GALLERY_CODE=2;
    private Badge qBadgeView;
    private  ImageButton cartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        msharedPreferences=getSharedPreferences("user_local", MODE_PRIVATE);
        homeToolbar=findViewById(R.id.home_toolbar);
        EventBus.getDefault().register(this);
        setSupportActionBar(homeToolbar);

//        cartButton=homeToolbar.findViewById(R.id.cart_button);
//        qBadgeView = new QBadgeView(getBaseContext()).bindTarget(cartButton).
//                setBadgeGravity(Gravity.END | Gravity.TOP).
//                setBadgeNumber(0);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        View headerView=navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, homeToolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        username = headerView.findViewById(R.id.navi_header_userName);
        profilePic=headerView.findViewById(R.id.profile_pic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGallery = new Intent(Intent.ACTION_GET_CONTENT);

                startGallery.setType("image/*");

                startActivityForResult(startGallery, GALLERY_CODE);
            }
        });
        username.setText(msharedPreferences.getString("FullName", ""));
//        cartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CartFragment cartFragment=new CartFragment();
//                getFragmentManager().beginTransaction().replace(R.id.fragment, cartFragment).addToBackStack("").commit();
//            }
//        });
        getFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_CODE:
                //user canceled
                if (data == null) {
                    return;
                } else {
                    //system will return photo uri instead of a photo
                    Uri uri = data.getData();
                    Picasso.get().load(uri)
                            .resize(56, 56)
                            .into(profilePic);
                }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(Eventclass eventclass ){
        this.eventclass=eventclass;
        Bundle cidBundle=new Bundle();
        cidBundle.putString("CID",eventclass.getCid());
        msharedPreferences.edit().putString("CID",eventclass.getCid()).commit();
        SubCategoryFragment subCategoryFragment=new SubCategoryFragment();
        subCategoryFragment.setArguments(cidBundle);

        getFragmentManager().beginTransaction().replace(R.id.fragment, subCategoryFragment).addToBackStack(" ").commit();
    }

    @Subscribe
    public void onEvent(EventFromSubCatToProduct eventFromSubCatToProduct){
        this.eventFromSubCatToProduct=eventFromSubCatToProduct;
        Bundle scidBundle=new Bundle();
        scidBundle.putString("SCID", eventFromSubCatToProduct.getsCid());
        msharedPreferences.edit().putString("SCID", eventFromSubCatToProduct.getsCid()).commit();
        ProductFragment productFragment=new ProductFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment, productFragment).addToBackStack("").commit();
    }

    @Subscribe
    public void onEvent(ProductDetails productDetails){
        Bundle productBundle=new Bundle();
        productBundle.putParcelable("Product Details",productDetails);
        ProductDescriptionFragment productDescriptionFragment=new ProductDescriptionFragment();
        productDescriptionFragment.setArguments(productBundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment, productDescriptionFragment).addToBackStack("").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
                CartFragment cartFragment=new CartFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, cartFragment).addToBackStack("").commit();
                break;
            case R.id.search:
                WishListFragment wishListFragment=new WishListFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, wishListFragment).addToBackStack("").commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_profile:
                ProfileFragment profile=new ProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, profile).addToBackStack("").commit();
                break;
            case R.id.navigation_shop:
                for(int i=0;i<getFragmentManager().getBackStackEntryCount();i++){
                    getFragmentManager().popBackStack();
                }

                break;
            case R.id.navigation_myOrders:
                CartFragment cartFragment=new CartFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, cartFragment).addToBackStack("").commit();
                break;
            case R.id.order_histort:
                startActivity(new Intent(Home.this, OrdersHistoryActivity.class));
                    break;
            case R.id.navigation_logout:
                finish();
                break;
            case R.id.technologies_used:
                TechnologiesUsedFragment technologiesUsedFragment=new TechnologiesUsedFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, technologiesUsedFragment).addToBackStack("").commit();
                break;
        }
        return true;
    }
}
