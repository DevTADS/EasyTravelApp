package com.example.easytravel.Util;

public class Apis {

    public static final String URL_001 = "http://192.168.1.3:8080/Usuario/";

    public static UsuarioService getUsuarioService(){
        return  Cliente.getClient(URL_001).create(UsuarioService.class);
    }
}
