/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import modelo.Califica;
import modelo.Producto;
import modelo.Usuario;

/**
 *
 * @author Jhon
 */
public class Recomendar {

    Producto producto;
    Califica calificacion;
    Usuario usuario;
    ProductoLogica productoLogica;
    CalificaLogica calificaLogica;
    UsuarioLogica usuarioLogica;

    public Recomendar() {
        producto = new Producto();
        productoLogica = new ProductoLogica();
        calificaLogica = new CalificaLogica();
        usuarioLogica = new UsuarioLogica();
    }

    public List<Producto> generarRecomendacion(Usuario user) {
        List<Producto> recomendados = new ArrayList<Producto>();
        List<Producto> recomendadosSlopeOne = new ArrayList<Producto>();
        recomendadosSlopeOne = recomendacion_SlopeOne(user);
       
        for(int i=0;i<10;i++) // muestro el top 10 de productos
        {
           //System.out.println(i);
           recomendados.add(i, recomendadosSlopeOne.get(i));
        }
        return recomendados;
        //return recomendadosSlopeOne;
        //return recomendados = traerProductos();
    }

    public List<Producto> recomendacion_SlopeOne(Usuario user) {
        List<Producto> topProductos = new ArrayList<Producto>();
        List<Califica> calificaciones = new ArrayList<Califica>();
        List<Usuario> usuarios = new ArrayList<Usuario>();
        List<Producto> listaProductos = new ArrayList<Producto>();
        int posUserEnLista=0;
        calificaciones = traerCalificaciones();
        usuarios = traerUsuarios();
        listaProductos = traerProductos();

        float[][] matrizDeCal = matrizCalificaciones(usuarios, listaProductos, calificaciones);
        float[][] matrizDePredicciones = realizarPredicciones(matrizDeCal,usuarios,listaProductos);
        float[] arregloCalUser = new float[listaProductos.size()];
        //System.out.println(" ");
        /*for(int n=0;n<3;n++){
            for(int m=0;m<3;m++){
               System.out.print(" "+matrizDeCal[n][m]);
            }
            System.out.println(" ");
        }*/
        //Busco la posicion del usuario de interes en la lista de usuarios, para extraer 
        // de la matrizDePrediccion las calificaciones ese usuario
        for(int p=0;p<usuarios.size();p++){
            if(usuarios.get(p).getUsuarioID() == user.getUsuarioID()){
                posUserEnLista = p;
                //System.out.println("Te encontre ");
            }
            //else System.out.println("No Te encontre ");
        }
        // Paso a un arreglo las calificaciones de los productos del usuario de interes
       /* for(int q=0;q<listaProductos.size();q++){
          arregloCalUser[q] = matrizDePredicciones[posUserEnLista][q];
          System.out.print(" "+arregloCalUser[q]);
        }
        System.out.println(" ");*/
        topProductos = ordenaListaProducto(arregloCalUser,listaProductos);
        return topProductos;
    }
    
    public List<Producto> ordenaListaProducto(float[] arregloCalUser,List<Producto> listProducto){
        Producto auxProducto = new Producto();
        float aux;
        for(int i = 0; i < arregloCalUser.length; i++){
            for(int j=i+1; j < arregloCalUser.length; j++){
		if(arregloCalUser[i] < arregloCalUser[j]){
                    aux = (float) arregloCalUser[i];
                    auxProducto = listProducto.get(i);
		    arregloCalUser[i] = arregloCalUser[j];
                    listProducto.set(i, listProducto.get(j));
                    arregloCalUser[j] = aux;
                    listProducto.set(j, auxProducto);
		}
            }
	}   
        /*System.out.println("Arreglo ordenado ");
        for(int j=0;j<arregloCalUser.length;j++){
            System.out.print(" "+arregloCalUser[j]+" , ");
            
        }
        System.out.println(" ");
        for(int j=0;j<listProducto.size();j++){
            System.out.print(listProducto.get(j).getProductoID() +" - ");
        }*/
        return listProducto;
    }

