/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.beans.Cliente;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class ArticulosDAO implements IArticulosDAO {

    @Override
    public int setArticulo(Articulo articulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into articulos values(null,?,?,?,?,?,?,?)";
        int idGenerado = -1;
        try {
            preparada = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparada.setString(1, articulo.getDescripcionCorta());
            preparada.setString(2, articulo.getDescripcion());
            preparada.setInt(3, articulo.getIdCategoria());
            preparada.setInt(4, articulo.getCliente().getIdCliente());
            Timestamp sqlDate = new Timestamp(articulo.getFechaInicio().getTime());
            preparada.setTimestamp(5, sqlDate);
            sqlDate = new Timestamp(articulo.getFechaFin().getTime());
            preparada.setTimestamp(6, sqlDate);
            preparada.setDouble(7, articulo.getImporteSalida());
            preparada.execute();
            resultado = preparada.getGeneratedKeys();
            resultado.next();
            idGenerado = resultado.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Mal introduciendo un ArticuloDAO");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return idGenerado;
    }

    @Override
    public List<Articulo> getCatalogoSubastas(int idCategoria) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        List<Articulo> articulos = new ArrayList();
        Articulo articulo;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from articulos where idCategoria = ? and fechaInicio<=now() and fechaFin>now()";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idCategoria);
            resultado = preparada.executeQuery();
            while (resultado.next()) {
                articulo = new Articulo();
                articulo.setIdArticulo(resultado.getInt("idArticulo"));
                articulo.setDescripcionCorta(resultado.getString("descripcionCorta"));
                articulo.setFechaFin(resultado.getTimestamp("fechaFin"));
                articulos.add(articulo);
                System.out.println("Articulo " + articulo.getDescripcionCorta() + "con fecha fin: " + articulo.getFechaFin());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Se ve que no hay articulos en esa categoria");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return articulos;
    }

    @Override
    public Articulo getArticulo(int idArticulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        Articulo articulo = null;
        Cliente cliente;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from articulos a inner join clientes c on a.idCliente=c.idCliente where idArticulo = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idArticulo);
            resultado = preparada.executeQuery();
            if (resultado.next()) {
                articulo = new Articulo();
                articulo.setIdArticulo(resultado.getInt("idArticulo"));
                articulo.setDescripcionCorta(resultado.getString("descripcionCorta"));
                articulo.setDescripcion(resultado.getString("descripcion"));
                articulo.setIdCategoria(resultado.getInt("idCategoria"));
//            articulo.setFechaInicio(resultado.getDate("fechaInicio"));
                articulo.setFechaFin(resultado.getTimestamp("fechaFin"));
                articulo.setImporteSalida(resultado.getDouble("importeSalida"));
                cliente = new Cliente();
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido1(resultado.getString("apellido1"));
                cliente.setApellido2(resultado.getString("apellido2"));
                cliente.setAvatar(resultado.getString("avatar"));
                articulo.setCliente(cliente);
                System.out.println("getArticulo: " + articulo.getDescripcionCorta() + " obtenido, con idCliente: " + cliente.getIdCliente());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Se ve que no hay articulos en esa categoria");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return articulo;
    }

    @Override
    public List<Articulo> getArticulosUsuario(String where) {
        Connection conexion;
        Statement sentencia = null;
        ResultSet resultado = null;
        List<Articulo> misArticulos = new ArrayList();
        Articulo articulo;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from articulos" + where;
        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            while (resultado.next()) {
                articulo = new Articulo();
                articulo.setIdArticulo(resultado.getInt("idArticulo"));
                articulo.setDescripcionCorta(resultado.getString("descripcionCorta"));
//                articulo.setDescripcion(resultado.getString("descripcion"));
                articulo.setIdCategoria(resultado.getInt("idCategoria"));
//            articulo.setFechaInicio(resultado.getDate("fechaInicio"));
//                articulo.setFechaFin(resultado.getTimestamp("fechaFin"));
                articulo.setImporteSalida(resultado.getDouble("importeSalida"));
                misArticulos.add(articulo);
            }
//            System.out.println("getArticulo: " + articulo.getDescripcionCorta() + " obtenido, con idCliente: " + cliente.getIdCliente());
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en getArticulosUsuario");
        } finally {
            Utilidades.cerrarPSyRSyS(null, resultado, sentencia);
            ConnectionFactory.closeConnection();
        }
        return misArticulos;
    }
}
