package com.example.pallavi.navigationdrawerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class UploadMedicalDetails extends Fragment {

    ArrayAdapter<String> arrayFam, arraySym;
    String values, valuesSymtpoms;
    Spinner spPreg, spDrug, spAlcohol, spTobacco;
    Button btnSubmitMH;
    Button ask;
    EditText aage;
    ListView lvApply, lvSymptoms;
    SparseBooleanArray sparseBooleanArray, sparseBooleanArray1;
    EditText etAllergy, etMedication;
    EditText edd,edm,edy;
    TextView name, age, address, contact, gender;
    String pregnant, drug, tobacco, alcohol, allergies, currmedi;
    String n,email,con,add,gen;
    String dd,dm,dy;
    String date;
    String httpUrl = "https://fearsome-terminals.000webhostapp.com/User_medical_history.php";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean flag;

    String[] arrayFamily = {"Asthma/Lung Problems", "Cancer", "Cardiac Disease", "Diabetes", "History of Back Pain", "Hypertension",
            "Psychiatric Disorders", "Seizure Disorder", "Stroke"};

    String[] arraySymptoms = {"Allergy", "Cardiovascular", "Chest Pain", "Connective Tissue Disease", "Diabetes Mellitus",
            "Eating Disorder", "Ear / Nose / Throat", "Eye", "Fever", "Gastrointestinal", "Genitourinary", "Hemtalogical", "Lymphatic",
            "Musculoskeletal Pain", "Neurological", "Psychiatric", "Respiratory Skin", "Weight Gain", "Weight Loss"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_upload_medical_details, container, false);


        btnSubmitMH = (Button) view.findViewById(R.id.submitMH);
        btnSubmitMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoandSend();
            }
        });

        lvApply = (ListView) view.findViewById(R.id.lvApplyFamily);
        lvSymptoms = (ListView) view.findViewById(R.id.lvSymptoms);

        arrayFam = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, arrayFamily);
        lvApply.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvApply.setAdapter(arrayFam);

        arraySym = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, arraySymptoms);
        lvSymptoms.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvSymptoms.setAdapter(arraySym);

        spPreg = (Spinner) view.findViewById(R.id.SpinnerPregnant);
        spPreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pregnant = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pregnant = null;
            }
        });

        spDrug = (Spinner) view.findViewById(R.id.SpinnerDruguse);
        spDrug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drug = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                drug = null;
            }
        });

        spTobacco = (Spinner) view.findViewById(R.id.SpinnerTobacco);
        spTobacco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tobacco = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tobacco = null;
            }
        });

        spAlcohol = (Spinner) view.findViewById(R.id.SpinnerAlcoholC);
        spAlcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alcohol = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                alcohol = null;
            }
        });

        etAllergy = (EditText) view.findViewById(R.id.etAllergies);
        etMedication = (EditText) view.findViewById(R.id.etCurrentMedi);

        name = (TextView) view.findViewById(R.id.namevaluemedical);
        address = (TextView) view.findViewById(R.id.addressvaluemedical);
        contact = (TextView) view.findViewById(R.id.contactvaluemedical);
        gender = (TextView) view.findViewById(R.id.gendervaluemedical);

        name.setText(getArguments().getString("name"));
        address.setText(getArguments().getString("address"));
        contact.setText(getArguments().getString("contact"));
        gender.setText(getArguments().getString("gender"));

        n = getArguments().getString("name");
        email = getArguments().getString("email");
        add = getArguments().getString("address");
        con = getArguments().getString("contact");
        gen = getArguments().getString("gender");



        return view;

    }

    public void getInfoandSend() {
        sparseBooleanArray = lvApply.getCheckedItemPositions();
        int index = 0;
        String tag = "";

        for (index = 0; index < sparseBooleanArray.size(); index++) {
            if (sparseBooleanArray.valueAt(index) == true) {
                tag = tag + "," + String.valueOf(lvApply.getItemAtPosition(sparseBooleanArray.keyAt(index)));
                //   Toast.makeText(getActivity(), "" + tag, 5000).show();
                Log.i("xxxx", index + " " + tag);
                values = tag;
            }

        }

        sparseBooleanArray1 = lvSymptoms.getCheckedItemPositions();
        int counter = 0;
        String collection = "";
        for (counter = 0; counter < sparseBooleanArray1.size(); counter++) {
            if (sparseBooleanArray1.valueAt(counter) == true) {
                collection = collection + "," + String.valueOf(lvSymptoms.getItemAtPosition(sparseBooleanArray1.keyAt(counter)));
                valuesSymtpoms = collection;
            }
        }

        allergies = etAllergy.getText().toString();
        currmedi = etMedication.getText().toString();


        Toast.makeText(getActivity(),n+" "+email+" "+add+" "+gen+" "+values+" "+valuesSymtpoms, Toast.LENGTH_SHORT).show();
        SendDataToServer();

    }
    public void SendDataToServer()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(getActivity(),jsonObject.toString(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(),"Could not insert", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                  //  Log.i("tagjsonexp","tagjson"+e.toString());
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Showing error message if something goes wrong
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name",n);
                params.put("email",email);
                params.put("contact",con);
                params.put("address",add);
                params.put("age","19");
                params.put("gender",gen);
                //      params.put("date",date);
                params.put("family",values);
                params.put("symptoms",valuesSymtpoms);
                params.put("allergy",allergies);
                params.put("pregnant",pregnant);
                params.put("druguse",drug);
                params.put("alcohol",alcohol);
                params.put("medication",currmedi);
                params.put("tobacco",tobacco);


                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);

    }

}


