/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;

import com.db4o.*;
import com.db4o.ext.Db4oException;
import com.db4o.query.Constraint;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionProductos {
    private static EmbeddedObjectContainer oc;
    int hola;
    
    public GestionProductos(){
        
    }
    
    public void abrirAlmacen(){
        String dataBase = "src/Datos/datos.ysp";
        try{
            this.oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dataBase);
            System.out.println("Abriendo almacén de datos...");
        }
        catch(Db4oException ex){  
            System.out.println("Base de datos no encontrada " + ex.getMessage());
        }
    }
    
    public void cerrarAlmacen(){
        this.oc.close();
        System.out.println("El almacén de datos ha sido cerrado.");
    }
    
    public void actualizarCambios(Object o){
        oc.store(o);
        oc.commit();
    }
    
    public void actualizarCambios(){
        oc.commit();
    }
    
    public void eliminarInstancias(Object o){
        try{
            this.oc.delete(o);
            System.out.println("Se ha eliminado el objeto.");
        }
        catch(Db4oException e){
            System.out.println("Error en el borrado" + e.getMessage());
            this.oc.rollback();
        }
        this.oc.commit();
    }
            
    public Almacen obtenerObjeto(Almacen almacen){
        System.out.println("Recuperando almacén...");
        return (Almacen) obtenerObjetoClave(almacen);
    }
    
    public Articulo obtenerObjeto(Articulo articulo){
        System.out.println("Recuperando artículo...");
        return (Articulo) obtenerObjetoClave(articulo);
    }
    
    public Producto obtenerObjeto(Producto producto){
        System.out.println("Recuperando producto...");
        return (Producto) obtenerObjetoClave(producto);
    }
    
    public Envase obtenerObjeto(Envase envase){
        System.out.println("Recuperando objeto...");
        return (Envase) obtenerObjetoClave(envase);
    }
    
    public UnidadDeMedida obtenerObjeto(UnidadDeMedida medida){
        System.out.println("Recuperando unidad de medida...");
        return (UnidadDeMedida) obtenerObjetoClave(medida);
    }
    
    public List<Almacen> obtenerAlmacenes(){
        return oc.queryByExample(new Almacen());
    }
    
    public List<Articulo> obtenerArticulos(){
        return oc.queryByExample(new Articulo());
    }
    
    public List<Producto> obtenerProductos(){
        return oc.queryByExample(new Producto());
    }
    
    public List<Envase> obtenerEnvases(){
        return oc.queryByExample(new Envase());
    }
    
    public List<UnidadDeMedida> obtenerUnidadesDeMedida(){
        return oc.queryByExample(new UnidadDeMedida());
    }
    
    public List<Producto> obtenerProductosDeUnArticulo(Articulo articulo){
        Query q1 = oc.query();
        q1.constrain(Producto.class);
        q1.descend("articulo").descend("idArticulo").constrain(articulo.getId());
        ObjectSet<Producto> set = q1.execute();
        return set;
    }
    
    public List<Producto> obtenerProductosLimitados(int limSuperior, int limInferior, String denominacion){
        List<Producto> listProd = new ArrayList();
        Query q2 = oc.query();
        q2.constrain(Producto.class);
        Constraint starsWith = q2.descend("articulo").descend("idArticulo").constrain(denominacion).startsWith(true);
        q2.descend("precio").constrain(limInferior).equal().greater();
        q2.descend("precio").constrain(limSuperior).equal().smaller();
        ObjectSet<Producto> producto = q2.execute();
        
        for(Producto prod: producto){
            listProd.add(producto.next());
            
        }
        
        return listProd; 
    }
    
    public List<Producto> obtenerProductosDeUnAlmacen(String idAlmacen, int cantidad){
        final Almacen m;
        ObjectSet<Producto> result = null;
        ObjectSet<Almacen> subQuery = oc.query(new Predicate<Almacen>(){
            @Override
            public boolean match(Almacen et) {
            return et.getId().equalsIgnoreCase(idAlmacen);
            }
        });
 
        if(subQuery.hasNext()){
            m=subQuery.next();
        }
        else{
            return new ArrayList();
        }
 
        result = oc.query(new Predicate<Producto>(){
 
            @Override
                public boolean match(Producto producto) {
                return m.getStock(producto).getCantidad()<= cantidad;
            }
        });
        return result;
    }
    
    public Object obtenerObjetoClave(Object o){
        ObjectSet set = this.oc.queryByExample(o);
        if(set.hasNext()){
            o = set.next();
            if(set.hasNext()){
                throw new Db4oException("Patrón muy general, intente especificar más.");
            }
        }
        else{
            System.out.println("No se ha encontrado el objeto indicado, se creará en la base de datos.");
            oc.store(o);
            oc.commit();
        }
        return o;
    }
    
    public List<Object> obtenerObjetoLista(int hola){
        return null;
    }
    
    public void InsertarDatos(GestionProductos gp){
        /*try{
            //Añadir un almacén
            Almacen alm = gp.obtenerObjeto(new Almacen("1", "Almacen Palma"));

            //Añadir seis articulos
            Articulo art =  gp.obtenerObjeto(new Articulo("1", "Papel"));
            Articulo art2 = gp.obtenerObjeto(new Articulo("2", "Ordenador"));
            Articulo art3 = gp.obtenerObjeto(new Articulo("3", "Mochila"));
            Articulo art4 = gp.obtenerObjeto(new Articulo("4", "Pizza"));
            Articulo art5 = gp.obtenerObjeto(new Articulo("5", "Auriculares"));
            Articulo art6 = gp.obtenerObjeto(new Articulo("6", "Móvil"));
        
            //Añadir tres unidades de medida
            UnidadDeMedida udm =  gp.obtenerObjeto(new UnidadDeMedida("kg", "kilogramo"));
            UnidadDeMedida udm2 = gp.obtenerObjeto(new UnidadDeMedida("l", "litros"));
            UnidadDeMedida udm3 = gp.obtenerObjeto(new UnidadDeMedida("g", "gramos"));
        
            //Añadir tres envases 
            Envase env =  gp.obtenerObjeto(new Envase("Paquete", 20, udm));
            Envase env2 = gp.obtenerObjeto(new Envase("Botella", 1.5, udm2));
            Envase env3 = gp.obtenerObjeto(new Envase("Bandeja", 40, udm3));
        
       
            //Añadir diez productos de diferente tipo
            gp.obtenerObjeto(new Producto(art, "Hacendado", 2.10));
            gp.obtenerObjeto(new Producto(art2, "Acer", 100.00));
            gp.obtenerObjeto(new Producto(art3, "Port Designs", 20.00));
            gp.obtenerObjeto(new Producto(art4, "Casa Tarradellas", 20.00));
            gp.obtenerObjeto(new Producto(art5, "Apple" , 500.00));
            gp.obtenerObjeto(new Producto(art6, "BQ", 250.00));
            gp.obtenerObjeto(new Producto(art2, "Lenovo", 900.00));
            gp.obtenerObjeto(new Producto(art3, "Trust", 50.00));
            gp.obtenerObjeto(new Producto(art4, "Buittoni", 25.50));
            gp.obtenerObjeto(new Producto(art5, "Sony", 60.69));
        
            //Asignación del stock para cada producto al almacén
        }*/
        
        try {
            gp.obtenerObjeto(new UnidadDeMedida("kg", "kilogramo"));
            gp.obtenerObjeto(new UnidadDeMedida("l", "litro"));
            gp.obtenerObjeto(new UnidadDeMedida("g", "gramo"));
 
            Almacen m = gp.obtenerObjeto(new Almacen("01", "Almacen Palma"));
            gp.obtenerObjeto(new Almacen("01", "Almacen Palma"));
 
            //Articulo 1
            Articulo art = gp.obtenerObjeto(new Articulo("Papel"));
            gp.obtenerObjeto(new Producto(art, "De Baño", 2.22));
            gp.obtenerObjeto(new Producto(art, "De Cocina", 1.29));
            gp.obtenerObjeto(new Producto(art, "De Regalo", 8.99));
            
            //Articulo 2
            art = gp.obtenerObjeto(new Articulo("Pasta"));
            UnidadDeMedida u = gp.obtenerObjeto(new UnidadDeMedida("g"));
            Envase env = gp.obtenerObjeto(new Envase("Paquete", 450, u));
            gp.obtenerObjeto(new ProductoEnvasado(art,"Macarrones",1.48,env));
            gp.obtenerObjeto(new ProductoEnvasado(art,"Tortellini",1.45,env));
 
            //Articulo 3
            art = gp.obtenerObjeto(new Articulo("Sopa"));
            gp.obtenerObjeto(new ProductoEnvasado(art,"Maravilla",2.51,env));
            env = gp.obtenerObjeto(new Envase("Paquete", 250, u));
            gp.obtenerObjeto(new ProductoEnvasado(art,"De navidad",3.55,env));
            
            //Articulo 4
            art = gp.obtenerObjeto(new Articulo("Agua"));
            u = gp.obtenerObjeto(new UnidadDeMedida("l"));
            env = gp.obtenerObjeto(new Envase("Botella", 1.5, u));
            gp.obtenerObjeto(new ProductoEnvasado(art,"Font Bella",2.10,env));

            //Articulo 5
            art = gp.obtenerObjeto(new Articulo("Manzana"));
            u = gp.obtenerObjeto(new UnidadDeMedida("kg"));
            gp.obtenerObjeto(new ProductoAGranel(art, 3.21, u));
            
            //Articulo 6
            art = gp.obtenerObjeto(new Articulo("Melocotón"));
            gp.obtenerObjeto(new ProductoAGranel(art, 4.39, u));
 
            gp.actualizarCambios();
 
            List<Producto> listaProd = gp.obtenerProductos();
 
            double[] cantidad = new double[]{5.0, 10.0, 15.0, 25.0, 8.0, 2.0, 17.0, 45.0, 12.5, 1.25};
 
            for(int i=0; i<listaProd.size(); i++){
                Stock stc;
                Producto p = listaProd.get(i);
                stc = new Stock(p, cantidad[i]);
                m.asignarStock(stc);
            }
 
            gp.actualizarCambios(m);
        }
        catch(Db4oException ex){
            System.out.println("Error al insertar datos " + ex.getMessage());
        }
    }
    
    public void modificarDatos(GestionProductos gp){
        try {
            List<Producto> lista = gp.obtenerProductosDeUnArticulo(new Articulo("Pasta"));
            for(Producto prod: lista){
                prod.setPrecio(prod.getPrecio()*1.05);
            }
 
            gp.actualizarCambios();
 
            Almacen alm = gp.obtenerObjeto(new Almacen("01"));
            lista = gp.obtenerProductosDeUnArticulo(new Articulo("Manzana"));
            for(Producto prod2: lista){
                alm.decrementarStockProducto(prod2, 10.0);
            }
 
            gp.actualizarCambios();
 
            alm = gp.obtenerObjeto(new Almacen("01"));
            lista = gp.obtenerProductosDeUnArticulo(new Articulo("Agua"));
            for(Producto prod3: lista){
                alm.decrementarStockProducto(prod3, 12.0);
            }
 
        }
        catch(Db4oException ex){
            System.out.println("Error al modificar los datos " + ex.getMessage());
        }
    }
    
    public void mostrarProductos(GestionProductos gp, int cant){
        try {
            List<Producto> lista = gp.obtenerProductosDeUnAlmacen("01", cant);
            System.out.println("Productos con stock menor o igual a " + cant);
            for(Producto prod: lista){
                System.out.println(prod.toString());
            }
        }
        catch(Db4oException ex){
            System.out.println("Error en la modificación de articulos " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Articulo art = new Articulo();
        Almacen alm = new Almacen();
        Envase env = new Envase();
        Producto pro = new Producto();
        UnidadDeMedida udm = new UnidadDeMedida();
        Scanner ent = new Scanner(System.in);
        GestionProductos gp = new GestionProductos();
        
        //Abrir almacen 
        gp.abrirAlmacen();
        
        //Insertar datos
        gp.InsertarDatos(gp);
        
        gp.mostrarProductos(gp,14);
        
        /*//Realizar consultas        
        
        
        System.out.println("Introduzca que acción quiere realizar: " );
        System.out.println(" \n 1. Encontrar un objeto \n 2. Mostrar lista de un objeto \n 3. Realizar consulta \n 4. Eliminar una instancia");
        int s = ent.nextInt();
        switch(s){
            case 1: 
                System.out.println("¿Que tipo de objeto quiere seleccionar? \n 1. Articulo \n 2. Almacen \n 3. Producto \n 4. Envase \n 5. Unidad de Medida");
                s = ent.nextInt();
                switch(s){
                    case 1:
                        gp.obtenerObjeto(art);
                        break;
                    
                    case 2:
                        gp.obtenerObjeto(alm);
                        break;
                        
                    case 3:
                        gp.obtenerObjeto(pro);
                        break;
                        
                    case 4:
                        gp.obtenerObjeto(env);
                        break;
                        
                    case 5:
                        gp.obtenerObjeto(udm);
                        break;
                }
                break;
                
            case 2:
                System.out.println("¿Que lista quiere mostrar? \n 1. Articulos \n 2. Almacenes \n 3. Productos \n 4. Envases \n 5. Unidades de Medida");
                s = ent.nextInt();
                switch(s){
                    case 1:
                        gp.obtenerArticulos();
                        break;
                    
                    case 2:
                        gp.obtenerAlmacenes();
                        break;
                        
                    case 3:
                        gp.obtenerProductos();
                        break;
                        
                    case 4:
                        gp.obtenerEnvases();
                        break;
                        
                    case 5:
                        gp.obtenerUnidadesDeMedida();
                        break;
                }
                break;
            
            case 3: 
                System.out.println("¿Que consulta quiere realizar? \n 1. Tots els productes d’un determinat article. \n 2. Tots el productes limitats pel preu i corresponents a aquells articles \n 3. Tots els productes d’un magatzem especificat que tinguin algun un estoc menor");
                s = ent.nextInt();
                switch(s){
                    case 1:
                        gp.obtenerProductosDeUnArticulo("2");
                        break;
                    
                    case 2:
                        gp.obtenerProductosLimitados(3, 6, "Pasta");
                        break;
                        
                    case 3:
                        gp.obtenerProductosDeUnAlmacen("2", 2);
                        break;
                }
                break;
            
            case 4:
                gp.eliminarInstancias(alm);
                break;
        }
        gp.actualizarCambios(alm);*/
        gp.cerrarAlmacen();
    }
}
