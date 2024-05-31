package com.example.easytravel.Actividades.Usuario;

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
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUsuario extends AppCompatActivity {

    EditText email, contraseña;
    TextView togglePassword;
    String str_email, str_password;
    String url = "https://qybdatye.lucusvirtual.es/easytravel/usuario/login.php";
    Button olvideContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_activity_login);

        email = findViewById(R.id.etemail);
        contraseña = findViewById(R.id.etcontraseña);
        togglePassword = findViewById(R.id.tvTogglePassword);
        olvideContrasena = findViewById(R.id.forgotpass);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        // Manejar la visibilidad de la contraseña
        togglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contraseña.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Mostrar contraseña
                    contraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    togglePassword.setText("Ocultar");
                } else {
                    // Ocultar contraseña
                    contraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    togglePassword.setText("Mostrar");
                }
                // Mover el cursor al final del texto
                contraseña.setSelection(contraseña.getText().length());
            }
        });

        // Botón para iniciar sesión
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

        // Botón para ir a la actividad de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUsuario.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        // Botón para ir a la actividad de olvido de contraseña
        olvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUsuario.this, OlvidoContrasena.class);
                startActivity(intent);
            }
        });
    }

    // Método para iniciar sesión
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
                    if (response.equalsIgnoreCase("Bienvenido! Nos alegra verte por aqui de nuevo...")) {
                        email.setText("");
                        contraseña.setText("");

                        // Obtener los datos del usuario
                        Log.d("LoginUsuario", "Obteniendo datos del usuario para: " + str_email);
                        Obtener_id.obtenerDatosEmpresa(getApplicationContext(), str_email, new Obtener_id.EmpresaCallback() {
                            @Override
                            public void onSuccess(JSONObject usuario) {
                                // Guardar los datos del usuario en SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("Usuario", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                try {
                                    // Verificar la estructura del JSON antes de guardar
                                    if (usuario.has("id_usuario") && usuario.has("nombre") && usuario.has("email") && usuario.has("contrasena")) {
                                        editor.putString("id_usuario", usuario.getString("id_usuario"));
                                        editor.putString("nombre", usuario.getString("nombre"));
                                        editor.putString("email", usuario.getString("email"));
                                        editor.putString("contrasena", usuario.getString("contrasena"));
                                        // Añade aquí todos los datos que quieras guardar
                                        editor.apply();

                                        // Iniciar la actividad principal
                                        startActivity(new Intent(getApplicationContext(), UsuarioActivity.class));
                                        Toast.makeText(LoginUsuario.this, response, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("LoginUsuario", "Respuesta JSON no contiene los campos esperados");
                                        Toast.makeText(LoginUsuario.this, "Error en la estructura de los datos del usuario", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginUsuario.this, "Error al guardar los datos del usuario", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(String mensajeError) {
                                Toast.makeText(LoginUsuario.this, mensajeError, Toast.LENGTH_SHORT).show();
                            }
                        });
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
