package com.example.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<contacto> list = new ArrayList<>();

    public rvAdapter(Context context){
        this.context = context;
    }

    public void setItems(ArrayList<contacto> listC){
        this.list.addAll(listC);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new contactoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        contactoVH vh = (contactoVH) holder;
        contacto c = list.get(position);
        vh.txtNombre.setText(c.getNombre());
        vh.txtTelefono.setText(c.getTelefono());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
