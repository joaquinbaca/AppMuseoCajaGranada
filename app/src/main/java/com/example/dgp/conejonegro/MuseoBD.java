package com.example.dgp.conejonegro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by alexr on 05/11/2017.
 */

public class MuseoBD {

    private Connection conexion;

    public MuseoBD() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conectarBasedeDatos();
    }
    
    public void conectarBasedeDatos() throws ClassNotFoundException, java.sql.SQLException {
        conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.82/museo", "root", "root");
    }

    public void cerrarBasedeDatos() throws java.sql.SQLException {
        conexion.close();
    }

    public ResultSet hacerConsulta(String consulta) throws java.sql.SQLException {
        Statement stmt = conexion.createStatement();
        return stmt.executeQuery(consulta);
    }
}
