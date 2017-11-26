/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.CategoriasDAO;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.DAOs.UsuariosDAO;

/**
 *
 * @author jesus
 */
public class MySQLDAOFactory extends DAOFactory {

    @Override
    public IUsuariosDAO getUsuariosDAO() {
        return new UsuariosDAO();
    }

    @Override
    public ICategoriasDAO getCategoriaDAO() {
        return new CategoriasDAO();
    }

}
