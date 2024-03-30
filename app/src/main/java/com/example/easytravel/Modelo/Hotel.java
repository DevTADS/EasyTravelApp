package com.example.easytravel.Modelo;

public class Hotel {
    private String nombre;
    private String pais;
    private String ciudad;
    private String telefono;
    private String direccion;

    public Hotel() {
        // Constructor vacío requerido por Firebase
    }

    public Hotel(String nombre, String pais, String ciudad, String telefono, String direccion) {
        this.nombre = nombre;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nPaís: " + pais + "\nCiudad: " + ciudad + "\nTeléfono: " + telefono + "\nDirección: " + direccion;
    }
}
