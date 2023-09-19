package com.example.firebaseapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Objects;

public class daoContacto {

    private DatabaseReference databaseReference;

    public daoContacto(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(contacto.class.getSimpleName());
    }

    public Task<Void> add(contacto con){
        return databaseReference.push().setValue(con);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> delete (String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key){
        if(key == null){
            return databaseReference.orderByKey().limitToFirst(8);
        }else{
            return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
        }

    }
}
