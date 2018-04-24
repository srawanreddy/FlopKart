package com.example.sravanreddy.flopkart.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.sravanreddy.flopkart.network.AppController;
import com.example.sravanreddy.flopkart.R;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText fName, lName, address, mobile, email, password;
    Button signUp, goBack;
    private String rjtUrl;
    AwesomeValidation awesomeValidation;
    private SharedPreferences msharedPreferences;
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
        msharedPreferences=getSharedPreferences("user_local", MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_button:
                msharedPreferences.edit().putString("Address", address.getText().toString()).commit();
                awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
                awesomeValidation.addValidation(this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.fname_error_register);
                awesomeValidation.addValidation(this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lname_error_register);

                awesomeValidation.addValidation(this, R.id.emailId, Patterns.EMAIL_ADDRESS, R.string.email_error_register);
                awesomeValidation.addValidation(this, R.id.password, "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", R.string.password_error_register);
                awesomeValidation.addValidation(this, R.id.mobile, "[0-9]{10}$", R.string.mobile_error_register);
                if (awesomeValidation.validate())
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
