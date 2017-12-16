/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Cliente;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jesus
 */
public class ClientesDAO implements IClientesDAO {

    @Override
    public boolean setCliente(Cliente cliente) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into clientes values(?,?,?,?,?,?,?,'sin.jpg')";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, cliente.getIdCliente());
            preparada.setString(2, cliente.getNombre());
            preparada.setString(3, cliente.getApellido1());
            preparada.setString(4, cliente.getApellido2());
            preparada.setString(5, cliente.getNif());
            preparada.setString(6, cliente.getDireccion());
            preparada.setString(7, cliente.getTelefono());
            preparada.execute();
        } catch (SQLException ex) {
            System.out.println("setCliente - ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return true;
    }

    @Override
    public boolean updateCliente(Cliente cliente) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "update clientes set nombre = ?, apellido1 = ?, apellido2 = ?, nif = ?, direccion = ?, telefono = ? where idCliente = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, cliente.getNombre());
            preparada.setString(2, cliente.getApellido1());
            preparada.setString(3, cliente.getApellido2());
            preparada.setString(4, cliente.getNif());
            preparada.setString(5, cliente.getDireccion());
            preparada.setString(6, cliente.getTelefono());
            preparada.setInt(7, cliente.getIdCliente());
            preparada.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("UpdateCliente - ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, null, null);
            ConnectionFactory.closeConnection();
        }
        return true;
    }

    @Override
    public boolean updateAvatar(Cliente cliente) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "update clientes set avatar = ? where idCliente = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, cliente.getAvatar());
            preparada.setInt(2, cliente.getIdCliente());
            preparada.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("UpdateAvatar - ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, null, null);
            ConnectionFactory.closeConnection();
        }
        return true;
    }
}
