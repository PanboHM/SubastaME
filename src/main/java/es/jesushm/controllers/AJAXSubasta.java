/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.IPujasDAO;
import es.jesushm.beans.Articulo;
import es.jesushm.beans.Puja;
import es.jesushm.beans.Usuario;
import java.io.IOException;
import java.util.Date;
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
@WebServlet(name = "AJAXSubasta", urlPatterns = {"/AJAXSubasta"})
public class AJAXSubasta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Contiene AJAX para pujar y para actualizar la lista de pujas
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String donde = request.getParameter("donde");
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IPujasDAO pDAO = daoF.getPujasDAO();
        switch (donde) {
            case "actualizarPujas":
                List<Puja> pujas = pDAO.getPujas(" where idArticulo = " + request.getParameter("idArticulo"));
                JSONArray pujasJSON = new JSONArray(pujas);
                response.setContentType("application/json");
                response.getWriter().print(pujasJSON);
                break;
            case "pujar":
                String resultado;
                int idSubastador = Integer.parseInt(request.getParameter("idSubastador"));
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
                int idPujador = usuario.getIdUsuario();
                if (idPujador == idSubastador) {
                    resultado = "mismoIndividuo";
                } else {
                    double importePuja = Double.parseDouble(request.getParameter("importePuja").replace(",", "."));
                    int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
                    IArticulosDAO aDAO = daoF.getArticulosDAO();
                    Articulo articulo = aDAO.getArticulo(idArticulo);
                    if (articulo.getFechaFin().after(new Date())) {
                        if (importePuja > pDAO.getUltimaPuja(idArticulo).getImporte()) {
                            Puja pujaPujador = new Puja();
                            pujaPujador.setIdArticulo(idArticulo);
                            pujaPujador.setIdCliente(idPujador);
                            pujaPujador.setImporte(importePuja);
                            if (pDAO.setPuja(pujaPujador)) {
                                resultado = "correcto";
                            } else {
                                resultado = "error";
                            }
                        } else {
                            resultado = "inferior";
                        }
                    } else {
                        resultado = "fuera";
                    }
                }
                response.getWriter().print(resultado);
                break;
            default:
                System.out.println("Hola");
                break;
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
