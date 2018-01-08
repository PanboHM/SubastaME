/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Categoria;

/**
 *
 * @author jesus
 */
public interface ICaracteristicasDAO {

    /**
     * Inserta una serie de características de una categoria
     * 
     * @param categoria
     * @return the boolean
     */
    public boolean setCaracterísticas(Categoria categoria);

}
