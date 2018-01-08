/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jesushm.DAOs;

import es.jesushm.beans.Articulo;
import es.jesushm.beans.CatYCar;
import es.jesushm.modelo.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class CaracYArtDAO implements ICaracYArtDAO {

    @Override
    public boolean setCaracYart(Articulo articulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        conexion = ConnectionFactory.getConnection();
        String sql = "insert into caracyart values(null,?,?,?)";
        for (CatYCar catValor : articulo.getCaracteristicas()) {
            try {
                preparada = conexion.prepareStatement(sql);
                preparada.setInt(1, articulo.getIdArticulo());
                preparada.setInt(2, catValor.getIdCaracteristica());
                preparada.setString(3, catValor.getValor());
                preparada.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Mal introduciendo CaracYArtDAO");
                return false;
            } finally {
                Utilidades.cerrarPSyRSyS(preparada, null, null);
            }
        }
        ConnectionFactory.closeConnection();
        return true;
    }

    @Override
    public List<CatYCar> getCaracYArt(int idArticulo) {
        Connection conexion;
        PreparedStatement preparada = null;
        ResultSet resultado = null;
        List<CatYCar> catYCars = new ArrayList();
        CatYCar catYCar;
        conexion = ConnectionFactory.getConnection();
        String sql = "select * from caracyart where idArticulo = ?";
        try {
            preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, idArticulo);
            resultado = preparada.executeQuery();
            while (resultado.next()) {
                catYCar = new CatYCar();
//                catYCar.setId(resultado.getInt("id"));
//                catYCar.setIdArticulo(resultado.getInt("idArticulo"));
                catYCar.setIdCaracteristica(resultado.getInt("idCaracteristica"));
                catYCar.setValor(resultado.getString("valor"));
                catYCars.add(catYCar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArticulosDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Mal introduciendo CaracYArtDAO");
        } finally {
            Utilidades.cerrarPSyRSyS(preparada, resultado, null);
            ConnectionFactory.closeConnection();
        }
        return catYCars;
    }
}
