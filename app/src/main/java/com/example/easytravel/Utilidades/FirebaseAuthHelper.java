package com.example.easytravel.Utilidades;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHelper {

    private FirebaseAuth mAuth; // Instancia de FirebaseAuth para interactuar con la autenticación de Firebase

    // Constructor de la clase FirebaseAuthHelper
    public FirebaseAuthHelper() {
        mAuth = FirebaseAuth.getInstance(); // Inicialización de la instancia de FirebaseAuth
    }

    // Método para crear un usuario con correo electrónico y contraseña
    public void crearUsuarioConCorreoYContraseña(String email, String contraseña, final OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(listener); // Añadir un listener para ser notificado cuando se complete la operación
    }

    // Método para iniciar sesión con correo electrónico y contraseña
    public void iniciarSesionConCorreoYContraseña(String email, String contraseña, final OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(listener); // Añadir un listener para ser notificado cuando se complete la operación
    }

    // Método para cerrar sesión del usuario actual
    public void cerrarSesion() {
        mAuth.signOut(); // Cerrar sesión del usuario actual
    }

    // Método para obtener el usuario actualmente autenticado
    public FirebaseUser obtenerUsuarioActual() {
        return mAuth.getCurrentUser(); // Obtener el usuario actualmente autenticado
    }
}
