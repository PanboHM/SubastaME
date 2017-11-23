/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jesus
 */
public class Usuario implements Serializable {

    private int usuario;
    private String email;
    private String password;
    private Date ultimoAcceso;
    private Cliente clienteU;

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Cliente getClienteU() {
        return clienteU;
    }

    public void setClienteU(Cliente clienteU) {
        this.clienteU = clienteU;
    }

}
