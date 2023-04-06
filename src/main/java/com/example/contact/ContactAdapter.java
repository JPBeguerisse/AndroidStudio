package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactItem> arrayList;
    ArrayList<ContactItem> arrayList_0 = new ArrayList<>();

    View view;

    public ContactAdapter(Context context, ArrayList<ContactItem> arrayList, ContactListener contactListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.contactListener = contactListener;
    }

    ContactListener contactListener;

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.rvlayout, parent, false);
        return new ViewHolder(view);
    }

    // ajout de nos valeur contact sur le checkbox en passant par un autre array
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(arrayList != null && arrayList.size() > 0)
        {
            holder.checkBox.setText(arrayList.get(position).getName() + " " + arrayList.get(position).getPhone());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.checkBox.isChecked())
                    {
                        //arrayList_0.add(arrayList.get(position).getName() + " " + arrayList.get(position).getPhone());
                        arrayList_0.add(arrayList.get(position));

                    }else{
                        arrayList_0.remove(arrayList.get(position));
                    }
                    contactListener.onContactChange(arrayList_0);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
