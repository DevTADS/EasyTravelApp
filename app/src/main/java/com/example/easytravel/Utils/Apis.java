package com.example.easytravel.Utils;

public class Apis {

    public static final String URL_001 = "http://192.168.31.201:8080/personas/";

    public static PersonaService getPersonaService(){
        return  Cliente.getClient(URL_001).create(PersonaService.class);
    }
}
