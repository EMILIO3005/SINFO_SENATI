package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    ListView lstAlumnos;

    RequestQueue requestQueue;
    //RecyclerView recyclerAlumnos;

    //ArrayList<Alumno> listAlumnos;
    //AlumnoAdapter alumnoAdapter;


    private final String URL = "http://192.168.101.25:3000/alumnos";

    private void loadUI(){
        lstAlumnos = findViewById(R.id.lstAlumnos);
    }
    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        //Log.e("Resultado", jsonArray.toString());
                        renderizarListView(jsonArray);
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error", volleyError.toString());
                    }
                }
        );
        requestQueue.add(JsonArrayRequest);

    }

    private void renderizarListView(JSONArray jsonArray){
        try {
            ArrayAdapter adapter;
            ArrayList<String> listaAlumnos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listaAlumnos.add(jsonObject.getString("apellidos") + "" + jsonObject.getString("nombres"));
            }
            //Adaptador -> es un mecanismo de software que permite transferir informacion | adapter = traductor
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAlumnos);
            lstAlumnos.setAdapter(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        loadUI();
        obtenerDatosWS();

    }

}