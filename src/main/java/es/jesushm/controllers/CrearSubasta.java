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
import es.jesushm.beans.CatYCar;
import es.jesushm.beans.Fotografia;
import es.jesushm.beans.Usuario;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author jesus
 */
@MultipartConfig
@WebServlet(name = "CrearSubasta", urlPatterns = {"/CrearSubasta"})
public class CrearSubasta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Crea un articulo/subasta en la base de datos
     * Primero recoge datos del formulari y los comprueba, de ir todo bien los inserta en la base de datos, si sale mal devuelve un  mensaje de error.
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
        ServletContext sc = getServletContext();
        DAOFactory daoF = DAOFactory.getDAOFactory();
        StringBuilder mensajeError = new StringBuilder();

        //usuario cargado para obtener el idCliente
        HttpSession sesion = request.getSession(true);
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        Articulo articulo = new Articulo();

        Calendar fechaInicio = GregorianCalendar.getInstance();
        Calendar fechaFin = GregorianCalendar.getInstance();
        Calendar fechaAhora = GregorianCalendar.getInstance();
        fechaAhora.add(GregorianCalendar.MINUTE, -5);
        String[] minutoss;
        int minutos;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        List<FileItem> imagenes = new ArrayList();
        boolean primeraImagenMal = true;
        List<Fotografia> fotografias = new ArrayList();
        Fotografia foto;

        List<CatYCar> caracteristicas = new ArrayList();
        CatYCar caracteristica;
        boolean CaracMal = false;

        Pattern patron;
        Matcher encaja;

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

// Los items obtenidos serán cada uno de los campos del formulario, tanto campos normales como ficheros subidos.
        List items = upload.parseRequest(request);

