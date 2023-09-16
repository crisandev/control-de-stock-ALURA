/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author scris
 */
public class ProductoDAO {

    final private Connection con;

    public ProductoDAO( Connection con ) {
        this.con = con;
    }

    public List<Producto> listar() {
        try (this.con) {
            try ( PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO")) {

                statement.execute();
                ResultSet resultSet = statement.getResultSet();

                List<Producto> resultado = new ArrayList<>();
                while (resultSet.next()) {
                    Producto fila = new Producto(resultSet.getInt("ID"),
                             resultSet.getString("NOMBRE"),
                             resultSet.getString("DESCRIPCION"),
                             resultSet.getInt("CANTIDAD"));
                    resultado.add(fila);
                }
                return resultado;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void guardar( Producto producto ) {
        String sql = "INSERT INTO PRODUCTO(nombre, descripcion,cantidad) VALUES (?,?,?)";
        try (con) {
            con.setAutoCommit(false);
            try ( PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ejecutaRegistro(statement, producto);
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void ejecutaRegistro( PreparedStatement statement, Producto producto ) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.execute();

        try ( ResultSet resultSet = statement.getGeneratedKeys()) {
            while (resultSet.next()) {
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s", producto));
            }
        }
    }

}
