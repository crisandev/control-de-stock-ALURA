/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scris
 */
public class Categoria {

    private Integer id;
    private String nombre;
    private List<Producto> productos;

    public Categoria( Integer id, String nombre ) {
        this.id = id;
        this.nombre = nombre;
    }

    public void agregar( Producto producto ) {
        if (this.productos == null) {
            this.productos = new ArrayList<>();
        }

        this.productos.add(producto);
    }

    public Integer getId() {
        return id;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
