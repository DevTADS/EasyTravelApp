package com.example.easytravel.Actividades.Empresa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;
import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class ActivityHomeEmpresa extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario
    private Spinner spinnerTipoServicio, spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;

    private String idEmpresa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_home);

        // Vinculación de las variables con los elementos del layout
        spinnerTipoServicio = findViewById(R.id.spinnerTipoServicio);
        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        nombreEditText = findViewById(R.id.etNombre);
        telefonoEditText = findViewById(R.id.etTelefono);
        direccionEditText = findViewById(R.id.etDireccion);

        Button registroButton = findViewById(R.id.btn_registrarse);

        // Configuración del Spinner Tipo de Servicio
        String[] tiposServicio = {"Hotel", "Restaurante"};
        ArrayAdapter<String> tipoServicioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposServicio);
        tipoServicioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoServicio.setAdapter(tipoServicioAdapter);

        // Configuración del Spinner País
        String[] paises = getResources().getStringArray(R.array.paises); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> paisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paises);
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(paisAdapter);

        // Configuración del Spinner Ciudad
        String[] ciudades = getResources().getStringArray(R.array.ciudades_uruguay); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> ciudadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudades);
        ciudadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(ciudadAdapter);

        // Configuración del botón de registro
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerIdEmpresaYRegistrarHotel();
            }
        });
    }

    // Método para obtener el ID de la empresa y registrar el hotel
    private void obtenerIdEmpresaYRegistrarHotel() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // El usuario está autenticado, obtén el ID de la empresa del perfil del usuario
            idEmpresa = currentUser.getUid();

            // Ahora puedes utilizar idEmpresa para registrar el hotel asociado a esa empresa
            registrarHotel();
        } else {
            // El usuario no está autenticado, maneja el caso en consecuencia
            Toast.makeText(ActivityHomeEmpresa.this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para registrar el hotel en Firestore
    private void registrarHotel() {
        // Obtener datos ingresados por el usuario
        String tipoServicio = spinnerTipoServicio.getSelectedItem().toString();
        String nombre = nombreEditText.getText().toString();
        String pais = spinnerPais.getSelectedItem().toString();
        String ciudad = spinnerCiudad.getSelectedItem().toString();
        String telefono = telefonoEditText.getText().toString();
        String direccion = direccionEditText.getText().toString();

        // Crear un nuevo mapa con los datos del hotel
        Map<String, Object> datosHotel = new HashMap<>();
        datosHotel.put("nombre", nombre);
        datosHotel.put("pais", pais);
        datosHotel.put("ciudad", ciudad);
        datosHotel.put("telefono", telefono);
        datosHotel.put("direccion", direccion);

        // Instanciar el helper de Firestore
        BaseDatos_FirestoreHelper basededatosFirestoreHelper = new BaseDatos_FirestoreHelper();

        // Llamar al método para agregar un hotel en Firestore
        basededatosFirestoreHelper.addHotel(idEmpresa, datosHotel, new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    mostrarToast("Hotel registrado correctamente");
                    limpiarCampos();
                } else {
                    mostrarToast("Error al registrar el hotel: " + task.getException().getMessage());
                }
            }
        });
    }

    // Método para mostrar un Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(ActivityHomeEmpresa.this, mensaje, Toast.LENGTH_LONG).show();
    }

    // Método para limpiar los campos de entrada después de registrar un hotel
    private void limpiarCampos() {
        nombreEditText.setText("");
        telefonoEditText.setText("");
        direccionEditText.setText("");
        spinnerTipoServicio.setSelection(0);
        spinnerPais.setSelection(0);
        spinnerCiudad.setSelection(0);
    }
}
