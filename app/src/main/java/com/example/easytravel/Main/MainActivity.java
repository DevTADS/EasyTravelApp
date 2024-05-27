package com.example.easytravel.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Actividades.Empresa.LoginEmpresa;
import com.example.easytravel.Actividades.Usuario.HomeeUsuario;
import com.example.easytravel.Actividades.Usuario.LoginUsuario;
import com.example.easytravel.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si el usuario ha iniciado sesión
        SharedPreferences sharedPreferences = getSharedPreferences("Usuario", MODE_PRIVATE);
        if (sharedPreferences.contains("id_usuario")) {
            // El usuario ya ha iniciado sesión, redirigir a HomeUsuario
            Intent intent = new Intent(MainActivity.this, HomeeUsuario.class);
            startActivity(intent);
            finish();
            return; // Salir del método onCreate para no cargar el layout de selección de tipo de usuario
        }

        setContentView(R.layout.activity_main);

        // Botón para ingresar como usuario
        Button btn_usuario = findViewById(R.id.btn_usuario);
        btn_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginUsuario.class);
                startActivity(intent);
            }
        });

        // Botón para ingresar como empresa
        Button btn_empresa = findViewById(R.id.btn_empresa);
        btn_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginEmpresa.class);
                startActivity(intent);
            }
        });
    }
}
