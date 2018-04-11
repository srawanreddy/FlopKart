package com.example.sravanreddy.flopkart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
private EditText mobile, password;
private Button resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mobile=findViewById(R.id.mobile_resetPassword);
        password=findViewById(R.id.password_resetPassword);
        resetPassword=findViewById(R.id.reset_password_button);
        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }


}
