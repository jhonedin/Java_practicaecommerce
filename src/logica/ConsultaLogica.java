
package logica;

import java.util.ArrayList;
import java.util.List;
import modelo.Consulta;
import persistencia.ConsultaJpaController;

/**
 *
 * @author Jhon
 */
public class ConsultaLogica {

    ConsultaJpaController consultaDAO;
    
    public ConsultaLogica(){
        consultaDAO = new ConsultaJpaController();
    }
    
    public List<Consulta> listaConsultasDeUsuario(int idUsuario){
        List<Consulta> listaConsultas = listarConsultas();
        List<Consulta> respuesta = new ArrayList<Consulta>(); 
        int idUsuarioListas; 
        for(int i=0;i<listaConsultas.size();i++){
            idUsuarioListas = listaConsultas.get(i).getUsuarioID().getUsuarioID();
            if(idUsuario==idUsuarioListas){
                respuesta.add(listaConsultas.get(i));
                System.out.println(listaConsultas.get(i));
            }
        }
        return respuesta;
    }
    
    public List<Consulta> listarConsultas(){
        return consultaDAO.findConsultaEntities();
    }
}
