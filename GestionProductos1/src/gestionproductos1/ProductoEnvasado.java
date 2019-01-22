/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;


/**
 *
 * @author José Martín Bruno
 */
public class ProductoEnvasado extends Producto {
    private Envase envase;

    public ProductoEnvasado() {
    }

    public ProductoEnvasado(Articulo articulo, String marca, double precio) {
        super(articulo, marca, precio);
    }

    public ProductoEnvasado(Articulo articulo, String marca, double precio, Envase envase) {
        super(articulo, marca, precio);
        this.envase = envase;
    }

    @Override
    public String toString() {
        return envase.toString() + " de " + super.toString();
    }

    /**
     * @return the envas
     */
    public Envase getEnvase() {
        return envase;
    }

    /**
     * @param envase the envas to set
     */
    public void setEnvase(Envase envase) {
        this.envase = envase;
    }
    
    
}
