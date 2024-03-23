package com.example.easytravel.Firebase;

import com.google.android.gms.tasks.OnCompleteListener;
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

    // Método para obtener todas las empresas de Firestore
    public void getAllEmpresas(String empresas, final OnCompleteListener<QuerySnapshot> listener) {
        db.collection(empresas)
                .get()
                .addOnCompleteListener(listener);
    }

    // Método para obtener todos los usuarios de Firestore
    public void getAllUsers(String usuarios, final OnCompleteListener<QuerySnapshot> listener) {
        db.collection(usuarios)
                .get()
                .addOnCompleteListener(listener);
    }

    // Modificar el método addHotel en la clase BaseDatos_FirestoreHelper para agregar un hotel a la subcolección de una empresa
    public void addHotel(String idEmpresa, Map<String, Object> datosHotel, final OnCompleteListener<DocumentReference> listener) {
        db.collection("empresas").document(idEmpresa).collection("hoteles")
                .add(datosHotel)
                .addOnCompleteListener(listener);
    }

    // Modificar el método obtenerHotelesPorEmpresa para recuperar los hoteles de la subcolección de una empresa
    public void obtenerHotelesPorEmpresa(String idEmpresa, OnCompleteListener<QuerySnapshot> listener) {
        db.collection("empresas").document(idEmpresa).collection("hoteles")
                .get()
                .addOnCompleteListener(listener);
    }



    // Método para obtener el ID de la empresa por su nombre
    public void obtenerIdEmpresaPorNombre(String nombreColeccion, String nombreEmpresa, OnCompleteListener<QuerySnapshot> listener) {
        db.collection(nombreColeccion)
                .whereEqualTo("nombre", nombreEmpresa)
                .get()
                .addOnCompleteListener(listener);
    }
}
