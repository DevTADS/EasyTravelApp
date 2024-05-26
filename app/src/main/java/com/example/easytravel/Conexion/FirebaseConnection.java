package com.example.easytravel.Conexion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.easytravel.Actividades.Usuario.LoginUsuario;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConnection {
    private FirebaseAuth mAuth;

    public FirebaseConnection() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password, final Context context) {
        if (password.length() < 6) {
            Toast.makeText(context, "Debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso, redirigir a la pantalla de inicio de sesión
                        Toast.makeText(context, "Registro en Firebase exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginUsuario.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        // Error en el registro
                        Toast.makeText(context, "Error en Firebase: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
