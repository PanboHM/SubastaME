/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.beans.Fotografia;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class FotografiasDAO implements IFotografiasDAO {

    @Override
    public boolean setFotografias(Articulo articulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into fotografias values(null,?,?)";
        for (Fotografia foto : articulo.getFotografias()) {
            try {
                preparada = conexion.prepareStatement(sql);
                preparada.setInt(1, articulo.getIdArticulo());
                preparada.setString(2, foto.getFotografia());
                preparada.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Mal introduciendo FotografiasDAO");
                return false;
            } finally {
                Utilidades.cerrarPSyRSyS(preparada, null, null);
            }
        }
        ConnectionFactory.closeConnection();
        return true;
    }
}
