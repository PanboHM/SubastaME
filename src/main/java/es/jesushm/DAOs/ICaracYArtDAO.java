/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.beans.CatYCar;
import java.util.List;

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
    
    /**
     * Obtiene un entero(idArticulo) y devuelve los valores de las características de ese artículo.
     * @param idArticulo id del artiuclo
     * @return devuelve un ArrayList de valores.
     */
    public List<CatYCar> getCaracYArt(int idArticulo);
}
