package com.example.user.doctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity {

    EditText etName,etEmail,etPassword,etContact,etAddress;
    RadioGroup grpradio;
    Button btnSignup;
    TextView tvLogin;
    String username,password,email,contact,address,location,category,gender;
    String httpURL = "https://fearsome-terminals.000webhostapp.com/Doctor_register.php";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean checkEdit;
    Spinner spinner, spinnerloc;
    ArrayAdapter<CharSequence> arrayAdapter;
    ArrayAdapter<CharSequence> arrayAdapter2;
    String docCategory, locCategory;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText)  findViewById(R.id.etUsername);
        etEmail = (EditText)  findViewById(R.id.etEmail);
        etPassword = (EditText)  findViewById(R.id.etPassword);
        etContact = (EditText)  findViewById(R.id.etContact);
        etAddress = (EditText)  findViewById(R.id.etAddress);
        btnSignup = (Button)  findViewById(R.id.btnSignup);

        tvLogin = (TextView)  findViewById(R.id.tv_login);

        spinner = (Spinner) findViewById(R.id.category);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinnerloc = (Spinner)findViewById(R.id.location);
        arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.categories_loc, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerloc.setAdapter(arrayAdapter2);


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        progressDialog = new ProgressDialog(MainActivity.this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
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
                    Toast.makeText(MainActivity.this,"Please fill in all your details",Toast.LENGTH_LONG).show();
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
                        Intent i = new Intent(MainActivity.this, VActivity.class);
                        i.putExtra("username", jsonObject.getString("name"));
                        i.putExtra("useremail", jsonObject.getString("email"));
                        i.putExtra("usercontact",jsonObject.getString("contact"));
                        i.putExtra("useraddress",jsonObject.getString("address"));
                        i.putExtra("userlocation",jsonObject.getString("location"));
                        startActivity(i);
                    } else if (jsonObject.getString("status").equals("exists")) {
                        Toast.makeText(MainActivity.this, "email ID already exists", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG).show();
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

        requestQueue = Volley.newRequestQueue(MainActivity.this);

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