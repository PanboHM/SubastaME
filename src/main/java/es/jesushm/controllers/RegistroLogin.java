/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.beans.Cliente;
import es.jesushm.beans.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author jesus
 */
@WebServlet(name = "RegistroLogin", urlPatterns = {"/RegistroLogin"})
public class RegistroLogin extends HttpServlet {

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
        String donde = request.getParameter("enviar");
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IUsuariosDAO uDAO = daoF.getUsuariosDAO();
        if (donde != null) {
            if (donde.equals("registro")) { //parte de registro
                Cliente cliente = new Cliente();
                Usuario usuario = new Usuario();
                try {
                    BeanUtils.populate(cliente, request.getParameterMap());
                    BeanUtils.populate(usuario, request.getParameterMap());
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                usuario.setClienteU(cliente);
                Date ultimoAcceso = new Date();
                usuario.setUltimoAcceso(ultimoAcceso);
                Boolean registroCorrecto = uDAO.registro(usuario);
                if (registroCorrecto) {
                    request.setAttribute("mensaje", "Usuario insertado");
                } else {
                    request.setAttribute("mensaje", "Usuario NO insertado");
                }
                request.getRequestDispatcher("/jsp/prueba.jsp").forward(request, response);
            } else if (donde.equals("login")) { //parte de login
                Usuario usuario = new Usuario();
                try {
                    BeanUtils.populate(usuario, request.getParameterMap());
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                usuario = uDAO.login(usuario);
                if (usuario != null) {
                    request.setAttribute("mensaje", "Login correcto");
                } else {
                    request.setAttribute("mensaje", "Login incorrecto");
                }
                request.getRequestDispatcher("/jsp/prueba.jsp").forward(request, response);
            }
        } else if (request.getParameter("datoEmail") != null) {
            Boolean existe = uDAO.comprobarEmail(request.getParameter("datoEmail"));
            response.getWriter().print(existe);
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
