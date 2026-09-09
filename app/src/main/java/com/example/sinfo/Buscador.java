package com.example.sinfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Buscador extends AppCompatActivity {

    EditText edtCodigo, edtApellidosB, edtNombresB, edtTelefonoB, edtDireccionB, edtEmailB;

    Button btnBuscar, btnEliminar, btnActualizar, btnReiniciar;

    private void loadUI(){
        edtCodigo = findViewById(R.id.edtCodigo);
        edtApellidosB = findViewById(R.id.edtApellidosB);
        edtNombresB = findViewById(R.id.edtNombresB);
        edtTelefonoB = findViewById(R.id.edtTelefonoB);
        edtDireccionB = findViewById(R.id.edtDireccionB);
        edtEmailB = findViewById(R.id.edtEmailB);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
    }




    private void limpiarResultados() {
        edtApellidosB.setText("");
        edtNombresB.setText("");
        edtTelefonoB.setText("");
        edtDireccionB.setText("");
        edtEmailB.setText("");
    }

    private void limpiarTodo() {
        edtCodigo.setText("");
        limpiarResultados();
        edtCodigo.requestFocus();
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        loadUI();

        btnActualizar.setOnClickListener(v ->{});
        btnEliminar.setOnClickListener(v ->{});
        btnReiniciar.setOnClickListener(v ->{limpiarTodo();});


    }
}