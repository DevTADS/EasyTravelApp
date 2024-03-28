package com.example.easytravel.Utils;

public class Apis {

    public static final String URL_001="database-2.c9qw4koielmq.us-east-2.rds.amazonaws.com:8080/personas/";

    public static PersonaService getPersonaService(){
        return  Cliente.getClient(URL_001).create(PersonaService.class);
    }
}
