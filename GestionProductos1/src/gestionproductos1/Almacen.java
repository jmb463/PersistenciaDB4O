/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author José Martín Bruno
 */
public class Almacen implements Serializable {
    private String idAlmacen;
    private String descripcion;
    private Map<Producto, Stock> stock = new HashMap<Producto, Stock>();

    public Almacen() {
    }

    public Almacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
    
    public Almacen(String idAlmacen, String descripcion) {
        this.idAlmacen = idAlmacen;
        this.descripcion = descripcion;
    }
    
    

    public String getId() {
        return idAlmacen;
    }

    public void setId(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    @Override
    public String toString() {
        return "Mag: " + idAlmacen ;
    }

    /**
     * @return the descripcio
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcio to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the stock
     */
    public Map<Producto, Stock> getStock() {
        return stock;
    }

    /**
     * @param Stock the stock to set
     */
    protected void setStock(Map<Producto, Stock> Stock) {
        this.stock = Stock;
    }
    
    public void asignarStock(Producto producto, Double cantidad){
        this.stock.put(producto, new Stock(producto, cantidad));
    }

    public void asignarStock(Stock stockPr){
        this.stock.put(stockPr.getProducto(), stockPr);
    }

    public void eliminarProducte(Producto producto){
        this.stock.remove(producto);
    }
    
    public void incrementarStockProducto(Producto producto, Double inc){
        Stock stockProd;
        if(!stock.containsKey(producto)){
            stock.put(producto, new Stock(producto, 0.0));
            
        }
        stockProd = stock.get(producto);
        Double cant = stockProd.getCantidad();
        cant+=inc;
        stockProd.setCantidad(cant);
    }

    public void decrementarStockProducto(Producto producto, Double inc){
        incrementarStockProducto(producto, -inc);
    }
    
    public Stock getStock(Producto producto){
        return stock.get(producto);
    }
    
    public Iterator<Producto> getProductos(){
        return stock.keySet().iterator();
    }

}
