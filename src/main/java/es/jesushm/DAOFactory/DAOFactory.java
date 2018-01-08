/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.ICaracYArtDAO;
import es.jesushm.DAOs.ICaracteristicasDAO;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IClientesDAO;
import es.jesushm.DAOs.IFotografiasDAO;
import es.jesushm.DAOs.IPujasDAO;
import es.jesushm.DAOs.IUsuariosDAO;

/**
 * Factoria de DAO´s
 *
 * @author jesus
 */
public abstract class DAOFactory {

    public abstract IUsuariosDAO getUsuariosDAO();

    public abstract ICategoriasDAO getCategoriasDAO();

    public abstract IArticulosDAO getArticulosDAO();

    public abstract ICaracYArtDAO getCaracYArtDAO();

    public abstract IFotografiasDAO getFotografiasDAO();

    public abstract IClientesDAO getClientesDAO();
    
    public abstract IPujasDAO getPujasDAO();
    
    public abstract ICaracteristicasDAO getCaracteristicasDAO();

    public static DAOFactory getDAOFactory() {
        DAOFactory daoF = new MySQLDAOFactory();
        return daoF;
    }
}
