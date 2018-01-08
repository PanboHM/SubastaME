/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Caracteristica;
import es.jesushm.beans.Categoria;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jesus
 */
public class CaracteristicasDAO implements ICaracteristicasDAO {

    @Override
    public boolean setCaracterísticas(Categoria categoria) {
        Connection conexion = ConnectionFactory.getConnection();
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        boolean correcto = false;
        String sql = "INSERT INTO caracteristicas VALUES(null,?,?)";
        try {
            for (Caracteristica caracFor : categoria.getCaracteristicas()) {
                preparada = conexion.prepareStatement(sql);
                preparada.setInt(1, categoria.getIdCategoria());
                preparada.setString(2, caracFor.getDenominacion());
                preparada.execute();
            }
            correcto = true;
        } catch (SQLException ex) {
            System.out.println("ErrorCode: " + ex.getErrorCode() + " - SQLState: " + ex.getSQLState() + " - Message: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return correcto;
    }

}
