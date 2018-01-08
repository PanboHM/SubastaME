/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOFactory;

import es.jesushm.DAOs.ArticulosDAO;
import es.jesushm.DAOs.CaracYArtDAO;
import es.jesushm.DAOs.CaracteristicasDAO;
import es.jesushm.DAOs.CategoriasDAO;
import es.jesushm.DAOs.ClientesDAO;
import es.jesushm.DAOs.FotografiasDAO;
import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.ICaracYArtDAO;
import es.jesushm.DAOs.ICaracteristicasDAO;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IClientesDAO;
import es.jesushm.DAOs.IFotografiasDAO;
import es.jesushm.DAOs.IPujasDAO;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.DAOs.PujasDAO;
import es.jesushm.DAOs.UsuariosDAO;

/**
 * Factoria de DAO´s de MySQL
 * @author jesus
 */
public class MySQLDAOFactory extends DAOFactory {

    @Override
    public IUsuariosDAO getUsuariosDAO() {
        return new UsuariosDAO();
    }

    @Override
    public ICategoriasDAO getCategoriasDAO() {
        return new CategoriasDAO();
    }

    @Override
    public IArticulosDAO getArticulosDAO() {
        return new ArticulosDAO();
    }

    @Override
    public ICaracYArtDAO getCaracYArtDAO() {
        return new CaracYArtDAO();
    }

    @Override
    public IFotografiasDAO getFotografiasDAO() {
        return new FotografiasDAO();
    }

    @Override
    public IClientesDAO getClientesDAO() {
        return new ClientesDAO();
    }

    @Override
    public IPujasDAO getPujasDAO() {
        return new PujasDAO();
    }

    @Override
    public ICaracteristicasDAO getCaracteristicasDAO() {
        return new CaracteristicasDAO();
    }

}
