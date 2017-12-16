/*
 * Cabecera de licencia
 */
package es.jesushm.controllers;

import es.jesushm.DAOFactory.DAOFactory;
import es.jesushm.DAOs.IClientesDAO;
import es.jesushm.DAOs.IUsuariosDAO;
import es.jesushm.beans.Cliente;
import es.jesushm.beans.Usuario;
import es.jesushm.modelo.Utilidades;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet que según el parámetro "controlPanel" hará:
 * Si es null cambiará el avatar
 * Si es "cambiarPassword" cambiará el password del usuario
 * Si es "cambiarDatos" cambiará los datos del usuario
 * @author jesus
 */
@WebServlet(name = "ControlPanel", urlPatterns = {"/ControlPanel"})
public class ControlPanel extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * Recibe las peticiones de panelDeControl.jsp y cambiará los datos del Cliente, su contraseña o su avatar.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws org.apache.commons.fileupload.FileUploadException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String donde = request.getParameter("controlPanel");
        ServletContext sc = getServletContext();
        HttpSession sesion = request.getSession();
        DAOFactory daoF = DAOFactory.getDAOFactory();
        IUsuariosDAO uDAO = daoF.getUsuariosDAO();
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");;
        Usuario usuarioTemp;
        StringBuilder mensajeError = new StringBuilder();
        if (donde != null) {
            switch (donde) {
                case "cambiarPassword":
                    usuarioTemp = new Usuario();

                    String passwordActual = request.getParameter("passwordActual");
                    String passwordNew = request.getParameter("passwordNew");
                    String passwordNewRepeat = request.getParameter("passwordNew");

                    if (passwordNew.equals("") || passwordNewRepeat.equals("")) {
                        request.setAttribute("status", "danger");
                        request.setAttribute("mensaje", "No puede dejar la nueva contraseña en blanco");
                    } else {
                        if (passwordActual.equals(usuario.getPassword())) {
                            if (passwordNew.equals(passwordNewRepeat)) {
                                usuarioTemp.setIdUsuario(usuario.getIdUsuario());
                                usuarioTemp.setPassword(passwordNew);

                                if (uDAO.updatePassword(usuarioTemp)) {
                                    request.setAttribute("status", "success");
                                    request.setAttribute("mensaje", "¡Contraseña acutalizada correctamente!");
                                    usuario.setPassword(passwordNew);
                                } else {
                                    request.setAttribute("status", "danger");
                                    request.setAttribute("mensaje", "Error interno al actualizar la contraseña");
                                }
                            } else {
                                mensajeError.append("Las nuevas contraseñas no coinciden");
                                request.setAttribute("status", "danger");
                                request.setAttribute("mensaje", mensajeError);
                            }
                        } else {
                            mensajeError.append("La contraseña actual no es correcta");
                            request.setAttribute("status", "danger");
                            request.setAttribute("mensaje", mensajeError);
                        }
                    }
                    break;
                case "cambiarDatos":
                    Cliente cliente = new Cliente();
                    try {
                        cliente = (Cliente) BeanUtils.cloneBean(usuario.getClienteU());
                        BeanUtils.populate(cliente, request.getParameterMap());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException ex) {
                        Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    usuarioTemp = new Usuario();
                    usuarioTemp.setClienteU(cliente);
                    mensajeError.append(Utilidades.comprobarDatosUsuario(usuarioTemp));
                    if (mensajeError.toString().equals("")) {
                        IClientesDAO cDAO = daoF.getClientesDAO();
                        cliente.setIdCliente(usuario.getClienteU().getIdCliente());
                        if (cDAO.updateCliente(cliente)) {
                            request.setAttribute("status", "success");
                            request.setAttribute("mensaje", "¡Datos actualizados con éxito!");
                            usuario.setClienteU(cliente);
                        } else {
                            request.setAttribute("status", "danger");
                            request.setAttribute("mensaje", "Error interno al actualizar los datos");
                        }
                    } else {
                        request.setAttribute("status", "danger");
                        request.setAttribute("mensaje", mensajeError);
                    }
                    break;

                default:
                    System.out.println("SWITCH DE CONTROLPANEL");
                    break;
            }
        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

// Los items obtenidos serán cada uno de los campos del formulario, tanto campos normales como ficheros subidos.
            List items = upload.parseRequest(request);

            FileItem avatar = null;
            File fichero;
// Se recorren todos los items, que son de tipo FileItem
            for (Object item : items) {
                FileItem uploaded = (FileItem) item;

                // Hay que comprobar si es un campo de formulario. Si no lo es, se guarda el fichero subido donde nos interese
                if (!uploaded.isFormField()) { // No es campo de formulario, guardamos el fichero en algún sitio
                    if (uploaded.getContentType().equals("image/png") || uploaded.getContentType().equals("image/jpeg")) {
                        avatar = uploaded;
                    } else {
                        request.setAttribute("status", "danger");
                        request.setAttribute("mensaje", "El archivo introducido no es una imagen jpeg/jpg o png");
                    }
                }
            }
            if (avatar != null) {
                String nombre = String.valueOf(usuario.getIdUsuario()) + "_" + avatar.getName();
                fichero = new File(sc.getRealPath("/img/subidas/avatar/"), nombre);
                Cliente clienteTemp = (Cliente) BeanUtils.cloneBean(usuario.getClienteU());
                clienteTemp.setAvatar(nombre);
                IClientesDAO cDAO = daoF.getClientesDAO();
                if (cDAO.updateAvatar(clienteTemp)) {
                    avatar.write(fichero);
                    request.setAttribute("status", "success");
                    request.setAttribute("mensaje", "¡Avatar actualizado!");
                    usuario.setClienteU(clienteTemp);
                } else {
                    request.setAttribute("status", "danger");
                    request.setAttribute("mensaje", "Error interno al actualizar el avatar.");
                }
            } else {
                request.setAttribute("status", "danger");
                request.setAttribute("mensaje", "No has introducido ninguna imagen");
            }
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
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
