package com.example.oscar.learcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    //Button btn_iniciar;
    EditText servidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        servidor= findViewById(R.id.txtServidor);
    }
    public void sig(View v){
        CargarArchivo.WEB_SERVICE= "http://"+servidor.getText().toString()+"/subirImagen.php";
        System.out.println(CargarArchivo.WEB_SERVICE);
        Toast.makeText(Principal.this, "ADVERTENCIA: Solo tome fotos de plantas de cafe", Toast.LENGTH_LONG).show();
        Intent inte= new Intent(this, CargarArchivo                                                                                                     .class);
        startActivity(inte);

    }
}
