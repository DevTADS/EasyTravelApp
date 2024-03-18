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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                // Crear un nuevo objeto Hotel o Restaurante según el tipo de servicio seleccionado
                if (tipoServicio.equals("Hotel")) {
                    Hotel hotel = new Hotel();
                    hotel.setNombre(nombre);
                    hotel.setPais(pais);
                    hotel.setCiudad(ciudad);
                    hotel.setTelefono(telefono);
                    hotel.setDireccion(direccion);

                    // Ahora puedes guardar el hotel en la base de datos Firebase Realtime Database
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("hoteles");
                    String hotelId = databaseReference.push().getKey(); // Generar una clave única para el hotel
                    databaseReference.child(hotelId).setValue(hotel);

                    mostrarToast("Hotel registrado correctamente");
                } else if (tipoServicio.equals("Restaurante")) {
                    // Implementa la lógica para guardar un restaurante en la base de datos
                }
            }
        });
    }

    // Método para mostrar un Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(ActivityHomeEmpresa.this, mensaje, Toast.LENGTH_LONG).show();
    }
}
