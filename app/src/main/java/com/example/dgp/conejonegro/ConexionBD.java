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
    private String ip = "webappmuseo.ddns.net";
    private String puerto = "3306";
    private String nombreBD = "Museo";
    private String user = "usuario";
    private String password = "123456";

    public  ConexionBD() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/" + nombreBD, user, password);
    }

    public ResultSet hacerConsulta(String consulta) throws java.sql.SQLException {
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(consulta);
        return rs;
    }

    public void hacerUpdate(String consulta) throws java.sql.SQLException {
        Statement stmt = conexion.createStatement();
        stmt.executeUpdate(consulta);
    }

    public void cerrarBasedeDatos() throws java.sql.SQLException {
        conexion.close();
    }

}
