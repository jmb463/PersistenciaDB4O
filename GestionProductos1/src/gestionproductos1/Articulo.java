package gestionproductos1;

import java.io.Serializable;

/**
 * Classe que representa una article de venda de l'aplicació comercial
 * @author José Martín Bruno
 */
public class Articulo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idArticulo; //denominació de l'article
    private String descripcion;

    /**
     * Constructor per defecte
     */
    public Articulo() {
    }

    /**
     * Constructor que inicializa la descripció de l'article
     * @param idArticulo inicalitza la denominació de l'article.
     */
    public Articulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    /**
     * Constructor que inicializa la denominació i la descripció de l'article. 
     * @param idArticulo és la denominació de l'article i fa d'identificador .
     * @param descripcion És la descripció que caldrà assignar a l'article 
     */
    public Articulo(String idArticulo, String descripcion) {
        this.idArticulo=idArticulo;
        this.descripcion = descripcion;
    }
    
    /**
     * Obté la denominació de l'article
     * @return denominació de l'article.
     */
    public String getId() {
        return idArticulo;
    }

    /**
     * Assigna la denominació de l'article que servirà també d'identificador
     * @param id Valor que s'assignarà a l'id de l'article
     */
    public void setId(String id) {
        this.idArticulo = id;
    }

//    /**
//     * Indica si l'objecte on es fa la crida i el que es passa per paràmetre són 
//     * iguals.
//     * @param object a comparar
//     * @return cert si són iguals. Fals en cas contrari.
//     */
//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof Article)) {
//            return false;
//        }
//        Article other = (Article) object;
//        if ((this.id == null && other.id != null) 
//                || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

    /**
     * Obté una cadena representativa de l'objecte on es fa la crida
     * @return cadena representativa de l'objecte 
     */
    @Override
    public String toString() {
        return "Articulo[id=" + idArticulo + "]";
    }

    /**
     * Obté la descripció de l'article
     * @return la descripcio
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Assigna un valor a la descripció de l'article
     * @param descripcion És el valor a assignar.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
