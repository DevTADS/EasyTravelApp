package com.example.easytravel.Modelos;

public class Empresa {
    private String nombre;
    private String correo;
    private String pais;
    private String telefono;
    private String direccion;
    private String password;
    private String id_empresa;

    public Empresa() {

    }

    public Empresa(String nombre, String correo, String pais, String telefono, String direccion, String password, String id_empresa) {
        this.nombre = nombre;
        this.correo = correo;
        this.pais = pais;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
        this.id_empresa = id_empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
