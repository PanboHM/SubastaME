/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.beans;

import java.io.Serializable;

/**
 *
 * @author jesus
 */
public class Cliente implements Serializable {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String nif;
    private String direccion;
    private String telefono;
    private int valorPositivas;
    private int valorNegativas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.apellido1 = Apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.apellido2 = Apellido2;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

}
