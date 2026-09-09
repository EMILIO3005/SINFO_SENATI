package com.example.sinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtPassword;
    Button btnLogin;

private void loadUI(){

    edtUsuario = findViewById(R.id.edtUsuario);
    edtPassword = findViewById(R.id.edtPassword);
    btnLogin = findViewById(R.id.btnLogin);
}
    private void autenticar(){
        String inputUsuario = edtUsuario.getText().toString().trim();
        String inputPassword = edtPassword.getText().toString().trim();

        boolean esUsuario = inputUsuario.equals("admin");
        boolean esPassCorrecto = inputPassword.equals("123456");

        boolean loginCorrecto = esUsuario && esPassCorrecto;

        edtUsuario.setError(inputUsuario.isEmpty() ? "Requerido" : null);
        edtPassword.setError(inputPassword.isEmpty() ? "Requerido" : null);

        Toast.makeText(this, loginCorrecto ? "Bienvenido" : "Credenciales incorrectas", Toast.LENGTH_SHORT).show();

        boolean navegar = loginCorrecto ? openActivity(Indice.class) : limpiarCampos();
    }
    private boolean limpiarCampos() {
        edtUsuario.setText("");
        edtPassword.setText("");
        edtUsuario.requestFocus();
        return true;
    }
    private boolean openActivity (Class<?> interfaz){
        Intent intent = new Intent(getApplicationContext(), interfaz);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        loadUI();
        btnLogin.setOnClickListener(v -> {autenticar();});

    }
}