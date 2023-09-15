package com.example.firebaseapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class contactoVH extends RecyclerView.ViewHolder {

    public TextView txtNombre,txtTelefono;


    public contactoVH(@NonNull View itemView) {
        super(itemView);

        txtNombre = itemView.findViewById(R.id.txtNombre);
        txtTelefono = itemView.findViewById(R.id.txtTelefono);
    }
}
