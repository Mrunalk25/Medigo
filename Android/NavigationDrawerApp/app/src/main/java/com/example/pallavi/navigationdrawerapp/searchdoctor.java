package com.example.pallavi.navigationdrawerapp;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PALLAVI on 03/03/2018.
 */

public class searchdoctor extends android.support.v4.app.Fragment {

    Spinner spinner, spinnerloc;
    ArrayAdapter<CharSequence> arrayAdapter;
    ArrayAdapter<CharSequence> arrayAdapter2;
    String docCategory, locCategory;
    String httpUrl = "http://fearsome-terminals.000webhostapp.com/SearchDoctor.php";
    Button search;
    Boolean empty;
    RequestQueue requestQueue;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Search a Doctor");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchdoctor, container, false);

        search = (Button) view.findViewById(R.id.btnSearch);

        spinner = (Spinner) view.findViewById(R.id.dropdowncategory);
        arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.categories,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinnerloc = (Spinner) view.findViewById(R.id.locationdropdown);
        arrayAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.categories_loc, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerloc.setAdapter(arrayAdapter2);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                docCategory = adapterView.getItemAtPosition(i).toString();

                //    Toast.makeText(getActivity(),docCategory,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locCategory = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEmptyOrNot();

                if (empty) {
                    Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_SHORT).show();
                } else {
                    SendCategory();
                }

            }
        });


        return view;

    }


    public void CheckEmptyOrNot() {
        if (TextUtils.isEmpty(locCategory) || TextUtils.isEmpty(docCategory)) {
            empty = true;
        } else {
            empty = false;
        }
    }

    public void SendCategory() {
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
                        Fragment fragment = new DoctorListDisplay();
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

}
