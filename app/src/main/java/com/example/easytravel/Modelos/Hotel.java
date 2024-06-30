package com.example.easytravel.Modelos;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String direccion;
    private String pais;
    private String ciudad;
    private String fotoUrl;

    public Hotel(int idHotel, String nombre, String direccion, String pais, String ciudad, String fotoUrl) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.fotoUrl = fotoUrl;
    }

    public int getIdHotel() {
        return idHotel;
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

    public String getFotoUrl() {
        return fotoUrl;
    }
}
