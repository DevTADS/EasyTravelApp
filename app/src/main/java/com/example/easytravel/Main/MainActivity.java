package com.example.easytravel.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_identificacion = findViewById(R.id.btn_identificacion);
        btn_identificacion.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ActivityInicioSesion.class);
            startActivity(intent);


        });
    }
}
