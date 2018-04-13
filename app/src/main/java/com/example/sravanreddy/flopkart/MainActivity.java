package com.example.sravanreddy.flopkart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private EditText uname, password;
private TextView forgotPassword;
private Button signIn, signUp;
private ProgressDialog pDialog;
private String jsonResponse;
private CheckBox rememberMe;
private String rjtUrl;
private Boolean userFound=false;


SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        msharedPreferences=getSharedPreferences("user_local", MODE_PRIVATE);
        uname=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.signin_button);
        signUp=findViewById(R.id.signUp_button);
        rememberMe=findViewById(R.id.rememberMe);
        forgotPassword=findViewById(R.id.forgotPassword);
        uname.setText(msharedPreferences.getString("UserName", ""));
        password.setText(msharedPreferences.getString("Password", ""));
        rememberMe.setChecked(msharedPreferences.getBoolean("isChecked", false));
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_button:
                makeJasonArrReq();

                break;
            case R.id.signUp_button:
                startActivity(new Intent(MainActivity.this, SignUp.class));
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                break;
        }

    }

    private  void makeJasonArrReq(){
        showDialog();
        rjtUrl="http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+uname.getText().toString()+"&password="+password.getText().toString();
        JsonArrayRequest jsonObjectRequest=new JsonArrayRequest(rjtUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject person = (JSONObject) response.get(i);
                        String msg = person.getString("msg");
                        String id = person.getString("id");
                        String firstname = person.getString("firstname");
                        String lastname = person.getString("lastname");
                        String email = person.getString("email");
                        String mobile = person.getString("mobile");
                        String apiKey = person.getString("appapikey ");
                        if(msg.contains("success")) {
                            if (rememberMe.isChecked()) {
                                msharedPreferences.edit().putString("Password", password.getText().toString()).commit();
                                msharedPreferences.edit().putBoolean("isChecked", true).commit();
                            } else {
                                msharedPreferences.edit().remove("Password").commit();
                                msharedPreferences.edit().remove("isChecked").commit();
                            }
                            hideDialog();
                            msharedPreferences.edit().putString("ID", id).commit();
                            msharedPreferences.edit().putString("FullName", firstname+" "+lastname).commit();
                            msharedPreferences.edit().putString("Email", email).commit();
                            msharedPreferences.edit().putString("Mobile", mobile).commit();
                            msharedPreferences.edit().putString("api_key", apiKey).commit();
                            Intent homeIntent=new Intent(MainActivity.this, Home.class);
                            startActivity(homeIntent);

                        }
                            else
                                Toast.makeText(getApplicationContext(), "Invalid Mobile or Password", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("Response",response.toString());

                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.i("Response",error.toString());
            }
        });

        AppController.getmInstance(this.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        //checkStatus(userFound);
    }

    private void checkStatus(Boolean userFound) {

    }

    private  void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog(){
        if(pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        msharedPreferences.edit().remove("Password");
    }
}
