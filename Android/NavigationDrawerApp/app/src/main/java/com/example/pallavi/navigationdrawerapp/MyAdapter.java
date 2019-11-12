package com.example.pallavi.navigationdrawerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<DoctorInfo> listItems;
    private Context context;

    public MyAdapter(List<DoctorInfo> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DoctorInfo list = listItems.get(position);

        String name = list.getDoc_name();
        String email = list.getDoc_email();
        holder.docName.setText(name);
        holder.docEmail.setText(email);
        holder.docContact.setText(list.getDoc_contact());
        holder.docAddress.setText(list.getDoc_address());
        holder.docCategory.setText(list.getDoc_category());



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView docName;
        public TextView docEmail;
        public TextView docCategory;
        public TextView docContact;
        public TextView docAddress;
        public Button btn;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            docName = (TextView) itemView.findViewById(R.id.tvDocName);
            docEmail = (TextView) itemView.findViewById(R.id.tvDocEmail);
            docContact = (TextView) itemView.findViewById(R.id.tvDocContact);
            docCategory = (TextView) itemView.findViewById(R.id.tvDocCategory);
            docAddress = (TextView) itemView.findViewById(R.id.tvDocAddress);
            btn = (Button) itemView.findViewById(R.id.btnApp);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLay);


        }
    }
}
