/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Caracteristica;
import es.jesushm.beans.Categoria;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class CategoriasDAO implements ICategoriasDAO {

    @Override
    public List<Categoria> getCategorias() {
        Connection conexion;
        Statement sentencia = null;
        ResultSet resultado = null;
        List<Categoria> categoriasYCaracteristicas = new ArrayList();
        List<Caracteristica> caracteristicas = new ArrayList();
        Categoria categoria = new Categoria();
        Caracteristica caracteristica;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from categorias inner join caracteristicas using (idCategoria)";
        int idSeparador = 0;
        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            while (resultado.next()) {
                if (resultado.getInt("idCategoria") > idSeparador) {
                    if (!resultado.isFirst()) {
                        categoria.setCaracteristicas(caracteristicas);
                        categoriasYCaracteristicas.add(categoria);
                    }
                    idSeparador = resultado.getInt("idCategoria");
                    categoria = new Categoria();
                    categoria.setIdCategoria(idSeparador);
                    categoria.setDenominacion(resultado.getString("denominacion"));
                    categoria.setImagen(resultado.getString("imagen"));
                    caracteristicas = new ArrayList();
                }
                caracteristica = new Caracteristica();
                caracteristica.setIdCaracteristica(resultado.getInt("idCaracteristica"));
                caracteristica.setDenominacion(resultado.getString("caracteristicas.denominacion"));
                caracteristicas.add(caracteristica);
                if (resultado.isLast()) {
                    categoria.setCaracteristicas(caracteristicas);
                    categoriasYCaracteristicas.add(categoria);
                }

            }
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            cerrarSyRS(sentencia, resultado);
            ConnectionFactory.closeConnection();
        }
        return categoriasYCaracteristicas;
    }

    private void cerrarSyRS(Statement sentencia, ResultSet resultado) {
        try {
            if (sentencia != null) {
                sentencia.close();
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
}
