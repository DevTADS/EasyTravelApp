package com.example.easytravel.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;

public class ActivityHomeEmpresa extends AppCompatActivity {

    private EditText tipo_Empresa;
    private EditText pais_Empresa;
    private EditText ciudad_Empresa;
    private Button registro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empresa);

        tipo_Empresa = findViewById(R.id.Tipo_empresa);
        pais_Empresa = findViewById(R.id.Pais);
        ciudad_Empresa = findViewById(R.id.Ciudad);
        registro = findViewById(R.id.btn_registrarse);


        // Obtener los valores del formulario
        String tipoEmpresa = tipo_Empresa.getText().toString();
        String pais = pais_Empresa.getText().toString();
        String ciudad = ciudad_Empresa.getText().toString();
/*
        // Configurando OnClickListener para el botón de registro
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro al hacer clic en el botón de registro
                startActivity(new Intent(ActivityLoginUsuario.this, ActivityRegistro.class));
            }
        });

     */

    }
}

