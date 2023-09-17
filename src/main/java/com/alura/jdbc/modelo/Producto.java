/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.modelo;

/**
 *
 * @author scris
 */
public class Producto {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Integer categoriaId;

    public Producto( String nombre, String descripcion, Integer cantidad ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public Producto( int id, String nombre, String descripcion, int cantidad ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public Producto( int id, String nombre, int cantidad ) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public void setCategoriaId( Integer categoriaId ) {
        this.categoriaId = categoriaId;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    
    
    public int getCategoriaId() {
        return categoriaId;
    }


    @Override
    public String toString() {
        return String.format(
                 "{id: %s, nombre: %s, descripcion: %s, cantidad: %d}",
                 this.id,
                 this.nombre,
                 this.descripcion,
                 this.cantidad);
    }

}
