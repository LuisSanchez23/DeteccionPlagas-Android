package com.example.oscar.learcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class recomendacionPlaga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacion_plaga);
    }
    public void regresar(View v){
        Intent inte= new Intent(this, CargarArchivo                                                                                                     .class);
        startActivity(inte);
    }
}
