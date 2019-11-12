package com.example.pallavi.navigationdrawerapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by PALLAVI on 31/03/2018.
 */

public class Home_fragment extends Fragment {

    Button uploadbtn;
    Button ask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.homepage, container, false);


        uploadbtn = (Button) view.findViewById(R.id.btnUploadMH);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name,address,age,gender,contact,email;

                name = getArguments().getString("name");
                address = getArguments().getString("address");
                contact = getArguments().getString("contact");
                email = getArguments().getString("email");
                age = getArguments().getString("age");
                gender = getArguments().getString("gender");

                Bundle b = new Bundle();
                b.putString("name",name);
                b.putString("address",address);
                b.putString("age",age);
                b.putString("email",email);
                b.putString("contact",contact);
                b.putString("gender",gender);

                Fragment f = new UploadMedicalDetails();
                f.setArguments(b);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_main, f);
                ft.commit();

            }
        });

        ask = (Button) view.findViewById(R.id.askqs);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_MAIN);
                PackageManager packageManager = getActivity().getPackageManager();
                i = packageManager.getLaunchIntentForPackage("com.example.user.medigo");
                startActivity(i);
            }
        });



            return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Home");

    }

}
