package es.jesushm.DAOs;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {

    static DataSource ds = null;
    static Connection conexion = null;
    static final String DATASOURCE_NAME = "java:comp/env/jdbc/SubastaME";
        
    /**
     *
     * @return Object Connection con una conexión de la base de datos
     */
    public static Connection getConnection(){
        try {
            Context contextoInicial = new InitialContext();
            ds = (DataSource) contextoInicial.lookup(DATASOURCE_NAME);
            conexion = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }
        return conexion;
    }
    
    /**
     * Cierra una conexión
     */
    public static void closeConnection(){
        try {
            conexion.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
