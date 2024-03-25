package com.example.easytravel.BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexionMySQL {


    private static final String URL = "jdbc:mysql://viaduct.proxy.rlwy.net:40716/railway?autoReconnect=true&useSSL=false";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "mrIdoXaUxTYnRaCLxCwNEHfrGrZQOCOr";


    public static Connection obtenerConexion() throws SQLException {
        Connection conexion = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conexion;
    }



}
