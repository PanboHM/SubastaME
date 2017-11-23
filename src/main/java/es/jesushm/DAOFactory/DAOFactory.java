/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.IUsuariosDAO;

/**
 *
 * @author jesus
 */
public abstract class DAOFactory {

//    public static final int MYSQL = 1;
//    public static final int ORACLE = 2;
//    public static final int DERBY = 3;
    public abstract IUsuariosDAO getUsuarioDAO();

    public static DAOFactory getDAOFactory() { //este metodo podría recibir un entero para el switch
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
