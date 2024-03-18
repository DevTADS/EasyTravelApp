package com.example.easytravel.Utilidades;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirestoreHelper {

    private FirebaseFirestore db; // Instancia de FirebaseFirestore para interactuar con Firestore

    // Constructor de la clase FirestoreHelper
    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance(); // Inicialización de la instancia de FirebaseFirestore
    }

    // Método para agregar una empresa a Firestore
    public void addEmpresa(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(listener);
    }

    // Método para agregar un usuario a Firestore
    public void addUser(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(listener);
    }

    // Método para obtener todos los usuarios de Firestore
    public void getAllUsers(String nombreColeccion, final OnCompleteListener<QuerySnapshot> listener) {
        db.collection(nombreColeccion)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> usuarios = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                usuarios.add(document.getString("correo")); // Cambia "correo" por el nombre del campo que contiene el correo electrónico del usuario
                            }
                            listener.onComplete(task); // Pasar el task completo como éxito
                        } else {
                            listener.onComplete(task); // Pasar el task completo como error
                        }
                    }
                });
    }

}
