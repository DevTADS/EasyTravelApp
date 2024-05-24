package com.example.easytravel.Actividades.Empresa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginEmpresa extends AppCompatActivity {

    EditText correo, contraseña;
    String str_email, str_password;
    String url = "https://qybdatye.lucusvirtual.es/sistema/appusuario/empresa/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_activity_login);

        correo = findViewById(R.id.etemail);
        contraseña = findViewById(R.id.etcontraseña);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(LoginEmpresa.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        str_email = correo.getText().toString().trim();
        str_password = contraseña.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Response", response); // Imprimir la respuesta JSON en el Logcat
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            // El inicio de sesión fue exitoso
                            String id_empresa = jsonObject.getString("id_empresa");
                            String nombre = jsonObject.getString("nombre");
                            // Guardar el id_empresa en las preferencias compartidas
                            guardarIdEmpresa(id_empresa);
                            // Redirigir a la actividad principal de la empresa
                            Intent intent = new Intent(LoginEmpresa.this, HomeEmpresa.class);
                            intent.putExtra("id_empresa", id_empresa); // Pasar el id_empresa a la actividad HomeEmpresa
                            intent.putExtra("nombre", nombre); // Pasar el nombre de la empresa a la actividad HomeEmpresa
                            startActivity(intent);
                            Toast.makeText(LoginEmpresa.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // El inicio de sesión falló
                            Toast.makeText(LoginEmpresa.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // La respuesta del servidor no contiene el campo "status"
                        Toast.makeText(LoginEmpresa.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginEmpresa.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginEmpresa.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void guardarIdEmpresa(String id_empresa) {
        // Obtener el objeto SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        // Crear un objeto SharedPreferences.Editor para editar el archivo SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Guardar el id_empresa en SharedPreferences
        editor.putString("id_empresa", id_empresa);
        // Aplicar los cambios
        editor.apply();
    }
}
