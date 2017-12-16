/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jesus
 */
public class Articulo implements Serializable {

    private int idArticulo;
    private String descripcion;
    private String descripcionCorta;
    private int idCategoria;
    private Cliente cliente;
    private Date fechaInicio;
    private Date fechaFin;
    private double importeSalida;
    private List<Fotografia> fotografias;
    private List<CatYCar> caracteristicas;

    public List<CatYCar> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CatYCar> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getImporteSalida() {
        return importeSalida;
    }

    public void setImporteSalida(double importeSalida) {
        this.importeSalida = importeSalida;
    }

    public List<Fotografia> getFotografias() {
        return fotografias;
    }

    public void setFotografias(List<Fotografia> fotografias) {
        this.fotografias = fotografias;
    }

}
