package com.example.easytravel.Modelos;

public class Hotel {
    private String nombre;
    private String telefono;
    private String direccion;
    private String fotoBase64;

    public Hotel(String nombre, String telefono, String direccion, String fotoBase64) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fotoBase64 = fotoBase64;
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

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
