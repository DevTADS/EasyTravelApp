package com.example.easytravel.Actividades.Empresa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;


import java.util.HashMap;
import java.util.Map;

public class ActivityHomeEmpresa extends AppCompatActivity {


    private Spinner spinnerTipoServicio, spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;

    private String idEmpresa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_home);


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


    }
}
