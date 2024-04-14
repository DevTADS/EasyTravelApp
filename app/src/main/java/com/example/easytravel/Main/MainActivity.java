package com.example.easytravel.Main;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Model.Usuario;
import com.example.easytravel.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_identificacion = findViewById(R.id.btn_identificacion);
        btn_identificacion.setOnClickListener(v -> {
            // Intent para iniciar la actividad ActivityInicioSesion
            Intent intent = new Intent(MainActivity.this, ActivityInicioSesion.class);
            startActivity(intent);


        });
    }

    // Método para verificar la conexión con el servidor

}
