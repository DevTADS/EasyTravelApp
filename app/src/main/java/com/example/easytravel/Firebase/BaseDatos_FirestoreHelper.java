package com.example.easytravel.Firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class BaseDatos_FirestoreHelper {

    private final FirebaseFirestore db; // Instancia de FirebaseFirestore para interactuar con Firestore

    // Constructor de la clase FirestoreHelper
    public BaseDatos_FirestoreHelper() {
        db = FirebaseFirestore.getInstance(); // Inicialización de la instancia de FirebaseFirestore
    }

    // Método para agregar un usuario a Firestore
    public void addUser(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(listener);
    }

    public void addEmpresa(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        // Agregar empresa en la colección especificada
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Obtener el ID generado automáticamente
                            String idEmpresa = task.getResult().getId();
                            // Guardar la empresa también en la colección "001" con ID manual
                            db.collection("001")
                                    .document(idEmpresa)
                                    .set(datos)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                listener.onComplete(task); // Completa la tarea con éxito
                                            } else {
                                                listener.onComplete(task); // Completa la tarea con error
                                            }
                                        }
                                    });
                        } else {
                            // Si ocurre un error al agregar la empresa en la colección especificada
                            listener.onComplete(task);
                        }
                    }
                });


    }
}
