package com.example.sinfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscador extends AppCompatActivity {

    EditText edtCodigo, edtApellidosB, edtNombresB, edtTelefonoB, edtDireccionB, edtEmailB;

    Button btnBuscar, btnEliminar, btnActualizar, btnReiniciar;
    RequestQueue requestQueue;

    private final String URL = "http://192.168.101.25:3000/alumnos";



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

        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);
    }

    private void validarError(int statusCode, String errorJSON){
        //404 No encontrado
        if (statusCode == 404) {
            try {
                JSONObject jsonObject = new JSONObject(errorJSON);
                String mensajeError = jsonObject.getString("message");
                this.resetUI();
                Toast.makeText(getApplicationContext(), mensajeError, Toast.LENGTH_LONG).show();
            }catch (JSONException e) {
                throw  new RuntimeException(e);
            }
        }
    }
    private void buscarAlumnos() {
        if (edtCodigo.getText().toString().isEmpty()){
            edtCodigo.setError("Campo requerido");
            edtCodigo.requestFocus();
            return;
        }
        requestQueue = Volley.newRequestQueue(this);
        String endPoind = URL + "/" + edtCodigo.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                endPoind,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            edtApellidosB.setText(jsonObject.getString("apellidos"));
                            edtNombresB.setText(jsonObject.getString("nombres"));
                            edtTelefonoB.setText(jsonObject.getString("telefono"));
                            edtDireccionB.setText(jsonObject.getString("direccion"));
                            edtEmailB.setText(jsonObject.getString("email"));

                            btnActualizar.setEnabled(true);
                            btnEliminar.setEnabled(true);

                        }catch (JSONException e){
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Manejo de errores
                        //Si el servidor retorna un codigo 40x (es un error)
                        NetworkResponse response = volleyError.networkResponse;

                        //Validar si existe un codigo de error
                        if (response != null && response.data != null){
                            //Mas Importante -> Saber el codigo de error
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);

                            validarError(statusCode, errorJSON);
                        }
                    }//Volley error
                }//ErrorListener

        );//JsonObjectRequest

        requestQueue.add(jsonObjectRequest);
    }

    private void actualizarAlumno(){

        // 1. Validar que el código no esté vacío
        if (edtCodigo.getText().toString().isEmpty()){
            edtCodigo.setError("Campo requerido");
            edtCodigo.requestFocus();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);
        // Definimos la misma ruta apuntando al código del alumno específico
        String endPoint = URL + "/" + edtCodigo.getText().toString();

        // 2. Creamos el objeto JSON con los nuevos datos capturados de la interfaz
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apellidos", edtApellidosB.getText().toString());
            jsonObject.put("nombres", edtNombresB.getText().toString());
            jsonObject.put("telefono", edtTelefonoB.getText().toString());
            jsonObject.put("direccion", edtDireccionB.getText().toString());
            jsonObject.put("email", edtEmailB.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 3. Crear la petición HTTP usando el metodo PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                endPoint,
                jsonObject,        // Enviamos el objeto con las modificaciones
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extraemos el mensaje de confirmación que devuelve tu API
                            String mensaje = response.getString("message");
                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();

                            // Opcional: puedes bloquear los botones otra vez tras guardar los cambios
                            btnActualizar.setEnabled(false);
                            btnEliminar.setEnabled(false);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Reutilizamos tu lógica de validación por si el servidor arroja un error estructurado
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null){
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se pudo actualizar el alumno", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // 4. Enviar la solicitud a la cola de Volley
        requestQueue.add(jsonObjectRequest);

    }

    private void resetUI(){
        edtCodigo.setText(null);
        edtApellidosB.setText(null);
        edtNombresB.setText(null);
        edtTelefonoB.setText(null);
        edtDireccionB.setText(null);
        edtEmailB.setText(null);

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        edtCodigo.requestFocus();

    }

    private void validarAccion(String accion){
        requestQueue = Volley.newRequestQueue(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Senati");
        builder.setMessage("¿Estas seguro de" + accion + "?");
        builder.setPositiveButton("Si", (a, b) ->{
            if (accion.equalsIgnoreCase("Eliminar")) this.eliminarAlumno();
            if (accion.equalsIgnoreCase("Actualizar")) this.actualizarAlumno();
        });
        builder.setNegativeButton("No", null);


        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void eliminarAlumno(){
        requestQueue = Volley.newRequestQueue(this);
        String endPoind = URL + "/" + edtCodigo.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoind,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            resetUI();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        loadUI();


        //Evento tiene que  ir con metodos
        btnBuscar.setOnClickListener(v ->{this.buscarAlumnos(); });
        btnActualizar.setOnClickListener(v ->{this.validarAccion("Actualizar");});
        btnEliminar.setOnClickListener(v ->{this.validarAccion("Eliminar");});
        btnReiniciar.setOnClickListener(v ->{this.resetUI();});


    }
}