// Se recorren todos los items, que son de tipo FileItem
        for (Object item : items) {
            FileItem uploaded = (FileItem) item;

            // Hay que comprobar si es un campo de formulario. Si no lo es, se guarda el fichero subido donde nos interese
            if (!uploaded.isFormField()) { // No es campo de formulario, guardamos el fichero en algún sitio
                if (uploaded.getContentType().equals("image/png") || uploaded.getContentType().equals("image/jpeg")) {
                    //guardo las imagenes en un arrayList de FileItem porque no las voy a escribir en el servidor hasta comprobar que todo esté correcto
                    imagenes.add(uploaded);
                } else {
                    if (primeraImagenMal) {
                        mensajeError.append("Alguno de los archivos introducidos no es una imagen jpeg o png");
                        primeraImagenMal = false;
                    }
                }
            } else {
                // es un campo de formulario, podemos obtener clave y valor
                if (uploaded.getFieldName().startsWith("carac")) {
                    //si ya había alguna característica no completada, no comprobamos ninguna mas
                    if (!CaracMal) {
                        if (uploaded.getString("UTF-8").trim().equals("")) {
                            mensajeError.append("La características del producto no pueden estar vacías");
                            CaracMal = true;

                        } else {
                            caracteristica = new CatYCar();
                            String idCaracteristica = uploaded.getFieldName().replace("carac", "");
                            caracteristica.setIdCaracteristica(Integer.parseInt(idCaracteristica));
                            caracteristica.setValor(uploaded.getString("UTF-8").trim());
                            caracteristicas.add(caracteristica);
                        }
                    }
                } else {
                    switch (uploaded.getFieldName()) {
                        case "fechaInicio":
                            fechaInicio.setTime(format.parse(uploaded.getString("UTF-8")));
                            break;
                        case "horaInicio":
                            minutoss = uploaded.getString("UTF-8").split(":");
                            minutos = (Integer.parseInt(minutoss[0]) * 60) + Integer.parseInt(minutoss[1]);
                            fechaInicio.add(GregorianCalendar.MINUTE, minutos);
                            if (fechaInicio.after(fechaAhora)) {
                                articulo.setFechaInicio(fechaInicio.getTime());
                            } else {
                                mensajeError.append("<p>La fecha de inicio tiene que ser superior a la actual</p>");
                            }
                            break;
                        case "fechaFin":
                            fechaFin.setTime(format.parse(uploaded.getString("UTF-8")));
                            break;
                        case "horaFin":
                            minutoss = uploaded.getString("UTF-8").split(":");
                            minutos = (Integer.parseInt(minutoss[0]) * 60) + Integer.parseInt(minutoss[1]);
                            fechaFin.add(GregorianCalendar.MINUTE, minutos);
                            if (fechaFin.after(fechaInicio)) {
                                articulo.setFechaFin(fechaFin.getTime());
                            } else {
                                mensajeError.append("<p>La fecha de fin no puede ser la misma o inferior a la de inicio</p>");
                            }
                            break;
                        case "descripcionCorta":
                            patron = Pattern.compile("^[A-ZÑÁÉÍÓÚa-zñáéíóú0-9 ]{1,100}$");
                            encaja = patron.matcher(uploaded.getString("UTF-8"));
                            if (!encaja.matches()) {
                                mensajeError.append("<p>Error en la descripción corta</p>");
                            } else {
                                articulo.setDescripcionCorta(uploaded.getString("UTF-8"));
                            }
                            break;
                        case "descripcion":
                            if (!"".equals(uploaded.getString("UTF-8").trim())) {
                                articulo.setDescripcion(uploaded.getString("UTF-8"));
                            }
                            break;
                        case "idCategoria":
                            if ("nada".equals(uploaded.getString("UTF-8"))) {
                                mensajeError.append("<p>Seleccione una categoría, por favor.</p>");
                            } else {
                                articulo.setIdCategoria(Integer.parseInt(uploaded.getString("UTF-8")));
                            }
                            break;
                        case "importeSalida":
                            patron = Pattern.compile("^[0-9]{1,6}([,][0-9]{1,2})?$");
                            encaja = patron.matcher(uploaded.getString("UTF-8"));
                            if (!encaja.matches()) {
                                mensajeError.append("<p>El importe de salida no es correcto</p>");
                            } else {
                                articulo.setImporteSalida(Double.parseDouble(uploaded.getString("UTF-8").replace(",", ".")));
                            }
                            break;
                        default:
                            System.out.println("ESTO NO DEBERIA VERSE :D switch de parametros");
                            break;
                    }
                }
            }
        }

        if (imagenes.isEmpty()) {
            mensajeError.append("<p>Inserte al menos una imagen</p>");
        }

        if (mensajeError.toString().equals("")) {
            articulo.setCliente(usuario.getClienteU());

            IArticulosDAO aDAO = daoF.getArticulosDAO();
            int idArt = aDAO.setArticulo(articulo);
            //si el articulo se ha escrito correctamente en la base de datos seguimos.
            if (idArt != -1) {
                articulo.setIdArticulo(idArt);

                articulo.setCaracteristicas(caracteristicas);
                ICaracYArtDAO cyaDAO = daoF.getCaracYArtDAO();
                if (cyaDAO.setCaracYart(articulo)) {
                    //ahora vamos a escribir las imagenes en el servidor, creando primero una carpeta para guardar las imagenes del articulo.
                    File carpeta = new File(sc.getRealPath("/img/subidas/articulos/" + articulo.getIdArticulo() + "/"));
                    carpeta.mkdirs();
                    File fichero;
                    for (FileItem imagen : imagenes) {
                        fichero = new File(sc.getRealPath("/img/subidas/articulos/" + articulo.getIdArticulo() + "/"), imagen.getName());
                        imagen.write(fichero);
                        foto = new Fotografia();
                        foto.setFotografia(imagen.getName());
                        fotografias.add(foto);
                    }

                    articulo.setFotografias(fotografias);
                    IFotografiasDAO fotoDAO = daoF.getFotografiasDAO();
                    if (!fotoDAO.setFotografias(articulo)) {
                        mensajeError.append("Error interno al crear la subasta");
                    } else {
                        request.setAttribute("status", "success");
                        request.setAttribute("mensaje", "Subasta correctamente creada");
                    }

                } else {
                    mensajeError.append("Error interno al crear la subasta");
                }
            } else {
                mensajeError.append("Error interno al crear la subasta");
            }
            if (mensajeError.toString().equals("")) {
                request.getRequestDispatcher("/jsp/mensaje.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/crearSubasta.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("articulo", articulo);
            request.setAttribute("mensajeError", mensajeError);
            request.getRequestDispatcher("/jsp/crearSubasta.jsp").forward(request, response);
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
            Logger.getLogger(CrearSubasta.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CrearSubasta.class.getName()).log(Level.SEVERE, null, ex);
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
