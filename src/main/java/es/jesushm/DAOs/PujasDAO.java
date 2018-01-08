/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Puja;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class PujasDAO implements IPujasDAO {

    @Override
    public Puja getUltimaPuja(int idArticulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        Puja puja = new Puja();
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from pujas where idArticulo = ? order by fecha desc limit 1";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idArticulo);
            resultado = preparada.executeQuery();
            if (resultado.next()) {
                puja.setFecha(resultado.getTimestamp("fecha"));
                puja.setIdArticulo(resultado.getInt("idArticulo"));
                puja.setIdCliente(resultado.getInt("idCliente"));
                puja.setImporte(resultado.getDouble("importe"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Mal obteniendo la última puja");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return puja;
    }

    @Override
    public List<Puja> getPujas(String where) {
        Connection conexion;
        Statement sentencia = null;
        ResultSet resultado = null;
        List<Puja> pujas = new ArrayList();
        Puja puja;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from pujas" + where + " order by fecha desc";
        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            resultado.next();
            do {
                puja = new Puja();
                puja.setFecha(resultado.getTimestamp("fecha"));
                puja.setIdArticulo(resultado.getInt("idArticulo"));
                puja.setIdCliente(resultado.getInt("idCliente"));
                puja.setImporte(resultado.getDouble("importe"));
                pujas.add(puja);
            } while (resultado.next());
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Errror en getPujas");
        } finally {
            Utilidades.cerrarPSyRSyS(null, resultado, sentencia);
            ConnectionFactory.closeConnection();
        }
        return pujas;
    }

    @Override
    public boolean setPuja(Puja puja) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        boolean correcto = false;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into pujas values(?,?,now(),?)";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, puja.getIdCliente());
            preparada.setInt(2, puja.getIdArticulo());
//            Timestamp sqlDate = new Timestamp(puja.getFecha().getTime());
//            sentencia.setTimestamp(3, sqlDate);
            preparada.setDouble(3, puja.getImporte());
            preparada.execute();
            correcto = true;
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Mal estableciendo una puja");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return correcto;
    }

}
