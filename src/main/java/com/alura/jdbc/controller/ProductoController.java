package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.modelo.Categoria;
import java.util.List;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }

    public int modificar( String nombre, String descripcion, Integer cantidad, Integer id ) {
        return this.productoDAO.modificar(nombre, descripcion, cantidad, id);
    }

    public int eliminar( Integer id ) {
        return this.productoDAO.eliminar(id);
    }

    public List<Producto> listar() {
        return productoDAO.listar();

    }

    public void guardar( Producto producto, Integer categoriaId ) {
        producto.setCategoriaId(categoriaId);
        this.productoDAO.guardar(producto);
    }
    
    public List<Producto> listar(Categoria categoria){
        return productoDAO.listar(categoria.getId());
    }
}
