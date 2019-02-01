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
        actualizarCambios();
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
        System.out.println("Recuperando envase...");
        return (Envase) obtenerObjetoClave(envase);
    }
    
    public UnidadDeMedida obtenerObjeto(UnidadDeMedida medida){
        System.out.println("Recuperando unidad de medida...");
        return (UnidadDeMedida) obtenerObjetoClave(medida);
    }
    
    public ProductoEnvasado obtenerObjeto(ProductoEnvasado pe){
        System.out.println("Recuperando producto envasado...");
        return (ProductoEnvasado) obtenerObjetoClave(pe);
    }
    
    public ProductoAGranel obtenerObjeto(ProductoAGranel pag){
        System.out.println("Recuperando producto a granel...");
        return (ProductoAGranel) obtenerObjetoClave(pag);
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
    
    public List<ProductoEnvasado> obtenerProductosEnvasados(){
        return oc.queryByExample(new ProductoEnvasado());
    }
    
    public List<ProductoAGranel> obtenerProductosAGranel(){
        return oc.queryByExample(new ProductoAGranel());
    }
    
    public List<Producto> obtenerProductosDeUnArticulo(Articulo articulo){
        Query q1 = oc.query();
        q1.constrain(Producto.class);
        q1.descend("articulo").descend("idArticulo").constrain(articulo.getId());
        ObjectSet<Producto> set = q1.execute();
        System.out.println(set.toString());
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
            System.out.println(producto.toString());
            
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
        System.out.println(result.toString());
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
            actualizarCambios(o);
        }
        return o;
    }
    
    public void InsertarDatos(GestionProductos gp){
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
 
            double[] cantidad = new double[]{5.2, 11.0, 15.5, 25.0, 28.0, 23.0, 11.0, 34.0, 2.5, 1.50};
 
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
            
            //1 Incrementación del articulo en un 5%
            List<Producto> lista = gp.obtenerProductosDeUnArticulo(new Articulo("Pasta"));
            for(Producto prod: lista){
                prod.setPrecio(prod.getPrecio()*1.05);
            }
 
            gp.actualizarCambios();
            
            
            //2 y 3
            Almacen alm = gp.obtenerObjeto(new Almacen("01"));
            lista = gp.obtenerProductosDeUnArticulo(new Articulo("Manzana"));
            for(Producto prod2: lista){
                alm.decrementarStockProducto(prod2, 11.0);
            }
 
            gp.actualizarCambios();
 
            alm = gp.obtenerObjeto(new Almacen("01"));
            lista = gp.obtenerProductosDeUnArticulo(new Articulo("Agua"));
            for(Producto prod3: lista){
                alm.decrementarStockProducto(prod3, 13.0);
            }
            
            gp.actualizarCambios();
 
        }
        catch(Db4oException ex){
            System.out.println("Error al modificar los datos " + ex.getMessage());
        }
    }
    
    public void mostrarProductos(GestionProductos gp, int cant){
        List<Producto> lista = gp.obtenerProductosDeUnAlmacen("01", cant);
        for(Producto prod: lista){
            System.out.println(prod.toString());
        }
       
    }
    
    public static void main(String[] args) {
        Articulo art = new Articulo("Papel");
        Almacen alm = new Almacen("01");
        UnidadDeMedida udm = new UnidadDeMedida("l");
        Envase env = new Envase("Botella", 12, udm);
        Producto pro = new Producto(art, "Mercadona", 1.20);
        ProductoEnvasado pe = new ProductoEnvasado(art, "Scotex", 2.00, env);
        ProductoAGranel pag = new ProductoAGranel(art, 20, udm);
        Scanner ent = new Scanner(System.in);
        GestionProductos gp = new GestionProductos();
        Boolean seguir = true;
        
        //Abrir almacen 
        gp.abrirAlmacen();
        
        //Insertar datos
        gp.InsertarDatos(gp);
        
        //Realizar consultas       
        while(seguir){
            System.out.println("Introduzca que acción quiere realizar: " );
            System.out.println(" \n 1. Encontrar un objeto \n 2. Mostrar lista de un objeto \n 3. Realizar consulta \n 4. Eliminar una instancia \n 5. Actualizar datos \n 6. Mostrar stock");
            int s = ent.nextInt();
            switch(s){
                case 1: 
                    System.out.println("¿Que tipo de objeto quiere seleccionar? \n 1. Articulo \n 2. Almacen \n 3. Producto \n 4. Envase \n 5. Unidad de Medida \n 6. Producto Envasado \n 7. Producto a granel");
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
                        
                        case 6:
                            gp.obtenerObjeto(pe);
                            break;
                        
                        case 7: 
                            gp.obtenerObjeto(pag);
                            break;
                    }
                    break;
                
                case 2:
                    System.out.println("¿Que lista quiere mostrar? \n 1. Articulos \n 2. Almacenes \n 3. Productos \n 4. Envases \n 5. Unidades de Medida \n 6. Productos envasados \n 7. Productos a granel");
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
                        
                        case 6:
                            gp.obtenerProductosEnvasados();
                            break;
                        
                        case 7:
                            gp.obtenerProductosAGranel();
                            break;
                    }
                    break;
            
                case 3: 
                    System.out.println("¿Que consulta quiere realizar? \n 1. Todos los productos de un determinado articulo. \n 2. Todos los productos limitados por precio y correspondientes a articulos. \n 3. Todos los productos de un almacén especificado que tengan un stock menor a una cantidad");
                    s = ent.nextInt();
                    switch(s){
                        case 1:
                            gp.obtenerProductosDeUnArticulo(art);
                            break;
                    
                        case 2:
                            gp.obtenerProductosLimitados(10, 0, "Papel");
                            break;
                        
                        case 3:
                            gp.obtenerProductosDeUnAlmacen("01", 10);
                            break;
                    }
                    break;
            
                case 4:
                    gp.eliminarInstancias(alm);
                    break;
                
                case 5:
                    gp.modificarDatos(gp);
                    break;
                
                case 6:
                    int cantidad = 10;
                    System.out.println("Productos con stock menor o igual a " + cantidad);
                    gp.mostrarProductos(gp, cantidad);
            }
            System.out.println("¿Quiere continuar? Responda con si o no, gracias");
            String cont= ent.next();
           
            
            if(cont.equalsIgnoreCase("no")){
                seguir=false;
            }
        }
        
        
        gp.cerrarAlmacen();
    }
}
