package com.example.easytravel.Modelos;


public class Restaurante {
    private int id;
    private String nombre;
    private String direccion;
    private String pais;
    private String ciudad;
    private String telefono;

    public void setId(int id) {
        this.id = id;
    }

    private String fotoUrl;

    public Restaurante(int id, String nombre, String direccion, String pais, String ciudad, String telefono, String fotoUrl) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
}
