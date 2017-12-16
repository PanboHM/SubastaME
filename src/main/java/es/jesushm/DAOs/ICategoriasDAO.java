/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Categoria;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface ICategoriasDAO {

    /**
     * Obtiene las categorías y las caracteristicas de cada categoria
     * @return un ArrayList con las categorías y cada una de sus características
     */
    public List<Categoria> getCategoriasYCaracs();
}
