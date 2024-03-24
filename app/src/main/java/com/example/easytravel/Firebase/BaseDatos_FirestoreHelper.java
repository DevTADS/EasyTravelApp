package com.example.easytravel.Firebase;

import androidx.annotation.NonNull;

import com.example.easytravel.Modelos.Empresa;
import com.example.easytravel.Modelos.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
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

    // Método para agregar un empresa a Firestore
    public void addEmpresa(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        // Agregar empresa en la colección especificada
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        // Llama al onComplete del listener con el resultado de la tarea
                        listener.onComplete(task);
                    }
                });
    }

    // Método para agregar un hotel a Firestore
    public void addHotel(String nombreColeccion, Map<String, Object> datos, final OnCompleteListener<DocumentReference> listener) {
        // Agregar hotel en la colección especificada
        db.collection(nombreColeccion)
                .add(datos)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        // Llama al onComplete del listener con el resultado de la tarea
                        listener.onComplete(task);
                    }
                });
    }

    // Método para obtener todas las empresas
    public interface OnEmpresaFetchComplete {
        void onFetchComplete(List<Empresa> empresas);

        void onError(String errorMessage);
    }

    public void obtenerEmpresas(final OnEmpresaFetchComplete callback) {
        db.collection("empresa") // Reemplaza "empresas" con el nombre de tu colección de empresas
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Empresa> empresas = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convertir cada documento a objeto Empresa
                                Empresa empresa = document.toObject(Empresa.class);
                                empresas.add(empresa);
                            }
                            // Llamar al método onFetchComplete con la lista de empresas
                            callback.onFetchComplete(empresas);
                        } else {
                            // Llamar al método onError si hay algún error
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });
    }
    // Interfaz para manejar la devolución de llamada al completar la obtención de hoteles por empresa
    public interface OnHotelFetchComplete {
        void onFetchComplete(List<Hotel> hoteles);
        void onError(String errorMessage);
    }


    public void obtenerHotelesPorEmpresa(String empresaId, final OnHotelFetchComplete callback) {
        db.collection("hoteles")
                .whereEqualTo("empresaId", empresaId)  // Filtrar hoteles por ID de empresa
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Hotel> hoteles = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convertir cada documento a objeto Hotel
                                Hotel hotel = document.toObject(Hotel.class);
                                hoteles.add(hotel);
                            }
                            // Llamar al método onFetchComplete con la lista de hoteles
                            callback.onFetchComplete(hoteles);
                        } else {
                            // Llamar al método onError si hay algún error
                            callback.onError(task.getException().getMessage());
                        }
                    }
                });
    }

}
