package com.example.easytravel.Actividades.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import com.example.easytravel.Actividades.Empresa.LoginEmpresa;
import com.example.easytravel.R;

import java.util.HashMap;
import java.util.Map;


public class LoginUsuario extends AppCompatActivity {

    EditText email, contraseña;
    String str_email, str_password;
    String url = "https://qybdatye.lucusvirtual.es/sistema/appusuario/usuario/login.php";
    Button forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_activity_login);

        email = findViewById(R.id.etemail);
        contraseña = findViewById(R.id.etcontraseña);
        forgotpass = findViewById(R.id.forgotpass);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("admin") && contraseña.getText().toString().equals("admin")) {
                    // Si el correo y la contraseña son "admin", abrir la actividad de administrador
                    Intent intent = new Intent(LoginUsuario.this, ActivityAdmin.class);
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
                Intent intent = new Intent(LoginUsuario.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUsuario.this, OlvidoContrasena.class);
                startActivity(intent);
            }
        });
    }

    private void Login() {
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (contraseña.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");
            progressDialog.show();

            str_email = email.getText().toString().trim();
            str_password = contraseña.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("ingreso correctamente")) {
                        email.setText("");
                        contraseña.setText("");
                        startActivity(new Intent(getApplicationContext(), HomeUsuario.class));
                        Toast.makeText(LoginUsuario.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginUsuario.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginUsuario.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    // Imprimir la respuesta de error completa
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String errorResponse = new String(error.networkResponse.data);
                        Log.e("Error Response", errorResponse);
                    }
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginUsuario.this);
            requestQueue.add(request);
        }
    }

}