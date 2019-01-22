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
public class UnidadDeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    private String simbolo=null;
    private String descripcion=null;

    public UnidadDeMedida() {
    }

    public UnidadDeMedida(String abreviacion) {
        this.simbolo = abreviacion;
    }
    
    public UnidadDeMedida(String abreviacion, String descripcion) {
        this.simbolo = abreviacion;
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return getSimbolo() + "(" +getDescripcion()+")";
    }

    /**
     * @return the simbol
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param abreviacion the simbol to set
     */
    public void setSimbolo(String abreviacion) {
        this.simbolo = abreviacion;
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
    
}
