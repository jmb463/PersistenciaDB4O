/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;

import java.io.Serializable;

/**
 *
 * @author José Martín Bruno
 */
public class Stock implements Serializable {
    private Producto producto=null;
    private Double cantidad=0.0;

    public Stock() {
    }

    public Stock(Producto prod, Double cantidad) {
        this.producto = prod;
        this.cantidad = cantidad;
    }
    
    /**
     * @return the quantitat
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the quantitat to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString(){
        return producto.toString() + "[Cant: " + getCantidad() +"]";
    }

    /**
     * @return the producte
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producte to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
