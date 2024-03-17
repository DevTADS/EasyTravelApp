package com.example.easytravel.Utilidades;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreHelper {

    private FirebaseFirestore db; // Instancia de FirebaseFirestore para interactuar con Firestore

    // Constructor de la clase FirestoreHelper
    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance(); // Inicialización de la instancia de FirebaseFirestore
    }

    // Método para agregar una empresa a Firestore

    public void addEmpresa(String nombreColeccion, Map<String, Object> datosEmpresa, OnCompleteListener<DocumentReference> listener) {
        db.collection(nombreColeccion)
                .add(datosEmpresa)
                .addOnCompleteListener(listener);
    }

    // Método para agregar un usuario a Firestore
    public void addUser(String collectionPath, Map<String, Object> data, final OnCompleteListener<DocumentReference> listener) {
        db.collection(collectionPath)
                .add(data) // Agregar los datos proporcionados al documento en la colección especificada
                .addOnCompleteListener(listener); // Añadir un listener para ser notificado cuando se complete la operación
    }
}
