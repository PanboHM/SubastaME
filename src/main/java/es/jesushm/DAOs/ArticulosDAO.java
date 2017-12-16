/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        int idGenerado;
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
            return -1;
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado,null);
            ConnectionFactory.closeConnection();
        }
        return idGenerado;
    }

}
