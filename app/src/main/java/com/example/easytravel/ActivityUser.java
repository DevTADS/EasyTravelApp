package com.example.easytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityUser extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Inicializar vistas
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_registro);

        // Configurar OnClickListener para el botón de login
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
                                    // Inicio de sesión exitoso, abrir la nueva actividad
                                    startActivity(new Intent(ActivityUser.this, NuevaActividad.class));
                                } else {
                                    // Error al iniciar sesión, mostrar un mensaje de error
                                    Toast.makeText(ActivityUser.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro al hacer clic en el botón de registro
                startActivity(new Intent(ActivityUser.this, ActivityRegistro.class));
            }
        });
    }
}
