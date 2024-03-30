package com.example.easytravel.Utils;

public class Apis {

    public static final String URL_001 = "http://192.168.1.10:8080/usuarios/";

    public static UsuarioService getUsuarioService(){
        return  Cliente.getClient(URL_001).create(UsuarioService.class);
    }
}
