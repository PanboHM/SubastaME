/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IArticulosDAO {

    /**
     * Guarda la info del articulo en la base de datos
     * 
     * @param articulo contiene los datos a insertar en la base de datos
     * @return devuelve un entero que es el idArticulo para poder usarlo en
     * proximas inserciones a la base de datos
     */
    public int setArticulo(Articulo articulo);

    /**
     * Devuelve un conjunto de articulos con sus respectivos clientes según que
     * categoría y que la fecha actual esté entre fechaInicio y fechaFin
     *
     * @param idCategoria numero de la categoría
     * @return colección de artículos de esa categoría
     */
    public List<Articulo> getCatalogoSubastas(int idCategoria);

    /**
     * Recibe un idArticulo y devuelve toda su información
     *
     * @param idArticulo id del artículo a buscar
     * @return object Articulo con toda la info
     */
    public Articulo getArticulo(int idArticulo);
    
    /**
     * Devuelve las subastas a cargo de un usuario
     * 
     * @param where en principio indica el usuario y la fecha, pero es flexible
     * @return un arrayList de subastas del usuario en cuestión
     */
    public List<Articulo> getArticulosUsuario(String where);
}
