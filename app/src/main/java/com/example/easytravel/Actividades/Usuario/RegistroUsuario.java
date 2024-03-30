package com.example.easytravel.Actividades.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Modelo.Usuario;
import com.example.easytravel.R;
import com.example.easytravel.Utils.Apis;
import com.example.easytravel.Utils.UsuarioService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuario extends AppCompatActivity {
    UsuarioService service;
    Spinner spinnerPais;
    Spinner spinnerCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registro);
        final EditText txtNombres = findViewById(R.id.txtNombres);
        final EditText txtCedula = findViewById(R.id.txtCedula);
        final EditText txtTelefono = findViewById(R.id.txtTelefono);
        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        final EditText txtDireccion = findViewById(R.id.txtDireccion);
        final EditText txtCorreo = findViewById(R.id.txtCorreo);
        final EditText txtContrasena = findViewById(R.id.txtContrasena);
        Button btnSave = findViewById(R.id.btnSave);

        // Adaptador para el Spinner de países
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapterPaises);

        btnSave.setOnClickListener(v -> {
            String nombre = txtNombres.getText().toString().trim();
            String cedula = txtCedula.getText().toString().trim();
            String telefono = txtTelefono.getText().toString().trim();
            String pais = spinnerPais.getSelectedItem().toString();
            String ciudad = spinnerCiudad.getSelectedItem().toString();
            String direccion = txtDireccion.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String contrasena = txtContrasena.getText().toString().trim();

            if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(RegistroUsuario.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                Usuario u = new Usuario(nombre, cedula, telefono, pais, ciudad, direccion, correo, contrasena);
                agregarUsuario(u);
            }
        });

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
    }

    private void DepartamentosUruguay() {
        String paisSeleccionado = spinnerPais.getSelectedItem().toString();
        if (paisSeleccionado.equals("Uruguay")) {
            ArrayAdapter<CharSequence> adapterDepartamentos = ArrayAdapter.createFromResource(this, R.array.departamentos, android.R.layout.simple_spinner_item);
            adapterDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCiudad.setAdapter(adapterDepartamentos);
        }
    }
    private void EstadosBrasil() {
        String paisSeleccionado = spinnerPais.getSelectedItem().toString();
        if (paisSeleccionado.equals("Brasil")) {
            ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.rio_grande_do_sul, android.R.layout.simple_spinner_item);
            adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCiudad.setAdapter(adapterEstados);
        }
    }
    private void agregarUsuario(Usuario u) {
        service = Apis.getUsuarioService();
        Call<Usuario> call = service.agregarUsuario(u);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(@NonNull Call<Usuario> call, @NonNull Response<Usuario> response) {
                if (response.isSuccessful()) {
                    // Imprime la respuesta recibida para verificar si contiene los datos esperados
                    Log.d("Respuesta del servidor:", response.body().toString());

                    // Si el código de estado es correcto, muestra el mensaje de éxito y redirige a otra actividad
                    Toast.makeText(RegistroUsuario.this, "Se agregó con éxito", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistroUsuario.this, LoginUsuario.class);
                    startActivity(intent);
                } else {
                    // Si la respuesta no es exitosa, muestra el código de estado y el mensaje de error correspondiente
                    Log.e("Error en respuesta:", "Código de estado HTTP: " + response.code());
                    Toast.makeText(RegistroUsuario.this, "Error en respuesta del servidor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Usuario> call, @NonNull Throwable t) {
                // En caso de que falle la solicitud, muestra un mensaje de error y registra la excepción
                Log.e("Error en la solicitud:", Objects.requireNonNull(t.getMessage()));
                Toast.makeText(RegistroUsuario.this, "Error al agregar persona", Toast.LENGTH_LONG).show();
            }

        });
    }
}
