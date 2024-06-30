package com.example.easytravel.Modelos;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String fotoUrl;

    public Hotel(int idHotel, String nombre, String fotoUrl) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.fotoUrl = fotoUrl;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
}
