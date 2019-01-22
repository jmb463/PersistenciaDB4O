/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionproductos1;

import java.io.Serializable;

/**
 *
 * @author José Martin Bruno
 */
public class Envase implements Serializable {
    private String tipo;
    private double cantidad;
    private UnidadDeMedida unidad;

    public Envase() {
    }

    public Envase(String tipo, double cantidad, UnidadDeMedida unidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }
    
    /**
     * @return the tipus
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the unidad
     */
    public UnidadDeMedida getUnitat() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnitat(UnidadDeMedida unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return tipo + " de " + cantidad + " " + unidad.getSimbolo();
    }    
   
}
