/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.modelo;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.beans.Categoria;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author jesus
 */
public class Inicio implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("El método contextInitialized de Inicio ha sido llamdo");
        ServletContext sc = sce.getServletContext();
        DAOFactory daoF = DAOFactory.getDAOFactory();
        ICategoriasDAO cDAO = daoF.getCategoriaDAO();
        List<Categoria> catYCar;
        catYCar = cDAO.getCategorias();
        if (catYCar != null) {
            sc.setAttribute("categorias", catYCar);
        } else {
            sc.setAttribute("categorias", null);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //Nada por ahora
    }

}
