/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Cliente;
import es.jesushm.beans.Usuario;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jesus
 */
public class UsuariosDAO implements IUsuariosDAO {

    @Override
    public int setUsuario(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        int idGenerado;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into usuarios(email,password,ultimoAcceso) values(?,SHA(?),?)";
        try {
            preparada = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparada.setString(1, usuario.getEmail());
            preparada.setString(2, usuario.getPassword());
            Timestamp sqlDate = new Timestamp(usuario.getUltimoAcceso().getTime());
            preparada.setTimestamp(3, sqlDate);
            preparada.executeUpdate();
            resultado = preparada.getGeneratedKeys();
            resultado.next();
            idGenerado = resultado.getInt(1);
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return -1;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return idGenerado;
    }

    @Override
    public Usuario login(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        Cliente cliente = new Cliente();
        String sql = "select * from usuarios inner join clientes on idUsuario = idCliente where email = ? and password = SHA(?)";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, usuario.getEmail());
            preparada.setString(2, usuario.getPassword());
            resultado = preparada.executeQuery();
            resultado.next();
            cliente.setIdCliente(resultado.getInt("idCliente"));
            cliente.setNombre(resultado.getString("nombre"));
            cliente.setApellido1(resultado.getString("apellido1"));
            cliente.setApellido2(resultado.getString("apellido2"));
            cliente.setNif(resultado.getString("nif"));
            cliente.setDireccion(resultado.getString("direccion"));
            cliente.setTelefono(resultado.getString("telefono"));
            cliente.setAvatar(resultado.getString("avatar"));
            usuario.setClienteU(cliente);
            usuario.setIdUsuario(resultado.getInt("idUsuario"));
            usuario.setBloqueado(resultado.getString("bloqueado"));
            usuario.setTipoAcceso(resultado.getString("tipoAcceso"));
            usuario.setUltimoAcceso(resultado.getTimestamp("ultimoAcceso"));
        } catch (SQLException ex) {
            System.out.println("Login - ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return null;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return usuario;
    }

    @Override
    public boolean comprobarEmail(String email) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "select email from usuarios where email = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, email);
            resultado = preparada.executeQuery();
            resultado.next();
            resultado.getString("email");
        } catch (SQLException ex) {
            System.out.println("ComprobarEmail - ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return true;
    }

    @Override
    public boolean updatePassword(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "update usuarios set password = SHA(?) where idUsuario = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setString(1, usuario.getPassword());
            preparada.setInt(2, usuario.getIdUsuario());
            preparada.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            return false;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, null, null);
            ConnectionFactory.closeConnection();
        }
        return true;
    }

    @Override
    public void updateUltimoAcceso(Usuario usuario) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "update usuarios set ultimoAcceso = ? where idUsuario = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            Timestamp sqlDate = new Timestamp(usuario.getUltimoAcceso().getTime());
            preparada.setTimestamp(1, sqlDate);
            preparada.setInt(2, usuario.getIdUsuario());
            preparada.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, null, null);
            ConnectionFactory.closeConnection();
        }
    }
}
