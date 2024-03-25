package com.example.easytravel.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.easytravel.BaseDeDatos.ConexionMySQL;
import com.example.easytravel.R;

import java.sql.Connection;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validarConexion();

        Button btn_identificacion = findViewById(R.id.btn_identificacion);
        btn_identificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityInicioSesion.class);
                startActivity(intent);
            }
        });
    }

    private void validarConexion() {
        try {
            Connection conexion = ConexionMySQL.obtenerConexion();
            if (conexion != null && !conexion.isClosed()) {
                // La conexión se estableció con éxito
                Toast.makeText(this, "Conexión exitosa a la base de datos", Toast.LENGTH_SHORT).show();
                conexion.close(); // Cerrar la conexión después de la validación
            } else {
                // No se pudo establecer la conexión
                Toast.makeText(this, "No se pudo establecer la conexión a la base de datos", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar cualquier excepción que pueda ocurrir al intentar establecer la conexión
            Toast.makeText(this, "Error al intentar conectar a la base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
