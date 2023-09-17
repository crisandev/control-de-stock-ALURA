/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void guardar( Producto producto ) {
        String sql = "INSERT INTO PRODUCTO(nombre, descripcion,cantidad, categoria_id) VALUES (?,?,?,?)";
        try ( PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);
            ejecutaRegistro(statement, producto);
            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int modificar( String nombre, String descripcion, Integer cantidad, Integer id ) {
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
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int eliminar( Integer id ) {
        try ( PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?")) {
            statement.setInt(1, id);
            statement.execute();
            return statement.getUpdateCount();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void ejecutaRegistro( PreparedStatement statement, Producto producto ) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getCategoriaId());
        statement.execute();

        try ( ResultSet resultSet = statement.getGeneratedKeys()) {
            while (resultSet.next()) {
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s", producto));
            }
        }
    }

    public List<Producto> listar( Integer categoriaId ) {
        String sql =  "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO WHERE categoria_id = ?";
         try ( PreparedStatement statement = con.prepareStatement(sql)) {

             statement.setInt(1, categoriaId);
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
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
