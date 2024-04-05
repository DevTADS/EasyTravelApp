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

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Model.Usuario;
import com.example.easytravel.R;
import com.example.easytravel.Utils.Apis;
import com.example.easytravel.Utils.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuario extends AppCompatActivity {
    UsuarioService service;
    Spinner spinnerPais;
    Spinner spinnerCiudad;
    EditText txtNombres;
    EditText txtCedula;
    EditText txtTelefono;
    EditText txtDireccion;
    EditText txtCorreo;
    EditText txtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registro);
        txtNombres = findViewById(R.id.txtNombres);
        txtCedula = findViewById(R.id.txtCedula);
        txtTelefono = findViewById(R.id.txtTelefono);
        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        // Adaptador para el Spinner de países
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapterPaises);


        Bundle bundle = getIntent().getExtras();
        String id = "";
        if (bundle != null) {
            id = bundle.getString("ID", "");
        }


        // Configurar el botón para guardar el nuevo usuario
        String finalId = id;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();

                u.setNombre(txtNombres.getText().toString());
                u.setCedula(txtCedula.getText().toString());
                u.setTelefono(txtTelefono.getText().toString());
                u.setPais(spinnerPais.getSelectedItem().toString());
                u.setCiudad(spinnerCiudad.getSelectedItem().toString());
                u.setDireccion(txtDireccion.getText().toString());
                u.setCorreo(txtCorreo.getText().toString());
                u.setContrasena(txtContrasena.getText().toString());


                    addUsuario(u);

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
        ArrayAdapter<CharSequence> adapterDepartamentos = ArrayAdapter.createFromResource(this, R.array.departamentos, android.R.layout.simple_spinner_item);
        adapterDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapterDepartamentos);
    }

    private void EstadosBrasil() {
        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.rio_grande_do_sul, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapterEstados);
    }

    private void addUsuario(Usuario u) {
        service = Apis.getUsuarioService();
        Call<Usuario> call = service.addUsuario(u);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroUsuario.this, "Se agrego conéxito", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Error:", t.getMessage());
            }
        });
    }
}
