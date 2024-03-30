package com.example.easytravel.Actividades.Usuario;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.easytravel.Modelo.Usuario;
import com.example.easytravel.R;
import com.example.easytravel.Util.Apis;
import com.example.easytravel.Util.UsuarioService;
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
        Button btnGuardar = findViewById(R.id.btnSave);

        // Adaptador para el Spinner de países
        ArrayAdapter<CharSequence> adapterPaises = ArrayAdapter.createFromResource(this, R.array.paises, android.R.layout.simple_spinner_item);
        adapterPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(adapterPaises);

        // Configurar el botón para guardar el nuevo usuario
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
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

    private void agregarUsuario() {

        String nombre = txtNombres.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String telefono = txtTelefono.getText().toString().trim();
        String pais = spinnerPais.getSelectedItem().toString();
        String ciudad = spinnerCiudad.getSelectedItem().toString();
        String direccion = txtDireccion.getText().toString().trim();
        String correo = txtCorreo.getText().toString().trim();
        String contrasena = txtContrasena.getText().toString().trim();

        Usuario usuario = new Usuario(nombre, cedula, telefono, pais, ciudad, direccion, correo, contrasena);

        // Obtener la instancia del servicio de usuario
        UsuarioService usuarioService = Apis.getUsuarioService();

        // Realizar la llamada al servidor para agregar el usuario
        Call<Usuario> call = usuarioService.agregarUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    // El usuario se agregó correctamente
                    Toast.makeText(RegistroUsuario.this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                    // Aquí puedes realizar alguna acción adicional si es necesario
                } else {
                    // Ocurrió un error al agregar el usuario
                    Toast.makeText(RegistroUsuario.this, "Error al agregar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Ocurrió un error de red o en la comunicación con el servidor
                Toast.makeText(RegistroUsuario.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
