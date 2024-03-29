/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.beans.Fotografia;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IFotografiasDAO {

    /**
     * Inserta una serie de imagenes que contienen la url en String en la base de datos
     * @param articulo recibe el articulo con el arrayList de fotografias
     * @return the boolean true si se han insertado correctamente y false si ha habido fallo
     */
    public boolean setFotografias(Articulo articulo);
    
    /**
     *
     * Devuelve un ArrayList de fotografías de un artículo en concreto
     * 
     * @param idArticulo Entero del artículo en cuestión
     * @return ArrayList de fotografías del artículo en cuestión.
     */
    public List<Fotografia> getFotografias(int idArticulo);
}
