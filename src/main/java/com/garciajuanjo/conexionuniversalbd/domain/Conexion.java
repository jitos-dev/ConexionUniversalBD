package com.garciajuanjo.conexionuniversalbd.domain;

import java.sql.*;
public class Conexion {

    public static Connection connection = null;
    public static String name_db = null;

    /**
     * Instancia el objeto Connection con los datos que le pasamos en el constructor
     * @param name_db nombre de la base de datos
     * @param username nombre de usuario
     * @param password contraseña de usuario
     */
    public Conexion(String name_db, String username, String password){
        try {
            Conexion.name_db = name_db;
            String conexion = "jdbc:mysql://localhost:3306/";
            conexion += name_db;
            connection = DriverManager.getConnection(conexion, username, password);
        } catch (SQLException sql){
            sql.printStackTrace();
        }
    }
}
