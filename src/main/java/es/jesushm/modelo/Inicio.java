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
import javax.servlet.annotation.WebListener;

/**
 * Carga las categorías y características en el contexto
 *
 * @author jesus
 */
@WebListener
public class Inicio implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        DAOFactory daoF = DAOFactory.getDAOFactory();
        ICategoriasDAO cDAO = daoF.getCategoriasDAO();
        List<Categoria> catYCar;
        catYCar = cDAO.getCategoriasYCaracs();
        if (catYCar != null) {
            sc.setAttribute("categorias", catYCar);
        } else {
            sc.setAttribute("categorias", null);
        }
        System.out.println("Se han cargado las categorias y caracteristicas en el contexto");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //Nada por ahora
    }

}
