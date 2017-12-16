/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;

/**
 *
 * @author jesus
 */
public interface ICaracYArtDAO {

    /**
     *
     * @param articulo recibe un articulo con los valores de las caracteristicas a insertar en la base de datos.
     * @return the boolean true si se han insertado correctamente y false si no se han insertado correctamente
     */
    public boolean setCaracYart(Articulo articulo);
    
}
