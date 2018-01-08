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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Lee las subastas y pujas del usuario en sesión y las carga en el request.
 *
 * @author jesus
 */
@WebServlet(name = "PrevControlPanel", urlPatterns = {"/PrevControlPanel"})
public class PrevControlPanel extends HttpServlet {

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

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IArticulosDAO aDAO = daoF.getArticulosDAO();
        IPujasDAO pDAO = daoF.getPujasDAO();
        List<Articulo> subastas;

        //Recopilamos las subastas finalizadas
        subastas = aDAO.getArticulosUsuario(" where fechaFin <= now()  and idCliente = " + usuario.getIdUsuario());
        if (!subastas.isEmpty()) {
            for (Articulo artFor : subastas) {
                artFor.getPujas().add(pDAO.getUltimaPuja(artFor.getIdArticulo()));
            }
        } else {
            subastas = null;
        }
        request.setAttribute("misSubastasFinalizadas", subastas);
//Ahora las que están sin finalizar
        subastas = aDAO.getArticulosUsuario(" where fechaFin > now() and idCliente = " + usuario.getIdUsuario());
        if (!subastas.isEmpty()) {
            for (Articulo artFor : subastas) {
                artFor.setPujas(new ArrayList());
                artFor.getPujas().add(pDAO.getUltimaPuja(artFor.getIdArticulo()));
            }
        } else {
            subastas = null;
        }
        request.setAttribute("misSubastasActivas", subastas);
// Ahora las pujas que ha realizado el cliente
        subastas = new ArrayList();
        List<Puja> misPujas = pDAO.getPujas(" where idCliente = " + usuario.getIdUsuario());
        Articulo articulo;
        if (!misPujas.isEmpty()) {
            for (Puja pujaFor : misPujas) {
                articulo = aDAO.getArticulo(pujaFor.getIdArticulo());
                articulo.setPujas(new ArrayList());
                articulo.getPujas().add(pujaFor);
                subastas.add(articulo);
            }
        } else {
            subastas = null;
        }
        request.setAttribute("misPujas", subastas);
        request.getRequestDispatcher("/jsp/panelDeControl.jsp").forward(request, response);

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
