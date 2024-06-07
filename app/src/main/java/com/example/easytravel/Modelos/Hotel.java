package com.example.easytravel.Modelos;

import android.graphics.Bitmap;

public class Hotel {
    private String nombre;
    private String telefono;
    private String direccion;
    private Bitmap foto;

    public Hotel(String nombre, String telefono, String direccion, Bitmap foto) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.foto = foto;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}
