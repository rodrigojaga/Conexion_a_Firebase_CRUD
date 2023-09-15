package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.javafaker.Faker;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNombre = findViewById(R.id.editTextText);
        EditText editTextTelefono = findViewById(R.id.editTextText2);
        daoContacto dao = new daoContacto();
        Button btn = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Faker f = new Faker();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto con = new contacto(f.rickAndMorty().character(),f.phoneNumber().cellPhone());
                dao.add(con).addOnSuccessListener(suc->{

                    Toast.makeText(MainActivity.this,"Succesful",Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(er->{

                    Toast.makeText(MainActivity.this,"FATAL ERROR",Toast.LENGTH_SHORT).show();

                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("nombre",editTextNombre.getText().toString());
                hashMap.put("telefono",editTextTelefono.getText().toString());
                //key es la cosa que esta en Firebase realtime database
                dao.update("-NeBMVDnX7XqkQMPBFCy",hashMap).addOnSuccessListener(suc ->{
                    Toast.makeText(MainActivity.this,"Succesful edited",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->{
                    Toast.makeText(MainActivity.this,"FATAL ERROR",Toast.LENGTH_SHORT).show();
                });
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //key es la cosa que esta en Firebase realtime database
                dao.delete("-NeBMVDnX7XqkQMPBFCy").addOnSuccessListener(suc ->{
                    Toast.makeText(MainActivity.this,"Succesful deleted",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->{
                    Toast.makeText(MainActivity.this,"FATAL ERROR",Toast.LENGTH_SHORT).show();
                });
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,rvActivity.class);
                startActivity(intent);
            }
        });


    }



}