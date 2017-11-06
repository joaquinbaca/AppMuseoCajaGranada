package com.example.dgp.conejonegro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alvaro on 06/11/2017.
 */

public class ConexionBD {

    private Connection conexion;

    public  ConexionBD() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.129:3307/museo", "root", "root");
    }

    public ResultSet hacerConsulta(String consulta) throws java.sql.SQLException {
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);
        return rs;
    }

    public void cerrarBasedeDatos() throws java.sql.SQLException {
        conexion.close();
    }

}
