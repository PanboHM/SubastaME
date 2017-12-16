/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.ICaracYArtDAO;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IClientesDAO;
import es.jesushm.DAOs.IFotografiasDAO;
import es.jesushm.DAOs.IUsuariosDAO;

/**
 * Factoria de DAO´s
 * @author jesus
 */
public abstract class DAOFactory {

    /**
     *
     * @return Devuelve un objeto USuariosDAO que nos permitirá usar sus métodos
     */
    public abstract IUsuariosDAO getUsuariosDAO();

    /**
     *
     * @return Devuelve un objeto CategoriasDAO que nos permitirá usar sus métodos
     */
    public abstract ICategoriasDAO getCategoriasDAO();

    /**
     *
     * @return Devuelve un objeto ArticulosDAO que nos permitirá usar sus métodos
     */
    public abstract IArticulosDAO getArticulosDAO();

    /**
     *
     * @return Devuelve un objeto CaracYArtDAO que nos permitirá usar sus métodos
     */
    public abstract ICaracYArtDAO getCaracYArtDAO();

    /**
     *
     * @return Devuelve un objeto FotografiasDAO que nos permitirá usar sus métodos
     */
    public abstract IFotografiasDAO getFotografiasDAO();

    /**
     *
     * @return Devuelve un objeto ClientesDAO que nos permitirá usar sus métodos
     */
    public abstract IClientesDAO getClientesDAO();

    /**
     * 
     * @return devuelve un objeto DAOFactory dependiendo a qué base de datos nos conectamos
     */
    public static DAOFactory getDAOFactory() {
        DAOFactory daoF = new MySQLDAOFactory();
        return daoF;
    }
}
