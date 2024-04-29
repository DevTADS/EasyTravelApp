package com.example.easytravel.Modelos;

public class Hotel {
    private String nombre;
    private String pais;
    private String ciudad;
    private String telefono;
    private String direccion;
    private String id_empresa;
    private String foto; // Nuevo atributo para la foto del hotel

    public Hotel() {

    }

    public Hotel(String nombre, String pais, String ciudad, String telefono, String direccion, String id_empresa, String foto) {
        this.nombre = nombre;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.direccion = direccion;
        this.id_empresa = id_empresa;
        this.foto = foto;
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

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nPaís: " + pais + "\nCiudad: " + ciudad + "\nTeléfono: " + telefono + "\nDirección: " + direccion;
    }
}
