/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author scris
 */
public class ConnectionFactory {

    public static Connection recuperaConexion() throws SQLException {
        String connectionString = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC";
        String user = "root";
        String pass = "MySQL@2023";
        return DriverManager.getConnection(connectionString, user, pass);
    }

}
