package com.example.easytravel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ActivityRegistro extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText correoEditText;
    private Button registrarseButton;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Inicializar vistas
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        correoEditText = findViewById(R.id.correo);
        registrarseButton = findViewById(R.id.btn_registrarse);

        // Configurar OnClickListener para el botón de registro
        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = usernameEditText.getText().toString();
                final String correo = correoEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                if (user.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    validacion();
                } else {
                    // Crear un nuevo objeto Usuario
                    Usuario p = new Usuario();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(user);
                    p.setCorreo(correo);
                    p.setPassword(password);

                    // Agregar el usuario a Firestore
                    db.collection("usuarios")
                            .document(p.getUid())
                            .set(p)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ActivityRegistro.this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                                    limpiarCampos();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ActivityRegistro.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            private void limpiarCampos() {
                usernameEditText.setText("");
                correoEditText.setText("");
                passwordEditText.setText("");
            }

            private void validacion() {
                if (usernameEditText.getText().toString().isEmpty()) {
                    usernameEditText.setError("Requerido");
                }
                if (correoEditText.getText().toString().isEmpty()) {
                    correoEditText.setError("Requerido");
                }
                if (passwordEditText.getText().toString().isEmpty()) {
                    passwordEditText.setError("Requerido");
                }
            }
        });
    }
}
