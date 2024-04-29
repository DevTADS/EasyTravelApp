package com.example.easytravel.Actividades.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Actividades.Hotel.RegistroHotel;
import com.example.easytravel.R;

public class HomeEmpresa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_activity_home);

        // Recibir el ID y el nombre de la empresa
        String id_empresa = getIntent().getStringExtra("id_empresa");
        String nombre = getIntent().getStringExtra("nombre");

        // Mostrar el nombre de la empresa en el TextView
        TextView tvNombreEmpresa = findViewById(R.id.tv_nombre_empresa);
        tvNombreEmpresa.setText(nombre);

        // Configurar el botón para agregar un hotel
        ImageButton iv_addhotel = findViewById(R.id.iv_addhotel);
        iv_addhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmpresa.this, RegistroHotel.class);
                startActivity(intent);
            }
        });

        // Configurar el botón para agregar un restaurante
        ImageButton iv_addrestaurante = findViewById(R.id.iv_addrestaurante);
        iv_addrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí deberías cambiar RegistroHotel.class por la actividad correspondiente para registrar un restaurante
                Intent intent = new Intent(HomeEmpresa.this, RegistroHotel.class);
                startActivity(intent);
            }
        });
    }
}
