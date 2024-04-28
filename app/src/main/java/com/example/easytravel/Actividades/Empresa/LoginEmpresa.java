package com.example.easytravel.Actividades.Empresa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Actividades.Administrador.ActivityAdmin;
import com.example.easytravel.R;

import java.util.HashMap;
import java.util.Map;

public class LoginEmpresa extends AppCompatActivity {

    EditText correo, contraseña;
    String str_email, str_password;
    String url = "https://tejuqiaq.lucusvirtual.es/loginempresa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_activity_login);

        correo = findViewById(R.id.etemail);
        contraseña = findViewById(R.id.etcontraseña);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.getText().toString().equals("admin") && contraseña.getText().toString().equals("admin")) {
                    // Si el correo y la contraseña son "admin", abrir la actividad de administrador
                    Intent intent = new Intent(LoginEmpresa.this, ActivityAdmin.class);
                    startActivity(intent);
                } else {
                    // Si no son "admin", ejecutar el método Login() para la lógica de inicio de sesión normal
                    Login();
                }
            }
        });


        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmpresa.this, RegistroEmpresa.class);
                startActivity(intent);
            }
        });
    }

    private void Login() {
        if (correo.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (contraseña.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");
            progressDialog.show();

            str_email = correo.getText().toString().trim();
            str_password = contraseña.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("ingreso correctamente")) {
                        correo.setText("");
                        contraseña.setText("");
                        startActivity(new Intent(getApplicationContext(), HomeEmpresa.class));
                        Toast.makeText(LoginEmpresa.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginEmpresa.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginEmpresa.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("correo", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginEmpresa.this);
            requestQueue.add(request);
        }
    }
}