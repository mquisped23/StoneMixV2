package com.example.stonemixapp_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddActivity extends AppCompatActivity {

    EditText nombreMaterial,cantidadMaterial,descripcionMaterial,urlMaterial;
    Button btnAdd, btnBack;
    SweetAlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nombreMaterial = (EditText)findViewById(R.id.txtNombreMaterial);
        cantidadMaterial = (EditText)findViewById(R.id.txtCantidadMaterial);
        descripcionMaterial = (EditText)findViewById(R.id.txtDescripcionMaterial);
        urlMaterial = (EditText)findViewById(R.id.txtUrlMaterial);

        btnAdd =(Button)findViewById(R.id.btnAdd);
        btnBack =(Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }


    // --------------------------------
    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("nombreMaterial", nombreMaterial.getText().toString());
        map.put("cantidadMaterial", cantidadMaterial.getText().toString());
        map.put("descripcionMaterial", descripcionMaterial.getText().toString());
        map.put("urlMaterial", urlMaterial.getText().toString());

        if (nombreMaterial.getText().toString().trim().equals("") || cantidadMaterial.getText().toString().trim().equals("")
                || descripcionMaterial.getText().toString().trim().equals("") || urlMaterial.getText().toString().trim().equals("")) {
            new SweetAlertDialog(AddActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Error")
                    .setContentText("Completar campos vacios")
                    .show();
            return;
        } else {
            FirebaseDatabase.getInstance().getReference().child("materiales").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            new SweetAlertDialog(AddActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Correcto")
                                    .setContentText("Datos Agregados")
                                    .show();
                            clearAll();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Error al ingresar Datos", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
        }
    }
    private void clearAll()
    {
        nombreMaterial.setText("");
        cantidadMaterial.setText("");
        descripcionMaterial.setText("");
        urlMaterial.setText("");

    }
}