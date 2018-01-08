/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.ICaracteristicasDAO;
import es.jesushm.DAOs.ICategoriasDAO;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.beans.Caracteristica;
import es.jesushm.beans.Categoria;
import es.jesushm.beans.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author jesus
 */
@WebServlet(name = "Admin", urlPatterns = {"/Admin"})
public class Admin extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String donde = request.getParameter("donde");

        DAOFactory daoF = DAOFactory.getDAOFactory();
        switch (donde) {
            case "updateBloqueado":
                IUsuariosDAO uDAO = daoF.getUsuariosDAO();
                Usuario usuario = null;
                for (Usuario usuarioFor : (List<Usuario>) request.getSession().getAttribute("listaUsuarios")) {
                    if (Integer.parseInt(request.getParameter("idUsuario")) == usuarioFor.getIdUsuario()) {
                        usuario = usuarioFor;
                        break;
                    }
                }
                if (usuario != null) {
                    switch (usuario.getBloqueado()) {
                        case "n":
                            usuario.setBloqueado("s");
                            break;
                        case "s":
                            usuario.setBloqueado("n");
                            break;
                        default:
                            System.out.println("Switch cambiando el atributo bloqueado del usuario");
                    }
                    uDAO.updateBloqueado(usuario);
                }
                break;
            case "nuevaCat":
                StringBuilder mensaje = new StringBuilder();
                Categoria nuevaCategoria = new Categoria();
                Caracteristica nuevaCarac;
                nuevaCategoria.setCaracteristicas(new ArrayList());

                Pattern patron;
                Matcher encaja;

                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                FileItem imagenCat = null;

                for (Object item : items) {
                    FileItem uploaded = (FileItem) item;
                    if (!uploaded.isFormField()) {
                        if (uploaded.getContentType().equals("image/png") || uploaded.getContentType().equals("image/jpeg")) {
                            if (uploaded.getName().length() <= 40) {
                                imagenCat = uploaded;
                                nuevaCategoria.setImagen(uploaded.getName());

                            } else {
                                mensaje.append("El nombre de los archivos no puede sobrepasar los 35 carácteres");
                            }
                        } else {
                            mensaje.append("El archivo introducido no es una imagen jpeg o png");

                        }
                    } else {
                        switch (uploaded.getFieldName()) {
                            case "categoria":
                                patron = Pattern.compile("^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,100}$");
                                encaja = patron.matcher(uploaded.getString("UTF-8"));
                                if (!encaja.matches()) {
                                    mensaje.append("<p>Error en la categoria</p>");
                                } else {
                                    nuevaCategoria.setDenominacion(uploaded.getString("UTF-8"));
                                }
                                break;
                            case "carac":
                                patron = Pattern.compile("^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,50}$");
                                encaja = patron.matcher(uploaded.getString("UTF-8"));
                                if (!encaja.matches()) {
                                    mensaje.append("<p>Error en la característica</p>");
                                } else {
                                    nuevaCarac = new Caracteristica();
                                    nuevaCarac.setDenominacion(uploaded.getString("UTF-8"));
                                    nuevaCategoria.getCaracteristicas().add(nuevaCarac);
                                }
                                break;
                            default:
                                System.out.println("ESTO NO DEBERIA VERSE :D");
                                break;
                        }
                    }
                }
                if (mensaje.toString().equals("")) {
                    ICategoriasDAO catDAO = daoF.getCategoriasDAO();
                    if (nuevaCategoria.getImagen() == null) {
                        nuevaCategoria.setImagen("default");
                    }
                    int idCategoria = catDAO.setCategoria(nuevaCategoria);
                    if (idCategoria != 0) {
                        if (imagenCat != null) {
                            File fichero = new File(request.getServletContext().getRealPath("/img/categorias/"), imagenCat.getName());
                            imagenCat.write(fichero);
                        }
                        nuevaCategoria.setIdCategoria(idCategoria);
                        ICaracteristicasDAO carDAO = daoF.getCaracteristicasDAO();
                        if (carDAO.setCaracterísticas(nuevaCategoria)) {
                            List<Categoria> categorias = catDAO.getCategoriasYCaracs();
                            request.getServletContext().setAttribute("categorias", categorias);
                            mensaje.append("Categoría y característicias insertadas correctamente");
                            request.setAttribute("status", "success");
                        } else {
                            mensaje.append("Error interno al insertar características");
                            request.setAttribute("status", "danger");
                        }
                    } else {
                        mensaje.append("Error interno al insertar Categoria");
                        request.setAttribute("status", "danger");
                    }

                } else {
                    request.setAttribute("status", "danger");
                }
                request.setAttribute("mensaje", mensaje);
                request.getRequestDispatcher("/jsp/admin.jsp").forward(request, response);

                break;
            default:
                System.out.println("SWITCH DE ADMIN");
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
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
