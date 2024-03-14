package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_usuario = findViewById(R.id.btn_usuario);
        btn_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad ActivityUser
                Intent intent = new Intent(MainActivity.this, ActivityUser.class);
                startActivity(intent);
            }
        });
        Button btn_empresa = findViewById(R.id.btn_empresa);
        btn_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia la actividad ActivityUser
                Intent intent = new Intent(MainActivity.this, ActivityEmpresa.class);
                startActivity(intent);
            }
        });
    }
}
