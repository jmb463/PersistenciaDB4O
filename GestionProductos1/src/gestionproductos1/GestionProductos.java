/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;

import com.db4o.*;
import java.util.List;

public class GestionProductos {

    int hola;
    
    public void abrirAlmacen(){
        
    }
    
    public void cerrarAlmacen(){
        
    }
    
    public Almacen obtenerObjeto(Almacen almacen){
        return null;
    }
    
    public Articulo obtenerObjeto(Articulo articulo){
        return null;
    }
    
    public Producto obtenerObjeto(Producto producto){
        return null;
    }
    
    public UnidadDeMedida obtenerObjeto(UnidadDeMedida medida){
        return null;
    }
    
    public List<Almacen> obtenerAlmacenes(){
        return null;
    }
    
    public List<Articulo> obtenerArticulos(){
        return null;
    }
    
    public List<Producto> obtenerProductos(){
        return null;
    }
    
    public List<UnidadDeMedida> obtenerUnidadesDeMedida(){
        return null;
    }
    
    public Producto obtenerProductosDeUnArticulo(){
        return null;
    }
    
    public Producto obtenerProductosLimitados(){
        return null; 
    }
    
    public Producto obtenerProductosDeUnAlmacen(){
        return null;
    }
    
    public Object obtenerObjetoClave(){
        //Que si no existe ese patron lo almacena en la base de datos y si existe
        return null;
    }
    
    public List<Object> obtenerObjetoLista(int hola){
        return null;
    }
    
    public static void main(String[] args) {
        String dataBase = null;
        ObjectContainer oc = Db4o.openFile(dataBase);
        GestionProductos gp = new GestionProductos();
        gp.abrirAlmacen();
        //Queries
        gp.cerrarAlmacen();
    }
}
