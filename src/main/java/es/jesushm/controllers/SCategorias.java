/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.beans.Categoria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;

/**
 *
 * @author jesus
 */
@WebServlet(name = "SCategorias", urlPatterns = {"/SCategorias"})
public class SCategorias extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getParameter("delContexto") != null) {
            //Obtenemos el arrayList de categorias que hemos cargado al inicio de la aplicacion en el context
            List<Categoria> categorias = (ArrayList<Categoria>) getServletContext().getAttribute("categorias");
            
            if (categorias != null) { //si hay categorias las transformamos a un JSONArray
                
                JSONArray categoriasJSON = new JSONArray(categorias);
//                System.out.println("JSON YA: " + categoriasJSON);

                //indicamos al response que lo que añadimos en un JSON
                response.setContentType("application/json");
                
                //esto no me preguntes porqué, pero se hace así
                //parece ser que el metodo AJAX que llama al controlador, recibe la respuesta por el response
                response.getWriter().print(categoriasJSON);
            } else {
                response.getWriter().print("null");
            }
        } else {
            //AQUI CARGABA LAS CATEGORIAS EN EL CONTEXTO, PERO CON EL LISTENER IMPLEMENTADO DE MOMENTO ESTO NO SE USA.
            DAOFactory daoF = DAOFactory.getDAOFactory();
            ICategoriasDAO cDAO = daoF.getCategoriasDAO();
            List<Categoria> catYCar;
//        List<Caracteristica> caracteristicas;
            catYCar = cDAO.getCategoriasYCaracs();
            if (catYCar != null) {
                /*
            Comprobación de los datos que me llegan del DAO.
            
            for (Categoria cat : catYCar) {
                System.out.println("IDCAT: " + cat.getIdCategoria());
                System.out.println("Categoría: " + cat.getDenominacion());
                System.out.println("Características: ");
                caracteristicas = cat.getCaracteristicas();
                for (Caracteristica caracteristica : caracteristicas) {
                    System.out.println(" - ID: " + caracteristica.getIdCaracteristica());
                    System.out.println(" - " + caracteristica.getDenominacion());
                }
            }
                 */
                getServletContext().setAttribute("categorias", catYCar);
            } else {
                getServletContext().setAttribute("categorias", null);
            }
            request.setAttribute("mensaje", "Categorias cargadas en el contexto");
            request.getRequestDispatcher("/jsp/prueba.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
