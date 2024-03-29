package com.example.easytravel.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("cedula")
    @Expose
    private String cedula;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("pais")
    @Expose
    private String pais;

    @SerializedName("ciudad")
    @Expose
    private String ciudad;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("password")
    @Expose
    private String password;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String cedula, String telefono, String pais, String ciudad, String direccion, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.correo = correo;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
