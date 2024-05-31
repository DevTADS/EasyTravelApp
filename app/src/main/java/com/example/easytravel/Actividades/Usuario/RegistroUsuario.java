package com.example.easytravel.Actividades.Usuario;

import com.example.easytravel.Conexion.FirebaseConnection;
import com.example.easytravel.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

public class RegistroUsuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editTextNombre;
    EditText editTextEmail;
    EditText editTextPassword;
    Spinner spinnerPais;
    Spinner spinnerCiudad;
    EditText editTextCedula;
    EditText editTextTelefono;
    EditText editTextDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_activity_registro);

        mAuth = FirebaseAuth.getInstance();
        editTextNombre = findViewById(R.id.ednombre);
        editTextEmail = findViewById(R.id.etemail);
        editTextPassword = findViewById(R.id.etcontraseña);
        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        editTextCedula = findViewById(R.id.txtCedula);
        editTextTelefono = findViewById(R.id.txtTelefono);
        editTextDireccion = findViewById(R.id.txtDireccion);
        Button btnRegresar = findViewById(R.id.btn_regresar);
        Button btnInsertar = findViewById(R.id.btn_registrar);

        // Configuración del Spinner de Países
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapterPaises);

        spinnerPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionarPais = parent.getItemAtPosition(position).toString();
                if (seleccionarPais.equals("Uruguay")) {
                    DepartamentosUruguay();
                } else if (seleccionarPais.equals("Brasil")) {
                    EstadosBrasil();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Botón para insertar nuevo usuario
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        // Botón para regresar a la pantalla anterior
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroUsuario.this, LoginUsuario.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Método para insertar nuevo usuario
    private void registrarUsuario() {
        final String nombre = editTextNombre.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String pais = spinnerPais.getSelectedItem().toString().trim();
        final String ciudad = spinnerCiudad.getSelectedItem().toString().trim();
        final String cedula = editTextCedula.getText().toString().trim();
        final String telefono = editTextTelefono.getText().toString().trim();
        final String direccion = editTextDireccion.getText().toString().trim();

        if (nombre.isEmpty()) {
            editTextNombre.setError("Este campo no puede estar vacío");
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Este campo no puede estar vacío");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Este campo no puede estar vacío");
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("La contraseña debe tener mínimo 6 caracteres");
            return;
        }
        if (cedula.isEmpty()) {
            editTextCedula.setError("Este campo no puede estar vacío");
            return;
        }
        if (telefono.isEmpty()) {
            editTextTelefono.setError("Este campo no puede estar vacío");
            return;
        }
        if (direccion.isEmpty()) {
            editTextDireccion.setError("Este campo no puede estar vacío");
            return;
        }

        // Mostrar el ProgressDialog mientras se procesa la solicitud
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espera...");
        progressDialog.show();

        // Realizar solicitud HTTP para registrar el usuario
        String url = "https://qybdatye.lucusvirtual.es/easytravel/usuario/insertar.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss(); // Ocultar el ProgressDialog

                        // Mostrar la respuesta del servidor para depuración
                        Toast.makeText(RegistroUsuario.this, response, Toast.LENGTH_SHORT).show();

                        if (response.equalsIgnoreCase("Datos insertados")) {
                            // Usuario registrado correctamente en el servidor
                            FirebaseConnection firebaseConnection = new FirebaseConnection();
                            firebaseConnection.registerUser(email, password, RegistroUsuario.this);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss(); // Ocultar el ProgressDialog en caso de error
                        Toast.makeText(RegistroUsuario.this, "Error al registrar el usuario: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("password", password);
                params.put("pais", pais);
                params.put("ciudad", ciudad);
                params.put("direccion", direccion);
                params.put("cedula", cedula);
                params.put("telefono", telefono);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    // Spinner de departamentos de Uruguay
    private void DepartamentosUruguay() {
        ArrayAdapter<CharSequence> adapterDepartamentos = ArrayAdapter.createFromResource(this, R.array.departamentos, android.R.layout.simple_spinner_item);
        adapterDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapterDepartamentos);
    }

    // Spinner de estados de Brasil
    private void EstadosBrasil() {
        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.rio_grande_do_sul, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapterEstados);
    }
}
