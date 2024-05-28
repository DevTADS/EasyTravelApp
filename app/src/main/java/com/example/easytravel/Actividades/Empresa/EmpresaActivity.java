package com.example.easytravel.Actividades.Empresa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Actividades.Hotel.RegistroHotel;
import com.example.easytravel.R;

public class EmpresaActivity extends AppCompatActivity {

    private String id_empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_activity_home);

        // Recibir el ID y el nombre de la empresa desde Intent o SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Empresa", MODE_PRIVATE);
        id_empresa = getIntent().getStringExtra("id_empresa");
        String nombre = getIntent().getStringExtra("nombre");

        if (id_empresa == null || nombre == null) {
            id_empresa = sharedPreferences.getString("id_empresa", null);
            nombre = sharedPreferences.getString("nombre", null);
        }

        // Guardar id_empresa en SharedPreferences si no está guardado
        if (id_empresa != null && nombre != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id_empresa", id_empresa);
            editor.putString("nombre", nombre);
            editor.apply();
        } else {
            // Manejar el caso cuando no se obtuvieron datos
            Toast.makeText(this, "Error: no se pudo obtener los datos de la empresa", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Mostrar el nombre de la empresa en el TextView
        TextView tvNombreEmpresa = findViewById(R.id.tv_nombre_empresa);
        tvNombreEmpresa.setText(nombre);

        // Configurar el botón para agregar un hotel
        ImageButton iv_addhotel = findViewById(R.id.iv_addhotel);
        iv_addhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpresaActivity.this, RegistroHotel.class);
                startActivity(intent);
            }
        });

        // Configurar el botón para agregar un restaurante
        ImageButton iv_addrestaurante = findViewById(R.id.iv_addrestaurante);
        iv_addrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí deberías cambiar RegistroHotel.class por la actividad correspondiente para registrar un restaurante
                Intent intent = new Intent(EmpresaActivity.this, RegistroHotel.class);
                startActivity(intent);
            }
        });
    }
}
