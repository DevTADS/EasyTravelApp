package com.example.easytravel.Modelos;

public class Empresa {
    private String nombre;
    private String correo;
    private String pais;
    private String telefono;
    private String direccion;
    private String password;

    // Constructor de la clase Empresa
    public Empresa(String nombre, String pais, String telefono, String direccion, String correo, String password) {
        this.nombre = nombre;
        this.pais = pais;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.password = password;
    }

    // Métodos getters y setters para acceder y modificar los campos de la empresa

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
