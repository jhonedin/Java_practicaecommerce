/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import persistencia.CompraJpaController;

/**
 *
 * @author Jhon
 */
public class CompraLogica {

    CompraJpaController compraDAO;
    
    public CompraLogica(){
        compraDAO = new CompraJpaController();
    }
    
    public List<Compra> listarCompra(){
        return compraDAO.findCompraEntities();
    }
    
    public List<Compra> listaComprasDeUsuario(int idUsuario){
        List<Compra> listaCompras = listarCompra();
        List<Compra> respuesta = new ArrayList<Compra>(); 
        int idUsurCompraListas; 
        for(int i=0;i<listaCompras.size();i++){
            idUsurCompraListas = listaCompras.get(i).getUsuarioID().getUsuarioID();
            if(idUsuario==idUsurCompraListas){
                respuesta.add(listaCompras.get(i));
                //System.out.println(listaCompras.get(i));
            }
        }
        return respuesta;
    }    

    
}