    public float[][] realizarPredicciones(float[][] matrizDeCal, List<Usuario> usuarios, List<Producto> listaProductos) {
        float[][] matrizPrediccion = null;
        int auxPosUser = 0, auxPosProd = 0;
        int cantidadUser = usuarios.size();
        int cantidadProd = listaProductos.size();
        float acumSumatorias = 0, desviacion = 0, estimacion=0,prediccion=0, acumPredicciones = 0;
        float prediccionTotal=0;
        float[] desviaciones = new float[cantidadProd];
        int cantElemento = 0, acumElementos=0;
        
        for(int x=0;x<cantidadUser;x++){
            for(int y=0;y<cantidadProd;y++){
                if(matrizDeCal[x][y]<0){ //Existe una posicion donde haya calificacion? cal= - 1
                    auxPosUser = x; // toma la posicion del usuario 
                    auxPosProd = y; // toma la posicion del producto
                /////////////////////////
                // Aqui calculo desviaciones y realizo predicciones
                     for (int i = 0; i < cantidadProd; i++) {
                        if (i != auxPosProd) { // exluyo del recorrido la posicion donde esta el producto
                            for (int j = 0; j < cantidadUser; j++) {
                                 if (j != auxPosUser) {
                                     if (matrizDeCal[j][i] != -1) {
                                    //System.out.println("De la sumatoria llevo " + acumSumatorias);
                                    //System.out.println("Estoy restando " + matrizDeCal[j][auxPosProd] + " - " + matrizDeCal[j][i]);
                                    acumSumatorias = (matrizDeCal[j][auxPosProd] - matrizDeCal[j][i]) + acumSumatorias;
                                    //System.out.println("Haciendo sumatorias " + acumSumatorias);
                                     cantElemento += 1;
                                    //System.out.println("Cantidad de elementos " + cantElemento);
                                    }
                                }
                            }
                            if (cantElemento > 0) {
                            desviacion = acumSumatorias / cantElemento;
                            }
                            //System.out.println("Muestro una desviacion calculada " + desviacion);
                            desviaciones[i] = desviacion;
                            if (i != auxPosProd) estimacion = desviacion + matrizDeCal[auxPosUser][i];
                            //System.out.println("Estimacion calculada " + estimacion);
                            // calcula predicciones sobre la estimacion y la cantidad de elementos que existen de un mismo producto
                            prediccion = estimacion*cantElemento;
                            acumPredicciones = prediccion + acumPredicciones;
                        }
                        acumSumatorias = 0;
                        acumElementos = cantElemento + acumElementos;
                        //System.out.println("El acumulado de elementos "+acumElementos);
                        cantElemento = 0;
                    }
                    prediccionTotal = acumPredicciones / acumElementos;
                    //System.out.println("Prediccion Total "+prediccionTotal);
                    matrizDeCal[x][y] = prediccionTotal;
                    prediccionTotal = 0;
                }
            }
        }
        matrizPrediccion = matrizDeCal;
        return matrizPrediccion;
    }

    public float[][] matrizCalificaciones(List<Usuario> userList, List<Producto> prodList, List<Califica> calificaList) {
        float[][] matrizCalificaciones = new float[userList.size()][prodList.size()];
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < prodList.size(); j++) {
                matrizCalificaciones[i][j] = buscarCalificacion(userList.get(i).getUsuarioID(),
                        prodList.get(j).getProductoID(),
                        calificaList);
            }
        }

        /*for (int i = 0; i < userList.size(); i++) {
            System.out.print(userList.get(i).getNombreUsuario() + " ");
            for (int j = 0; j < prodList.size(); j++) {
                System.out.print(" " + matrizCalificaciones[i][j]);
            }
            System.out.println(" ");
        }*/

        return matrizCalificaciones;
    }

    public float buscarCalificacion(int idUser, int idProduct, List<Califica> calificaList) {
        float cal = 0;
        int idU, idP;
        for (int i = 0; i < calificaList.size(); i++) {
            idU = calificaList.get(i).getUsuarioID().getUsuarioID();
            idP = calificaList.get(i).getProductoID().getProductoID();
            if ((idU == idUser) && (idP == idProduct)) {
                cal = calificaList.get(i).getCalificacion();
                //System.out.println("Encontro");
                return cal;
            } else {
                cal = -1; //  si no existe calificacio de un producto se asigna -1 en la matriz de calificacion
            }
        }
        //System.out.println(cal);
        return cal;
    }

    public List<Producto> traerProductos() {
        return productoLogica.listarProductos();
    }

    public List<Califica> traerCalificaciones() {
        return calificaLogica.CalificacionesList();
    }

    public List<Usuario> traerUsuarios() {
        return usuarioLogica.UsuariosList();
    }

}
