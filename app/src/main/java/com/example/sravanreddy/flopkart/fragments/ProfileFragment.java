package com.example.sravanreddy.flopkart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sravanreddy.flopkart.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView fullName, address, emailId, mobile, resetPassword;
    private SharedPreferences msharedPreferences;
    private Button updateProfile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(R.layout.profile_fragment, null);
        msharedPreferences=getActivity().getSharedPreferences("user_local", Context.MODE_PRIVATE);
        fullName=view.findViewById(R.id.profile_fullname);
        address=view.findViewById(R.id.profile_address);
        emailId=view.findViewById(R.id.profile_emailId);
        mobile=view.findViewById(R.id.profile_mobile);
        resetPassword=view.findViewById(R.id.resetPassword);
        updateProfile=view.findViewById(R.id.update_profile_button);
        fullName.setText(msharedPreferences.getString("FullName", ""));
        address.setText(msharedPreferences.getString("Address",""));
        emailId.setText(msharedPreferences.getString("Email", ""));
        mobile.setText(msharedPreferences.getString("Mobile", ""));
        updateProfile.setOnClickListener(this);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPasswordFragment resetPasswordFragment=new ResetPasswordFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, resetPasswordFragment).addToBackStack("").commit();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
    UpdateProfileFragment updateProfileFragment=new UpdateProfileFragment();
    getFragmentManager().beginTransaction().replace(R.id.fragment, updateProfileFragment).addToBackStack("").commit();
    }
}
