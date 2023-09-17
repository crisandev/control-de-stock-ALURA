/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scris
 */
public class CategoriaDAO {

    private Connection con;

    public CategoriaDAO( Connection con ) {
        this.con = con;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORIA";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Categoria categoria = new Categoria(resultSet.getInt("ID"), resultSet.getString("NOMBRE"));
                    resultado.add(categoria);
                }
            }
            return resultado;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();
        String sql = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD "
                 + "FROM CATEGORIA C "
                 + "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {

            try ( ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Integer categoriaId = resultSet.getInt("ID");
                    String categoriaNombre = resultSet.getString("NOMBRE");

                    Categoria categoria = resultado
                             .stream()
                             .filter(cat -> cat.getId().equals(categoriaId))
                             .findAny().orElseGet(() -> {
                                 Categoria cat = new Categoria(categoriaId, categoriaNombre);
                                 resultado.add(cat);
                                 return cat;
                             });
                    Producto producto = new Producto(resultSet.getInt("P.ID"),
                             resultSet.getString("P.NOMBRE"),
                             resultSet.getInt("P.CANTIDAD"));
                    
                    categoria.agregar(producto);
                }
            }
            return resultado;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
