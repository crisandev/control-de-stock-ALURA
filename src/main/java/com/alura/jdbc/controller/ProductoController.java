package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;
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

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }
    

    public int modificar( String nombre, String descripcion, Integer cantidad, Integer id ) throws SQLException {
        ConnectionFactory currentConnection = new ConnectionFactory();

        try ( Connection con = currentConnection.recuperaConexion();) {
            String sql = "UPDATE PRODUCTO SET "
                     + "NOMBRE = ?, "
                     + "DESCRIPCION = ?, "
                     + "CANTIDAD = ? "
                     + "WHERE ID = ? ";
            try ( PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();
                return statement.getUpdateCount();
            }
        }
    }

    public int eliminar( Integer id ) throws SQLException {
        ConnectionFactory currentConnection = new ConnectionFactory();

        try ( Connection con = currentConnection.recuperaConexion();) {
            try ( PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?")) {
                statement.setInt(1, id);
                statement.execute();
                return statement.getUpdateCount();
            }
        }
    }

    public List<Producto> listar()  {
        return productoDAO.listar();

    }

    public void guardar( Producto producto )  {
        ProductoDAO productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
        productoDAO.guardar(producto);
    }
}
