package com.example.easytravel.Actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;

public class ActivityHomeEmpresa extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario
    private Spinner spinnerTipoEmpresaa, spinnerPaiss, spinnerCiudadd;
    private EditText latitudEditText, longitudEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity__home_empresa);

        // Vinculación de las variables con los elementos del layout
        spinnerTipoEmpresaa = findViewById(R.id.spinnerTipoEmpresa);
        spinnerPaiss = findViewById(R.id.spinnerPais);
        spinnerCiudadd = findViewById(R.id.spinnerCiudad);
        latitudEditText = findViewById(R.id.Latirud);
        longitudEditText = findViewById(R.id.Longitud);
        Button registroButton = findViewById(R.id.btn_registrarse);

        // Configuración del Spinner Tipo de Empresa
        String[] tiposEmpresa = getResources().getStringArray(R.array.tipos_empresa); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> tipoEmpresaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposEmpresa);
        tipoEmpresaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoEmpresaa.setAdapter(tipoEmpresaAdapter);

        // Configuración del Spinner País
        String[] paises = getResources().getStringArray(R.array.paises); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> paisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paises);
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaiss.setAdapter(paisAdapter);

        // Configuración del Spinner Ciudad
        String[] ciudades = getResources().getStringArray(R.array.ciudades_uruguay); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> ciudadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudades);
        ciudadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudadd.setAdapter(ciudadAdapter);

        // Configuración del botón de registro
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos ingresados por el usuario
                String tipoEmpresa = spinnerTipoEmpresaa.getSelectedItem().toString();
                String pais = spinnerPaiss.getSelectedItem().toString();
                String ciudad = spinnerCiudadd.getSelectedItem().toString();
                String latitud = latitudEditText.getText().toString();
                String longitud = longitudEditText.getText().toString();

                // Mostrar los datos en un Toast (puedes hacer aquí el manejo de los datos según tu lógica)
                String mensaje = "Tipo de Empresa: " + tipoEmpresa + "\nPaís: " + pais + "\nCiudad: " + ciudad + "\nLatitud: " + latitud + "\nLongitud: " + longitud;
                Toast.makeText(ActivityHomeEmpresa.this, mensaje, Toast.LENGTH_LONG).show();
            }
        });
    }
}
