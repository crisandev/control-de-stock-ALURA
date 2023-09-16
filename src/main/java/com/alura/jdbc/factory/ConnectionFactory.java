/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class ConnectionFactory {

    private String connectionString = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC";
    private String user = "root";
    private String pass = "MySQL@2023";
    private DataSource dataSource;

    public ConnectionFactory() {
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl(connectionString);
        pooledDataSource.setUser(user);
        pooledDataSource.setPassword(pass);
        pooledDataSource.setMaxPoolSize(10);
        this.dataSource = pooledDataSource;
    }

    public Connection recuperaConexion()  {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
