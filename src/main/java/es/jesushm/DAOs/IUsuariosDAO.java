/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Usuario;

/**
 *
 * @author jesus
 */
public interface IUsuariosDAO {
    
    public boolean registro(Usuario usuario);
    
    public Usuario login(Usuario usuario);
    
    public boolean comprobarEmail(String email);
}
