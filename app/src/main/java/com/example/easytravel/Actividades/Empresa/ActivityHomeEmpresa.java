package com.example.easytravel.Actividades.Empresa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;
import com.example.easytravel.Utilidades.FirestoreHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class ActivityHomeEmpresa extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario
    private Spinner spinnerTipoServicio, spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;

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
                FirestoreHelper firestoreHelper = new FirestoreHelper();

                // Llamar al método para agregar una empresa en Firestore
                firestoreHelper.addEmpresa("hoteles", datosHotel, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            mostrarToast("Hotel registrado correctamente");
                        } else {
                            mostrarToast("Error al registrar el hotel: " + task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }

    // Método para mostrar un Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(ActivityHomeEmpresa.this, mensaje, Toast.LENGTH_LONG).show();
    }
}
