package com.example.pallavi.navigationdrawerapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PALLAVI on 03/03/2018.
 */

public class user_details extends android.support.v4.app.Fragment {

    TextView username,email,usernamedisplay,contactdisplay,addressdisplay,genderdisplay;
    Button viewMH;
    RequestQueue requestQueue;
    String httpUrl;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("User Details");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details,container,false);

        username = (TextView) view.findViewById(R.id.user_details_name);
        email = (TextView) view.findViewById(R.id.user_details_email);
        usernamedisplay = (TextView) view.findViewById(R.id.name_display);
        contactdisplay = (TextView) view.findViewById(R.id.contactdisplay);
        addressdisplay = (TextView) view.findViewById(R.id.addressdisplay);
        genderdisplay = (TextView) view.findViewById(R.id.genderdisplay);
        viewMH = (Button) view.findViewById(R.id.btnViewMH);

        viewMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        getMedicalHistory();
            }
        });


        username.setText(getArguments().getString("name"));
        usernamedisplay.setText(getArguments().getString("name"));
        email.setText(getArguments().getString("email"));
        contactdisplay.setText(getArguments().getString("contact"));
        addressdisplay.setText(getArguments().getString("address"));
        genderdisplay.setText(getArguments().getString("gender"));
        return view;


    }

  /*  private void getMedicalHistory() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        //  Toast.makeText(getActivity(),jsonObject.toString(), Toast.LENGTH_SHORT).show();
                        android.support.v4.app.Fragment fragment = new DoctorListDisplay();
                        Bundle b = new Bundle();
                        b.putString("doc_category",docCategory);
                        b.putString("loc_category",locCategory);
                        fragment.setArguments(b);
                        android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_main,fragment);
                        ft.commit();

                    } else {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Showing error message if something goes wrong
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("doc_category", docCategory);
                params.put("loc_category", locCategory);

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);

    }

    } */


}
