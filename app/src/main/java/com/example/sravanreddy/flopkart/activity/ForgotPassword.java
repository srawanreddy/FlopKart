package com.example.sravanreddy.flopkart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sravanreddy.flopkart.R;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
private EditText emailId;
private Button resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailId=findViewById(R.id.email_forgotPassword);
        resetPassword=findViewById(R.id.reset_password_button);
        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url="http://rjtmobile.com/aamir/e-commerce/android-app/androidapp/shop_fogot_pass.php?&email="+emailId.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ForgotPassword.this, " New Password Sent to  your Email Id", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPassword.this, "Forgot Password Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
