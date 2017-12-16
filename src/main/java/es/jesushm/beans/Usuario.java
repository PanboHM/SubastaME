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

    private int idUsuario;
    private String email;
    private String password;
    private Date ultimoAcceso;
    private Cliente clienteU;
    private String tipoAcceso;
    private String bloqueado;
    private int valorPositivas;
    private int valorNegativas;

    public String getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public String getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getValorPositivas() {
        return valorPositivas;
    }

    public void setValorPositivas(int valorPositivas) {
        this.valorPositivas = valorPositivas;
    }

    public int getValorNegativas() {
        return valorNegativas;
    }

    public void setValorNegativas(int valorNegativas) {
        this.valorNegativas = valorNegativas;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
