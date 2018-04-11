package com.example.sravanreddy.flopkart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText fName, lName, address, mobile, email, password;
    Button signUp, goBack;
    private String rjtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fName=findViewById(R.id.fname);
        lName=findViewById(R.id.lname);
        address=findViewById(R.id.address);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        signUp=findViewById(R.id.submit_button);
        goBack=findViewById(R.id.goBack);
        signUp.setOnClickListener(SignUp.this);
        goBack.setOnClickListener(SignUp.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_button:
                if(validateData())
                postUserDetails();
                else
                    Toast.makeText(this, "Invalid User Details, Please check all the Details again", Toast.LENGTH_LONG).show();
                break;
            case R.id.goBack:
                finish();
                break;
        }
    }

    private boolean validateData() {
        if(fName.getText().toString()==""||
                lName.getText().toString()==""||
                address.getText().toString()==""||
                mobile.getText().toString()==""||
                password.getText().toString()==""||
                email.getText().toString()=="")
            return false;
        return true;
    }

    private void postUserDetails() {
        rjtUrl="http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php?fname="+fName.getText().toString()+"&lname="+lName.getText().toString()+"&address="+address.getText().toString()+"&%20email="+email.getText().toString()+"&mobile="+mobile.getText().toString()+"&password="+password.getText().toString();
        StringRequest postRequest=new StringRequest(Request.Method.POST, rjtUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response Success", response);
                if(response.contains("successfully"));
                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response Error", error.toString());
            }
        });

        AppController.getmInstance(this.getApplicationContext()).addToRequestQueue(postRequest);
    }
}
