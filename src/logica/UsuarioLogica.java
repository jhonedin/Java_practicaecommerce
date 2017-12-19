/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Usuario;
import persistencia.UsuarioJpaController;

/**
 *
 * @author Jhon
 */
public class UsuarioLogica {

    UsuarioJpaController usuarioDAO;
    
    public UsuarioLogica(){
        usuarioDAO = new UsuarioJpaController();
    }
    
    public List<Usuario> UsuariosList(){
        return usuarioDAO.findUsuarioEntities();
    }
    
    public Usuario buscarUsuario(String id, String pass)throws Exception{
        try{
            id.charAt(0);
            
        }
        catch(StringIndexOutOfBoundsException e){
            throw new Exception("El campo usuario no debe estar vacío");
        }
        try{
            pass.charAt(0);
        }
        catch(StringIndexOutOfBoundsException e){
            throw new Exception("El campo contraseña no debe estar vacío");
        }
        
        List<Usuario> usuarios = usuarioDAO.findUsuarioEntities();
        for (int i = 0; i < usuarios.size(); i++) {
            //System.out.println(usuarios.get(i).getUsuarioID()+" "+usuarios.get(i).getNombreUsuario()+" "+usuarios.get(i).getPassword());
            if(usuarios.get(i).getUsuarioID().toString().equals(id) &&
                    usuarios.get(i).getPassword().equals(pass)){
                return usuarios.get(i);
            }
        }
        
        return null;
    }    
    
}
