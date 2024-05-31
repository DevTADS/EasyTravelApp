package com.example.easytravel.Actividades.Empresa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Main.MainActivity;
import com.example.easytravel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginEmpresa extends AppCompatActivity {

    EditText correo, contraseña;
    TextView mostrarPassword;
    String str_email, str_password;
    String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/login.php";
    Button olvideContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_activity_login);

        correo = findViewById(R.id.etemail);
        contraseña = findViewById(R.id.etcontraseña);
        mostrarPassword = findViewById(R.id.tv_mostrar_password);
        olvideContrasena = findViewById(R.id.forgotpass);
        Button btn_Login = findViewById(R.id.btn_login);
        Button btn_Registrar = findViewById(R.id.btn_registrar);
        ImageButton btn_volver = findViewById(R.id.btn_volver);

        // Manejar la visibilidad de la contraseña
        mostrarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseña.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    contraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mostrarPassword.setText("Ocultar");
                } else {
                    contraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mostrarPassword.setText("Mostrar");
                }
                contraseña.setSelection(contraseña.getText().length());
            }
        });

        // Botón para iniciar sesión
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(LoginEmpresa.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    Login();
                }
            }
        });

        // Botón para registrar empresa
        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmpresa.this, RegistroEmpresa.class);
                startActivity(intent);
            }
        });

       // Botón para volver a la actividad principal
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmpresa.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Método para iniciar sesión
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
                Log.d("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("status")) {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            // El inicio de sesión fue exitoso
                            String id_empresa = jsonObject.getString("id_empresa");
                            String nombre = jsonObject.getString("nombre");

                            // Guardar los datos de la empresa en SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("Empresa", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id_empresa", id_empresa);
                            editor.putString("nombre", nombre);
                            editor.apply();

                            // Redirigir a la actividad principal de la empresa
                            Intent intent = new Intent(LoginEmpresa.this, EmpresaActivity.class);
                            intent.putExtra("id_empresa", id_empresa); // Pasar el id_empresa a la actividad HomeEmpresa
                            intent.putExtra("nombre", nombre); // Pasar el nombre de la empresa a la actividad HomeEmpresa
                            startActivity(intent);
                            Toast.makeText(LoginEmpresa.this, "Bienvenido - Inicio Correcto !!", Toast.LENGTH_SHORT).show();
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
}
