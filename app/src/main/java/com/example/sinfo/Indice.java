package com.example.sinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Indice extends AppCompatActivity {

    Button btnIrListado, btnIrBuscador, btnIrRegistro;

    private void loadUi(){
        btnIrListado = findViewById(R.id.btnIrListado);
        btnIrBuscador = findViewById(R.id.btnIrBuscador);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);
    }

    private void openActivity(Class interfaz) {
        Intent i = new Intent(getApplicationContext(), interfaz);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_indice);

        loadUi();

        btnIrListado.setOnClickListener(v ->{openActivity(Listado.class);});
        btnIrBuscador.setOnClickListener(v -> {openActivity(Buscador.class);});
        btnIrRegistro.setOnClickListener(v -> {openActivity(Registro.class);});

    }
}