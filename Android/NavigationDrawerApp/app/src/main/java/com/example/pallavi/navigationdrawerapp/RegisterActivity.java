package com.example.pallavi.navigationdrawerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etEmail,etPassword,etContact,etAddress;
    RadioGroup grpradio;
    Button btnSignup;
    TextView tvLogin;
    String username,password,email,contact,address,gender;
    String httpURL = "https://fearsome-terminals.000webhostapp.com/User_register.php";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean checkEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText)  findViewById(R.id.etUsername);
        etEmail = (EditText)  findViewById(R.id.etEmail);
        etPassword = (EditText)  findViewById(R.id.etPassword);
        etContact = (EditText)  findViewById(R.id.etContact);
        etAddress = (EditText)  findViewById(R.id.etAddress);
        btnSignup = (Button)  findViewById(R.id.btnSignup);
        grpradio = (RadioGroup) findViewById(R.id.grp_of_radio);
        tvLogin = (TextView)  findViewById(R.id.tv_login);

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        progressDialog = new ProgressDialog(RegisterActivity.this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckWhetherEmptyOrNot();

                int radioselect = grpradio.getCheckedRadioButtonId();
                String radios = Integer.toString(radioselect);
                if(radioselect == 1)
                {
                    gender = "Male";
                }
                else if(radioselect == 2)
                {
                    gender = "Female";
                }


                if(checkEdit)
                {
                    UserRegistration();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Please fill in all your details",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void UserRegistration()
    {
        progressDialog.setMessage("Please wait, your data is being inserted....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        Intent i = new Intent(RegisterActivity.this, NavigationActivity.class);
                        i.putExtra("username", jsonObject.getString("name"));
                        i.putExtra("useremail", jsonObject.getString("email"));
                        i.putExtra("usercontact",jsonObject.getString("contact"));
                        i.putExtra("useraddress",jsonObject.getString("address"));
                        i.putExtra("usergender",jsonObject.getString("gender"));
                        startActivity(i);
                    } else if (jsonObject.getString("status").equals("exists")) {
                        Toast.makeText(RegisterActivity.this, "email ID already exists", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(RegisterActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();

                params.put("name",username);
                params.put("email",email);
                params.put("password",password);
                params.put("contact",contact);
                params.put("address",address);
                params.put("gender",gender);

                return params;

            }
        };

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        requestQueue.add(stringRequest);

    }


    public void CheckWhetherEmptyOrNot()
    {
        username = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        contact = etContact.getText().toString().trim();
        address = etAddress.getText().toString().trim();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(address))
        {
            checkEdit = false;
        }
        else
        {
            checkEdit = true;
        }
    }

}
