/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.modelo;

import es.jesushm.beans.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jesus
 */
public class Utilidades {

    /**
     *
     * @param usuario contiene datos del usuario a comprobar
     * @return devuelve un String con los fallos o "" si no hay ningun fallo
     */
    public static String comprobarDatosUsuario(Usuario usuario) {
        StringBuilder mensajeError = new StringBuilder();
        Pattern patron;
        Matcher encaja;
//        Comprobación de email
        if (usuario.getEmail() != null) {
            patron = Pattern.compile("^[a-z0-9][a-z0-9._%+-]*@[a-z0-9.-]+\\.[a-z]{2,4}$");
            encaja = patron.matcher(usuario.getEmail());
            if (!encaja.matches()) {
                mensajeError.append("<p>Email incorrecto</p>");
            }
        }
//                Comprobacion de nombre
        patron = Pattern.compile("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{0,8}[\\s]?){1,2}$");
        encaja = patron.matcher(usuario.getClienteU().getNombre());
        if (!encaja.matches()) {
            mensajeError.append("<p>Formato de nombre no válido</p>");
        }
//                Comprobacion de primer apellido
        patron = Pattern.compile("^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú ]{0,19}$");
        encaja = patron.matcher(usuario.getClienteU().getApellido1());
        if (!encaja.matches()) {
            mensajeError.append("<p>Formato de primer apellido no válido</p>");
        }
//                Comprobacion de DNI
        patron = Pattern.compile("^[\\d]{8}[TRWAGMYFPDXBNJZSQVHLCKET]{1}$");
        encaja = patron.matcher(usuario.getClienteU().getNif());
        if (!encaja.matches()) {
            mensajeError.append("<p>DNI incorrecto.</p>");
        }
//                Comprobacion de Direccion
        patron = Pattern.compile("^[A-ZÁÉÍÓÚa-zñáéíóú0-9 ]{1,45}$");
        encaja = patron.matcher(usuario.getClienteU().getDireccion());
        if (!encaja.matches()) {
            mensajeError.append("<p>Dirección no válida</p>");
        }

//              Comprobacion de segundo apellido si no está vacio
        if (!usuario.getClienteU().getApellido2().trim().equals("")) {
            patron = Pattern.compile("^[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú ]{0,19}$");
            encaja = patron.matcher(usuario.getClienteU().getApellido2());
            if (!encaja.matches()) {
                mensajeError.append("<p>Formato de segundo apellido no válido.</p>");
            }
        }
//                Comprobacion de telefono si no está vacio
        if (!usuario.getClienteU().getTelefono().trim().equals("")) {
            patron = Pattern.compile("^[9|6|7]{1}[\\d]{8}$");
            encaja = patron.matcher(usuario.getClienteU().getTelefono());
            if (!encaja.matches()) {
                mensajeError.append("<p>Teléfono no válido</p>");
            }
        }
        return mensajeError.toString();
    }

    /**
     * Método que se usa en todos los DAO´s para cerrar objetos sql
     *
     * @param preparada PreparedStatement que cerrar
     * @param resultado ResulSet que cerrar
     * @param sentencia Statement que cerrar
     */
    public static void cerrarPSyRSyS(PreparedStatement preparada, ResultSet resultado, Statement sentencia) {
        try {
            if (preparada != null) {
                preparada.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultado != null) {
                resultado.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (sentencia != null) {
                sentencia.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
