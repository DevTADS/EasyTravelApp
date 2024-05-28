package com.example.easytravel.Actividades.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easytravel.R;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroEmpresa extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText pass;
    Spinner spinnerPais;
    EditText txtTelefono;
    EditText txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.empresa_activity_registro);

        txtName = findViewById(R.id.ednombre);
        txtEmail = findViewById(R.id.etemail);
        pass = findViewById(R.id.etcontraseña);
        spinnerPais = findViewById(R.id.spinnerPais);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        Button btnRegresar = findViewById(R.id.btn_regresar);

        // Configuración del Spinner de Países
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapterPaises);


        Button btn_insert = findViewById(R.id.btn_register);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEmpresa();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistroEmpresa.this, LoginEmpresa.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void registrarEmpresa() {
        final String nombre = txtName.getText().toString().trim();
        final String correo = txtEmail.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String pais = spinnerPais.getSelectedItem().toString().trim();
        final String telefono = txtTelefono.getText().toString().trim();
        final String direccion = txtDireccion.getText().toString().trim();

        if (nombre.isEmpty()) {
            txtName.setError("Este campo no puede estar vacío");
            return;
        }
        if (correo.isEmpty()) {
            txtEmail.setError("Este campo no puede estar vacío");
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Este campo no puede estar vacío");
            return;
        }

        if (telefono.isEmpty()) {
            txtTelefono.setError("Este campo no puede estar vacío");
            return;
        }
        if (direccion.isEmpty()) {
            txtDireccion.setError("Este campo no puede estar vacío");
            return;
        }

        // Realizar solicitud HTTP para registrar el usuario
        String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/insertarempresa.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Mostrar la respuesta del servidor para depuración
                        Toast.makeText(RegistroEmpresa.this, response, Toast.LENGTH_SHORT).show();

                        if (response.equalsIgnoreCase("Datos insertados")) {
                            Toast.makeText(RegistroEmpresa.this, "Empresa registrada correctamente", Toast.LENGTH_SHORT).show();
                            // Puedes agregar aquí la lógica para navegar a la actividad de inicio de sesión
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistroEmpresa.this, "Error al registrar empresa: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("correo", correo);
                params.put("password", password);
                params.put("pais", pais);
                params.put("telefono", telefono);
                params.put("direccion", direccion);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}
