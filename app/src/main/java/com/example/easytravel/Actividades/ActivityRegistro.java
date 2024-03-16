package com.example.easytravel.Actividades;

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
import com.example.easytravel.Utilidades.FirebaseAuthHelper;
import com.example.easytravel.Utilidades.FirestoreHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistro extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoContraseña;
    private EditText campoCorreo;
    private Button botonRegistrarse;
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    private boolean contraseñaVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar FirebaseAuthHelper y FirestoreHelper
        authHelper = new FirebaseAuthHelper();
        firestoreHelper = new FirestoreHelper();

        // Inicializar vistas
        campoUsuario = findViewById(R.id.username);
        campoContraseña = findViewById(R.id.password);
        campoCorreo = findViewById(R.id.correo);
        botonRegistrarse = findViewById(R.id.btn_registrarse);

        // Configurar OnClickListener para el botón de registro
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usuario = campoUsuario.getText().toString();
                final String correo = campoCorreo.getText().toString();
                final String contraseña = campoContraseña.getText().toString();

                if (usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                    validacion();
                } else {
                    // Crear usuario en Firebase Authentication
                    authHelper.crearUsuarioConCorreoYContraseña(correo, contraseña, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Usuario creado exitosamente
                                // Guardar usuario en Firestore
                                Map<String, Object> datosUsuario = new HashMap<>();
                                datosUsuario.put("nombre", usuario);
                                datosUsuario.put("correo", correo);
                                guardarUsuarioEnFirestore(datosUsuario);
                            } else {
                                // Error al crear usuario
                                Toast.makeText(ActivityRegistro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // Configurar OnClickListener para el ícono de la contraseña
        campoContraseña.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.password_icon, 0);
        campoContraseña.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (campoContraseña.getRight() - campoContraseña.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        cambiarVisibilidadContraseña();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    // Método para cambiar la visibilidad de la contraseña
    private void cambiarVisibilidadContraseña() {
        if (contraseñaVisible) {
            // Cambiar a contraseña oculta
            campoContraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            contraseñaVisible = false;
        } else {
            // Cambiar a texto visible
            campoContraseña.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            contraseñaVisible = true;
        }
        // Mover el cursor al final del texto
        campoContraseña.setSelection(campoContraseña.getText().length());
    }

    // Método para guardar usuario en Firestore
    private void guardarUsuarioEnFirestore(Map<String, Object> datosUsuario) {
        firestoreHelper.addUser("usuarios", datosUsuario, new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    // Usuario guardado en Firestore correctamente
                    Toast.makeText(ActivityRegistro.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    // Error al guardar usuario en Firestore
                    Toast.makeText(ActivityRegistro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para limpiar campos de texto
    private void limpiarCampos() {
        campoUsuario.setText("");
        campoCorreo.setText("");
        campoContraseña.setText("");
    }

    // Método para validar campos de texto
    private void validacion() {
        if (campoUsuario.getText().toString().isEmpty()) {
            campoUsuario.setError("Requerido");
        }
        if (campoCorreo.getText().toString().isEmpty()) {
            campoCorreo.setError("Requerido");
        }
        if (campoContraseña.getText().toString().isEmpty()) {
            campoContraseña.setError("Requerido");
        }
    }
}
