/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Califica;
import modelo.Producto;
import modelo.Usuario;
import persistencia.CalificaJpaController;

/**
 *
 * @author Jhon
 */
public class CalificaLogica {

    CalificaJpaController calificaDAO;
    
    public CalificaLogica(){
       calificaDAO = new CalificaJpaController();
    }   
    
    public int getCalUserProducto(Usuario user, Producto prod){
        int cal=0;
        return cal;
    }
    
    public List<Califica> CalificacionesList(){
        return calificaDAO.findCalificaEntities();
    }
}
