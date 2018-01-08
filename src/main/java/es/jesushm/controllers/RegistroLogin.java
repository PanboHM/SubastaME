/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.IArticulosDAO;
import es.jesushm.DAOs.IClientesDAO;
import es.jesushm.DAOs.IFotografiasDAO;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.beans.Articulo;
import es.jesushm.beans.Cliente;
import es.jesushm.beans.Usuario;
import es.jesushm.modelo.Utilidades;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * Registra, logea o comprueba el email en la base de datos Si está registrando,
 * comprobará datos, si están correctos llamará al DAO para insertarlos en la
 * base de datos, sino devuelve un mensaje de error Si está logeando, llamará a
 * un DAO que comprobará email y password en la base de datos, si está correcto
 * obtendremos todos los datos del usuario si no, mensaje de error Una llamada
 * de AJAX activará la llamada a un DAO para comprobar si el email está en la
 * base de datos
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
        String donde = request.getParameter("enviar");
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IUsuariosDAO uDAO = daoF.getUsuariosDAO();
        Pattern patron;
        Matcher encaja;
        StringBuilder mensajeError = new StringBuilder();
        HttpSession session = request.getSession();
        Usuario usuario = new Usuario();
        Date ultimoAcceso = new Date();
        if (donde != null) {
            switch (donde) {
                case "registro":
                    //parte de setUsuario
                    Cliente cliente = new Cliente();
                    try {
                        BeanUtils.populate(cliente, request.getParameterMap());
                        BeanUtils.populate(usuario, request.getParameterMap());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    usuario.setClienteU(cliente);
                    mensajeError.append(Utilidades.comprobarDatosUsuario(usuario));

                    //              Comprobacion de contraseñas iguales
                    if (!request.getParameter("password").equals(request.getParameter("passwordA"))) {
                        mensajeError.append("<p>Las contraseñas no son iguales</p>");
                    }
                    //              Comprobacion de segundo apellido vacio
                    if (usuario.getClienteU().getApellido2().trim().equals("")) {
                        usuario.getClienteU().setApellido2(null);
                    }
                    //              Comprobacion de telefono vacio
                    if (usuario.getClienteU().getTelefono().trim().equals("")) {
                        usuario.getClienteU().setTelefono(null);
                    }
                    if (mensajeError.toString().equals("")) {
                        usuario.setUltimoAcceso(ultimoAcceso);
                        int idGenerado = uDAO.setUsuario(usuario);
                        usuario.getClienteU().setIdCliente(idGenerado);
                        boolean registroCorrecto = false;
                        if (idGenerado != -1) {
                            IClientesDAO cDAO = daoF.getClientesDAO();
                            registroCorrecto = cDAO.setCliente(usuario.getClienteU());
                        }
                        if (registroCorrecto) {
                            request.setAttribute("mensaje", "¡Usuario creado correctamente, ya puedes entrar con tu email y contraseña!");
                            request.setAttribute("status", "success");
                        } else {
                            request.setAttribute("mensaje", "Ha ocurrido un problema interno al registrate, vuelve a intentarlo más tarde");
                            request.setAttribute("status", "danger");
                        }
                        request.getRequestDispatcher("/jsp/mensaje.jsp").forward(request, response);
                    } else {
                        request.setAttribute("usuario", usuario);
                        request.setAttribute("mensajeError", mensajeError);
                        request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
                    }
                    break;

                case "login":
                    //PARTE DEL LOGIN
                    try {
                        BeanUtils.populate(usuario, request.getParameterMap());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(RegistroLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }       //Comprobacion de email
                    patron = Pattern.compile("^[a-z0-9][a-z0-9._%+-]*@[a-z0-9.-]+\\.[a-z]{2,4}$");
                    encaja = patron.matcher(usuario.getEmail());
                    if (!encaja.matches()) {
                        mensajeError.append("<p>Email no válido.</p>");
                    }
                    if (mensajeError.toString().equals("")) {
                        usuario = uDAO.login(usuario);
                        if (usuario != null) {
                            if (usuario.getBloqueado().equals("s")) {
                                mensajeError.append("<p>Su usuario está bloqueado y no puede acceder.</p>");
                                request.setAttribute("mensajeError", mensajeError);
                                request.setAttribute("email", request.getParameter("email"));
                                request.getRequestDispatcher("/index.jsp").forward(request, response);
                            } else {
                                session.setAttribute("usuario", usuario);
                                Usuario usuario2 = new Usuario();
                                usuario2.setIdUsuario(usuario.getIdUsuario());
                                usuario2.setUltimoAcceso(ultimoAcceso);
                                uDAO.updateUltimoAcceso(usuario2);
                                if (usuario.getTipoAcceso().equals("u")) {
                                    List<Articulo> subastasRecientes;
                                    IArticulosDAO aDAO = daoF.getArticulosDAO();
                                    IFotografiasDAO fDAO = daoF.getFotografiasDAO();
                                    subastasRecientes = aDAO.getArticulosUsuario(" where fechaInicio < now() order by fechaInicio desc limit 4");
                                    for (Articulo artFor : subastasRecientes) {
                                        artFor.setFotografias(fDAO.getFotografias(artFor.getIdArticulo()));
                                    }
                                    request.getServletContext().setAttribute("recientes", subastasRecientes);
                                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                                } else {
                                    List<Usuario> usuarios = uDAO.getUsuarios();
                                    Usuario usuarioTemp = null;
                                    for (Usuario usuarioFor : usuarios) {
                                        if (usuarioFor.getIdUsuario() == usuario.getIdUsuario()) {
                                            usuarioTemp = usuarioFor;
                                            break;
                                        }
                                    }
                                    usuarios.remove(usuarioTemp);
                                    session.setAttribute("listaUsuarios", usuarios);
                                    request.getRequestDispatcher("/jsp/admin.jsp").forward(request, response);
                                }
                            }
                        } else {
                            mensajeError.append("<p>La combinación usuario/contraseña no existe.</p>");
                            request.setAttribute("mensajeError", mensajeError);
                            request.setAttribute("email", request.getParameter("email"));
                            request.getRequestDispatcher("/index.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("mensajeError", mensajeError);
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                    }
                    break;

                case "logout":
                    session.removeAttribute("usuario");
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                    break;
                default:
                    System.out.println("SWITCH de registroLogin");
                    break;
            }
        } else if (request.getParameter("datoEmail") != null) { //AJAX que comprueba si el email está ocupado en el momento del setUsuario
            Boolean existe = uDAO.comprobarEmail(request.getParameter("datoEmail"));
            response.setContentType("text/html;charset=UTF-8"); //Se ha de indicar el tipo de respuesta, OBLIGATORIO, aunque parezca que no
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
