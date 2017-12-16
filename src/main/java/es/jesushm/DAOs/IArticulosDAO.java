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
public interface IArticulosDAO {

    /**
     *
     * @param articulo contiene los datos a insertar en la base de datos
     * @return devuelve un entero que es el idArticulo para poder usarlo en proximas inserciones a la base de datos
     */
    public int setArticulo(Articulo articulo);
}
