package com.example.user.doctor;


        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin;
    Boolean newUser;
    TextView nextRegister;
    TextView register;
    String useremail,userpassword;
    String httpURL="https://fearsome-terminals.000webhostapp.com/Doctor_login.php";
    Boolean flag;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmailID);
        password = (EditText) findViewById(R.id.etPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        nextRegister = (TextView) findViewById(R.id.nextALogin);
        progressDialog = new ProgressDialog(LoginActivity.this);

        nextRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmptyOrNot();

                if(flag)
                {
                    UserLogin();

                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Please fill out all the fields",Toast.LENGTH_LONG).show();
                }



            }
        });


    }

    public void UserLogin()
    {
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //     Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {

                        newUser = true;
                        Intent i = new Intent(LoginActivity.this,VActivity.class);
                        i.putExtra("username",jsonObject.getString("name"));
                        i.putExtra("useremail",jsonObject.getString("email"));
                        i.putExtra("usercontact",jsonObject.getString("contact"));
                        i.putExtra("useraddress",jsonObject.getString("address"));
                        i.putExtra("usergender",jsonObject.getString("gender"));
                        startActivity(i);

                    } else if (jsonObject.getString("status").equals("INVALID"))
                    {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_LONG).show();
                    } else
                    {
                        Toast.makeText(LoginActivity.this, "Password doesn't match", Toast.LENGTH_LONG).show();
                    }

                }catch(JSONException e)
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
                        Toast.makeText(LoginActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();

                params.put("user_email",useremail);
                params.put("user_pass",userpassword);

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        requestQueue.add(stringRequest);
    }

    public void CheckEmptyOrNot()
    {
        useremail = email.getText().toString().trim();
        userpassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(useremail) || TextUtils.isEmpty(userpassword))
        {
            flag = false;
        }
        else
        {
            flag = true;
        }
    }
}
