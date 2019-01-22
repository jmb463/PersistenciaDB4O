/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;


/**
 *
 * @author josep
 */
public class ProductoAGranel extends Producto {
    private static final long serialVersionUID = 1L;
    private UnidadDeMedida unidad;

    public ProductoAGranel() {
    }

    public ProductoAGranel(Articulo articulo, double precio) {
        super(articulo, null, precio);
    }
    
    public ProductoAGranel(Articulo articulo, double precio, UnidadDeMedida unidad) {
        super(articulo, null, precio);
        this.unidad = unidad;
    }
    
    @Override
    public String toString() {
        return "Producto a granel [" + getArticulo().toString()  + ", precio=" + getPrecio() + "/" + unidad.getSimbolo() + "]";        
    }

    /**
     * @return the unidad
     */
    public UnidadDeMedida getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unitat to set
     */
    public void setUnitat(UnidadDeMedida unidad) {
        this.unidad = unidad;
    }
    
}
