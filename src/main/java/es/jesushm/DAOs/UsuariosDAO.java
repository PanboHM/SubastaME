/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Cliente;
import es.jesushm.beans.Usuario;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jesus
 */
public class UsuariosDAO implements IUsuariosDAO {

    @Override
    public boolean registro(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        int idGenerado;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into usuarios(email,password,ultimoAcceso) values(?,SHA(?),?)";
        try {
            preparada = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparada.setString(1, usuario.getEmail());
            preparada.setString(2, usuario.getPassword());
            Timestamp sqlDate = new Timestamp(usuario.getUltimoAcceso().getTime());
            preparada.setTimestamp(3, sqlDate);
            preparada.executeUpdate();
            resultado = preparada.getGeneratedKeys();
            resultado.next();
            idGenerado = resultado.getInt(1);
            sql = "insert into clientes values(?,?,?,?,?,?,?,null,null)";
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idGenerado);
            preparada.setString(2, usuario.getClienteU().getNombre());
            preparada.setString(3, usuario.getClienteU().getApellido1());
            preparada.setString(4, usuario.getClienteU().getApellido2());
            preparada.setString(5, usuario.getClienteU().getNif());
            preparada.setString(6, usuario.getClienteU().getDireccion());
            preparada.setString(7, usuario.getClienteU().getTelefono());
            preparada.execute();
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (preparada != null) {
                    preparada.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        ConnectionFactory.closeConnection();
        return true;
    }

    @Override
    public Usuario login(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        Cliente cliente = new Cliente();
        String sql = "select * from usuarios natural join clientes where email=? and password=SHA(?)";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, usuario.getEmail());
            preparada.setString(2, usuario.getPassword());
            resultado = preparada.executeQuery();
            resultado.next();
            cliente.setNombre(resultado.getString("nombre"));
            cliente.setApellido1(resultado.getString("apellido1"));
            cliente.setApellido2(resultado.getString("apellido2"));
            cliente.setNif(resultado.getString("nif"));
            cliente.setDireccion(resultado.getString("direccion"));
            cliente.setTelefono(resultado.getString("telefono"));
            usuario.setClienteU(cliente);
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return null;
        } finally {
            try {
                if (preparada != null) {
                    preparada.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        ConnectionFactory.closeConnection();
        return usuario;
    }

    @Override
    public boolean comprobarEmail(String email) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from usuarios where email = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, email);
            resultado = preparada.executeQuery();
            resultado.next();
            resultado.getString("email");
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (preparada != null) {
                    preparada.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                if (resultado != null) {
                    resultado.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        ConnectionFactory.closeConnection();
        return true;
    }

}
