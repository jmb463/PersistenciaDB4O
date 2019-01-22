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
public class Producto implements Serializable {
    private Articulo articulo;
    private String marca;
    private double precio;

    public Producto() {
    }

    public Producto(Articulo articulo, String marca, double precio) {
        this.articulo = articulo;
        this.precio = precio;
        this.marca=marca;
    }
    
    @Override
    public String toString() {
        return "Producto [" + articulo.toString()+ ", marca=" + marca + ", precio=" + precio + "]";
    }

    /**
     * @return the article
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the article to set
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    /**
     * @return the preu
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the preu to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
}
