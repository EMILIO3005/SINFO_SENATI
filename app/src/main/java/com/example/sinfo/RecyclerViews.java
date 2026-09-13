package com.example.sinfo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViews extends AppCompatActivity {

    ArrayList<Alumno> listaPersona = new ArrayList<>();
    RecyclerView recyclerPersonas;


    private void loadUI(){
        recyclerPersonas = findViewById(R.id.recyclerPersonas);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_views);

        loadUI();
        recyclerPersonas.setLayoutManager(new LinearLayoutManager(this));
        AdapterDatos adaptador = new AdapterDatos(listaPersona);
        recyclerPersonas.setAdapter(adaptador);
    }
}