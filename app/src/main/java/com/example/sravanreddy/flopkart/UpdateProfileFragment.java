package com.example.sravanreddy.flopkart;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class UpdateProfileFragment extends Fragment implements View.OnClickListener {
private TextView updateFName, updateLName, updateEmail, updateMobile, updateAddress;
private Button update;
private SharedPreferences msharedPreferences;
private String url="http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname=aamir&lname=husain&address=noida& email=aa@gmail.com&mobile=55565454";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.update_profile_fragment_layout, null);
        updateFName=view.findViewById(R.id.firstName_update);
        updateLName=view.findViewById(R.id.lastName_update);
        updateEmail=view.findViewById(R.id.email_update);
        updateMobile=view.findViewById(R.id.mobile_update);
        updateAddress=view.findViewById(R.id.address_update);
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
        update=view.findViewById(R.id.update_button);
        update.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        jsonRequest();
    }

    private void jsonRequest() {
        url="http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname="+updateFName.getText().toString()+"&lname="+updateLName.getText().toString()+"&address="+updateAddress.getText().toString()+"&email="+updateEmail.getText().toString()+"&mobile="+updateMobile.getText().toString();
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.i("Update profile Response", response);
                Toast.makeText(getActivity(),"Update Successful" , Toast.LENGTH_SHORT).show();
                msharedPreferences.edit().putString("FullName", updateFName.getText().toString()+" "+updateLName.getText().toString()).commit();
                msharedPreferences.edit().putString("Email", updateEmail.getText().toString()).commit();
                msharedPreferences.edit().putString("Address", updateAddress.getText().toString()).commit();
                msharedPreferences.edit().putString("Mobile", updateMobile.getText().toString()).commit();
                getActivity().getFragmentManager().popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Update profile Response", error.toString());
                Toast.makeText(getActivity(),"Failed" , Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
}
