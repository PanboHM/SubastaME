/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IUsuariosDAO;

/**
 *
 * @author jesus
 */
public abstract class DAOFactory {

//    public static final int MYSQL = 1;
//    public static final int ORACLE = 2;
//    public static final int DERBY = 3;
    public abstract IUsuariosDAO getUsuariosDAO();
    public abstract ICategoriasDAO getCategoriaDAO();

    public static DAOFactory getDAOFactory() { //este metodo podría recibir un entero en el switch para decidir que base de datos vamos a usar
        DAOFactory daoF = new MySQLDAOFactory();

//        switch (tipo) {
//            case MYSQL:
//                daof = new MySQLDAOFactory();
//                break;
//            case ORACLE:
//                daof = new OracleDAOFactory();
//                break
//            case DERBY:
//                daof = new DerbyDAOFactory();
//                break
//        }
        return daoF;
    }
}
