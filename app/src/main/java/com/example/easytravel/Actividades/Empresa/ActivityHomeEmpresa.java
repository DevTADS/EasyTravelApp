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

    private BaseDatos_FirestoreHelper baseDatosFirestoreHelper;
    private Spinner spinnerTipoServicio, spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;

    private String idEmpresa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_home);

        baseDatosFirestoreHelper = new BaseDatos_FirestoreHelper();
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
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    idEmpresa = currentUser.getUid();

                    String tipoServicio = spinnerTipoServicio.getSelectedItem().toString();
                    String pais = spinnerPais.getSelectedItem().toString();
                    String ciudad = spinnerCiudad.getSelectedItem().toString();
                    String nombre = nombreEditText.getText().toString();
                    String telefono = telefonoEditText.getText().toString();
                    String direccion = direccionEditText.getText().toString();

                    if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
                        mostrarToast("Todos los campos son obligatorios");
                    } else {
                        // Crear datos del hotel
                        Map<String, Object> datosHotel = new HashMap<>();
                        datosHotel.put("idEmpresa", idEmpresa); // Agregar el ID de la empresa como un campo
                        datosHotel.put("tipoServicio", tipoServicio);
                        datosHotel.put("pais", pais);
                        datosHotel.put("ciudad", ciudad);
                        datosHotel.put("nombre", nombre);
                        datosHotel.put("telefono", telefono);
                        datosHotel.put("direccion", direccion);

                        // Agregar el hotel en Firestore
                        baseDatosFirestoreHelper.addHotel("hotel", datosHotel, new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    // Éxito al agregar el hotel en Firestore
                                    mostrarToast("Hotel registrado correctamente");
                                    limpiarCampos();
                                } else {
                                    // Error al agregar el hotel en Firestore
                                    mostrarToast("Error al registrar hotel");
                                }
                            }
                        });
                    }
                } else {
                    mostrarToast("No se pudo obtener el ID de la empresa");
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
