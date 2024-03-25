package com.example.easytravel.Actividades.Empresa;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;


import java.util.HashMap;
import java.util.Map;

public class ActivityRegistroEmpresa extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoContraseña;
    private EditText campoCorreo;
    private AutoCompleteTextView campoPais;
    private EditText campoTelefono;
    private EditText campoDireccion;
    private Button botonRegistrarse;
    private boolean contraseñaVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_registro);


    }
}
