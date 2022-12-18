package com.example.Silmax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword;
    Button botonIngresar;
    String usuario1 = "patrick23@gmail.com";
    String password1 = "patrick";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText)findViewById(R.id.lblUsuario);
        txtPassword = (EditText)findViewById(R.id.lblPassword);
        botonIngresar =(Button)findViewById(R.id.btnIngresar);

        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loguear(txtUsuario.getText().toString(),txtPassword.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void loguear(String usuario, String password) throws InterruptedException {

        if(!usuario1.equals(usuario) || !password1.equals(password)){
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Error")
                    .setContentText("Ingrese el correo o contraseña correcta!")
                    .show();
            return;
        }

        if(usuario1.equals(usuario) && password1.equals(password)){

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Correcto")
                    .setContentText("Datos Correctos")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    })
                    .show();

        }




    }
}