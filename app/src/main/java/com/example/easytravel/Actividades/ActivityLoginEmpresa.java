package com.example.easytravel.Actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLoginEmpresa extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private Button registerButton;
    private boolean passwordVisible = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empresa);


        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar vistas
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_registro);

        // Configurando OnClickListener para el botón de login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores del formulario
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Iniciar sesión con el correo electrónico y la contraseña
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String email = emailEditText.getText().toString();
                                    String password = passwordEditText.getText().toString();
                                    // Verificar si el usuario es administrador
                                    if (email.equals("easytraveltads@gmail.com") && password.equals("tads2024")) {
                                        // Iniciar la actividad de administrador
                                        startActivity(new Intent(com.example.easytravel.Actividades.ActivityLoginEmpresa.this, ActivityAdmin.class));
                                    } else {
                                        // Inicio de sesión exitoso, abrir la nueva actividad de usuario
                                        startActivity(new Intent(com.example.easytravel.Actividades.ActivityLoginEmpresa.this, ActivityHomeEmpresa.class));
                                    }
                                } else {
                                    // Error al iniciar sesión, mostrar un mensaje de error
                                    Toast.makeText(com.example.easytravel.Actividades.ActivityLoginEmpresa.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Configurando OnClickListener para el botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro al hacer clic en el botón de registro
                startActivity(new Intent(com.example.easytravel.Actividades.ActivityLoginEmpresa.this, ActivityRegistroEmpresa.class));
            }
        });

        // Configurando OnClickListener para el ícono de la contraseña
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_icon, 0);
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Cambiar a contraseña oculta
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordVisible = false;
        } else {
            // Cambiar a texto visible
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordVisible = true;
        }
        // Mover el cursor al final del texto
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}
