/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Producto;
import persistencia.ProductoJpaController;

/**
 *
 * @author Jhon
 */
public class ProductoLogica {

    ProductoJpaController productoDAO;
    
    public ProductoLogica(){
        productoDAO = new ProductoJpaController();
    }
    


    public List<Producto> listarProductos(){
        return productoDAO.findProductoEntities();
    }    
    
}
