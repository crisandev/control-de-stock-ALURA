package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

    public int modificar( String nombre, String descripcion, Integer cantidad, Integer id ) throws SQLException {
        Connection con = ConnectionFactory.recuperaConexion();
        String sql = "UPDATE PRODUCTO SET "
                 + "NOMBRE = ?, "
                 + "DESCRIPCION = ?, "
                 + "CANTIDAD = ? "
                 + "WHERE ID = ? ";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, cantidad);
        statement.setInt(4, id);
        statement.execute();
        con.close();
        return statement.getUpdateCount();
    }

    public int eliminar( Integer id ) throws SQLException {
        Connection con = ConnectionFactory.recuperaConexion();
        PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
        statement.setInt(1, id);
        statement.execute();
        con.close();
        return statement.getUpdateCount();
    }

    public List<Map<String, String>> listar() throws SQLException {

        Connection con = ConnectionFactory.recuperaConexion();
        PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        List<Map<String, String>> resultado = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> fila = new HashMap<>();
            fila.put("ID", String.valueOf(resultSet.getInt("ID")));
            fila.put("NOMBRE", resultSet.getString("NOMBRE"));
            fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
            fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
            resultado.add(fila);
        }

        con.close();
        return resultado;
    }

    public void guardar( Map<String, String> producto ) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();
        final String nombre = producto.get("NOMBRE");
        final String descripcion = producto.get("DESCRIPCION");
        Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
        Integer maximoCantidad = 50;
        String sql = "INSERT INTO PRODUCTO(nombre, descripcion,cantidad) VALUES (?,?,?)";
        con.setAutoCommit(false);
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        try {
            do {
                int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
                ejecutaRegistro(statement, descripcion, nombre, cantidadParaGuardar);
                cantidad -= maximoCantidad;
            } while (cantidad > 0);
            con.commit();
        } catch (Exception e) {
            con.rollback();
        }
        statement.close();
        con.close();
    }

    private void ejecutaRegistro( PreparedStatement statement, final String descripcion, final String nombre, final Integer cantidad ) throws SQLException {
        statement.setString(1, descripcion);
        statement.setString(2, nombre);
        statement.setInt(3, cantidad);

        statement.execute();

        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            System.out.println(String.format("Fue insertado el producto de ID %d", resultSet.getInt(1)));
        }
    }

}