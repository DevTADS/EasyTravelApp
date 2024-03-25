package com.example.easytravel.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Actividades.Empresa.ActivityLoginEmpresa;
import com.example.easytravel.Actividades.Usuario.ActivityLoginUsuario;
import com.example.easytravel.R;


public class ActivityInicioSesion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);


        Button btn_usuario = findViewById(R.id.btn_usuario);
        btn_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInicioSesion.this, ActivityLoginUsuario.class);
                startActivity(intent);
            }
        });
        Button btn_empresa = findViewById(R.id.btn_empresa);
        btn_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInicioSesion.this, ActivityLoginEmpresa.class);
                startActivity(intent);
            }
        });
    }
}
