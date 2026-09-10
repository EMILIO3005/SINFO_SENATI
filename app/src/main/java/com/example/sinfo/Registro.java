package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    EditText edtApellidos, edtNombres, edtTelefono, edtDireccion, edtEmail;
    Button btnGuardar;


    private void loadUI(){
        edtApellidos = findViewById(R.id.edtApellidos);
        edtNombres = findViewById(R.id.edtNombres);
        edtTelefono = findViewById(R.id.edtTelefono);
        edtDireccion = findViewById(R.id.edtDireccion);
        edtEmail = findViewById(R.id.edtEmail);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    //1. Objeto que sirva como canal de comunicacion
    RequestQueue requestQueue;

    //Enpoint (direccion que apunta a ws)

    private final String URL = "http://192.168.101.25:3000/alumnos";

    /***
     * Envia los datos del formulario a la BD a traves del WebService
     */

    private void registrarAlumno(){

        //Objeto de conexion
        requestQueue = Volley.newRequestQueue(this);

        //Creamos un JSON que contendra los datos a enviar
        JSONObject jsonObject = new JSONObject();

        //Asignamos los datos al JSON

        try {

            jsonObject.put("apellidos", edtApellidos.getText().toString());
            jsonObject.put("nombres", edtNombres.getText().toString());
            jsonObject.put("telefono", edtTelefono.getText().toString());
            jsonObject.put("direccion", edtDireccion.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());

        }catch (JSONException e) {
            Log.e("Error_JSON", e.toString());
            throw new RuntimeException(e);
        }

        // Que metodo utilizare para enviar los datos? - Rpta: POST

        // ¿Que objeto obtengo del WS? - Rpta: JSON

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String mensaje = jsonObject.getString("message");
                            int id = jsonObject.getInt("id");

                            Toast.makeText(getApplicationContext(),mensaje + "- ID:" + id, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),"No se pudo guardar", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        //Enviar los datos al servidor
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        loadUI();

        btnGuardar.setOnClickListener(v -> {this.registrarAlumno();});

    }
}