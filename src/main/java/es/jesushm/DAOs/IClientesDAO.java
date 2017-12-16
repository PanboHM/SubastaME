/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Cliente;

/**
 *
 * @author jesus
 */
public interface IClientesDAO {

    /**
     *
     * @param cliente Recibe un Cliente con datos a insertar en la base de datos
     * @return true si ha sido insertado correctamente o false sino.
     */
    public boolean setCliente(Cliente cliente);

    /**
     *
     * @param cliente recibe un Cliente con datos a actualizar en la base de datos
     * @return true si se ha actualizado correctamente o false sino.
     */
    public boolean updateCliente(Cliente cliente);
    
    /**
     *
     * @param cliente recibe un Cliente(solo hace falta idCliente y avatar)
     * @return true si se ha actualizado el avatar correctamente y si no false
     */
    public boolean updateAvatar(Cliente cliente);
}
