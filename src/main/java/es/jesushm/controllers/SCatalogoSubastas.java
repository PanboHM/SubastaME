/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.ICaracYArtDAO;
import es.jesushm.DAOs.IFotografiasDAO;
import es.jesushm.beans.Articulo;
import java.io.IOException;
import java.util.List;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jesus
 */
@WebServlet(name = "SCatalogoSubastas", urlPatterns = {"/SCatalogoSubastas"})
public class SCatalogoSubastas extends HttpServlet {

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
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IArticulosDAO aDAO = daoF.getArticulosDAO();
        IFotografiasDAO fDAO = daoF.getFotografiasDAO();
        ICaracYArtDAO cyaDAO = daoF.getCaracYArtDAO();

        if (request.getParameter("idSubasta") != null) {
            Articulo articulo = aDAO.getArticulo(Integer.parseInt(request.getParameter("idSubasta")));
            articulo.setFotografias(fDAO.getFotografias(articulo.getIdArticulo()));
            articulo.setCaracteristicas(cyaDAO.getCaracYArt(articulo.getIdArticulo()));
//            int idCategoria=Integer.parseInt(request.getParameter("idCategoria"));
//            request.setAttribute("idCategoria", idCategoria);
            request.setAttribute("subasta", articulo);
            request.getRequestDispatcher("/jsp/subasta.jsp").forward(request, response);
        } else {
            int queCategoria = Integer.parseInt(request.getParameter("idCategoria"));
//            ServletContext sc = request.getServletContext();
            List<Articulo> articulos = aDAO.getCatalogoSubastas(queCategoria);
            if (!articulos.isEmpty()) {
                for (Articulo artFor : articulos) {
                    artFor.setFotografias(fDAO.getFotografias(artFor.getIdArticulo()));
//                    artFor.setCaracteristicas(cyaDAO.getCaracYArt(artFor.getIdArticulo()));
                }
            }
            request.setAttribute("catalogoSubastas", articulos);
            request.getRequestDispatcher("/jsp/catalogoSubastas.jsp").forward(request, response);
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
