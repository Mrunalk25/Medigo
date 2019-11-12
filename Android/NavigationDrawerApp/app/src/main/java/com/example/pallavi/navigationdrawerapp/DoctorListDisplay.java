package com.example.pallavi.navigationdrawerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorListDisplay extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    RequestQueue requestQueue;
    private List<DoctorInfo> listItems;
    String docCategory,locCategory;
    String httpUrl = "http://fearsome-terminals.000webhostapp.com/SearchDoctor.php";
    Button b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctor_list_display, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
      //  adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        docCategory = getArguments().getString("doc_category");
        locCategory = getArguments().getString("loc_category");

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        listItems = new ArrayList<>();
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("doctor_info");
                    if (jsonObject.getBoolean("status")) {
                   //     Toast.makeText(getActivity(),jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        DoctorInfo doctorInfo = new DoctorInfo();
                        doctorInfo.setDoc_email(o.getString("email"));
                        doctorInfo.setDoc_name(o.getString("name"));
                        doctorInfo.setDoc_contact(o.getString("contact"));
                        doctorInfo.setDoc_address(o.getString("address"));
                        doctorInfo.setDoc_category(o.getString("category"));
                        listItems.add(doctorInfo);
                    }

                    adapter = new MyAdapter(listItems,getActivity());
                    recyclerView.setAdapter(adapter);
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

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
