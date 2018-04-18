package com.example.sravanreddy.flopkart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener {
private TextView mobile, oldPassword, newPassword;
private Button reset, close;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.reset_password_fragment_layout, null);
        mobile=view.findViewById(R.id.reset_password_mobile_tv);
        oldPassword=view.findViewById(R.id.reset_password_old_password);
        newPassword=view.findViewById(R.id.reset_password_new_password);
        reset=view.findViewById(R.id.reset);
        reset.setOnClickListener(this);
        close=view.findViewById(R.id.close_reset_password);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset:
                jsonRequest();
                break;
            case R.id.close_reset_password:
                UpdateProfileFragment updateProfileFragment=new UpdateProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, updateProfileFragment).addToBackStack("").commit();
                break;

        }
    }

    private void jsonRequest() {
        String url="http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?&mobile="+mobile.getText().toString()+"&password="+oldPassword.getText().toString()+"&newpassword="+newPassword.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "Password Reset Successful", Toast.LENGTH_LONG).show();
                UpdateProfileFragment updateProfileFragment=new UpdateProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, updateProfileFragment).addToBackStack("").commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Password Reset Failed", Toast.LENGTH_LONG).show();

            }
        });
        AppController.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
