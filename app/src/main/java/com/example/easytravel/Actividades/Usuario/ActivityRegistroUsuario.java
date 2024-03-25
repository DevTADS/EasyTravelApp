package com.example.easytravel.Actividades.Usuario;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.BaseDeDatos.ConexionMySQL;

import com.example.easytravel.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActivityRegistroUsuario extends AppCompatActivity {
    private Context context;
    private EditText campoNombre, campoCedula, campoTelefono, campoPais, campoCiudad,
            campoDireccion, campoCorreo, campoContraseña;

    private Button btnRegistrarse, btnVerificarConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registro);

        campoNombre = findViewById(R.id.etNombre);
        campoCedula = findViewById(R.id.etCedula);
        campoTelefono = findViewById(R.id.etTelefono);
        campoPais = findViewById(R.id.etPais);
        campoCiudad = findViewById(R.id.etCiudad);
        campoDireccion = findViewById(R.id.etDireccion);
        campoCorreo = findViewById(R.id.etCorreo);
        campoContraseña = findViewById(R.id.etPassword);
        btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnVerificarConexion = findViewById(R.id.btn_verificar_conexion);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String nombre = campoNombre.getText().toString();
                String cedula = campoCedula.getText().toString();
                String telefono = campoTelefono.getText().toString();
                String pais = campoPais.getText().toString();
                String ciudad = campoCiudad.getText().toString();
                String direccion = campoDireccion.getText().toString();
                String correo = campoCorreo.getText().toString();
                String contraseña = campoContraseña.getText().toString();

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = ConexionMySQL.obtenerConexion();

                    if (connection != null) {
                        String sql = "INSERT INTO usuarios (nombre, cedula_identidad, telefono, pais, ciudad, direccion, correo, contrasena) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, nombre);
                        preparedStatement.setString(2, cedula);
                        preparedStatement.setString(3, telefono);
                        preparedStatement.setString(4, pais);
                        preparedStatement.setString(5, ciudad);
                        preparedStatement.setString(6, direccion);
                        preparedStatement.setString(7, correo);
                        preparedStatement.setString(8, contraseña);

                        int filasAfectadas = preparedStatement.executeUpdate();

                        if (filasAfectadas > 0) {
                            Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


}
