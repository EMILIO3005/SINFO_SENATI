package com.example.sinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaDetallada extends AppCompatActivity {

    //Contenedor tenporal
    ArrayList<String> listaAlumnos = new ArrayList<>();
    ArrayList<Alumno> lstAlumno = new ArrayList<>();

    //Adaptador (transferir informacion obtenida del ws > lista > RV
    AdapterDatos adapterDatos;

    //View donde se renderiza
    RecyclerView recyclerPersonas;


    //Canal de comunicacion
    RequestQueue requestQueue;


    //Enpoind del WS
    private final String URL = "http://192.168.1.72:3000/alumnos";

    private void loadUI(){ recyclerPersonas = findViewById(R.id.recyclerPersonas);}


    //Accseso al WS
    private void obtenerDatosWS() {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        //Log.i("Datos_obtenidos", jsonArray.toString());
                        String apellidos, nombres, telefono, direccion;
                        for (int i = 0; i < jsonArray.length(); i++){
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                apellidos = jsonObject.getString("apellidos");
                                nombres = jsonObject.getString("nombres");
                                telefono = jsonObject.getString("telefono");
                                direccion = jsonObject.getString("direccion");
                                lstAlumno.add(new Alumno(apellidos, nombres, telefono, direccion));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        adapterDatos.notifyDataSetChanged();

                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error_WS", volleyError.toString());
                    }
                }
        );
        requestQueue.add(JsonArrayRequest);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_detallada);

        this.loadUI();
        this.obtenerDatosWS();

        //Configuraciones generales
        recyclerPersonas.setLayoutManager(new LinearLayoutManager(this));
        adapterDatos = new AdapterDatos(lstAlumno);
        recyclerPersonas.setAdapter(adapterDatos);

    }
}