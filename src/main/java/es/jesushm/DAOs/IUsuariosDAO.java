/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Usuario;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IUsuariosDAO {
    
    /**
     * 
     * @param usuario Datos del Usuario a insertar en la base de datos
     * @return el idUsuario generado al insertar el Usuario en la base de datos, devuelve -1 si no ha sido posible la insercción
     */
    public int setUsuario(Usuario usuario);
    
    /**
     *
     * @param usuario contiene email y password
     * @return Object Usuario si ha sido satisfactorio, si no devuelve null.
     */
    public Usuario login(Usuario usuario);
    
    /**
     * Comprueba la existencia de un email en la base de datos
     * @param email recibe un email de usuario
     * @return devuelve false si no se encuentra y true si está en la base de datos
     */
    public boolean comprobarEmail(String email);
    
    /**
     *
     * @param usuario
     * @return Devuelve true si la contraseña ha sido actualiza y false si no.
     */
    public boolean updatePassword(Usuario usuario);
    
    /**
     * Actualiza la fecha de último acceso del Usuario en cuestión
     * @param usuario recibe idUsuario y ultimoAcceso
     */
    public void updateUltimoAcceso(Usuario usuario);
    
    /**
     * Devuelve todos los usuarios almacenados en la base de datos
     * 
     * @return ArrayList con todos los usuarios
     */
    public List<Usuario> getUsuarios();
    
    /**
     * Bloquea o desbloquea un usuario
     * 
     * @param usuario usuario a bloquear/desbloquear
     * @return the boolean
     */
    public boolean updateBloqueado(Usuario usuario);
}
