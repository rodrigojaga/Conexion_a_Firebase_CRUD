package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.javafaker.Faker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient client;

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
        Button btn5 = findViewById(R.id.button5);
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

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this,options);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent,1234);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }catch(ApiException e){
                Log.d("Errror",e.getMessage());
            }
        }
    }
}