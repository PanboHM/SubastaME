/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Puja;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IPujasDAO {

    /**
     * Devuelve la última puja de un artículo en concreto
     *
     * @param idArticulo id del artículo
     * @return the double valor de la última puja
     */
    public Puja getUltimaPuja(int idArticulo);

    /**
     * Nos devuelve el historial de pujas de un articulo o usuario concreto
     *
     * @param where id de usuario o id de articulo
     * @return ArrayList de pujas
     */
    public List<Puja> getPujas(String where);

    /**
     * Establece una puja en la base de datos
     *
     * @param puja puja a insertar
     * @return true si todo correcto, si no false
     */
    public boolean setPuja(Puja puja);
}
