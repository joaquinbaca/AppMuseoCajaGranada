package com.example.dgp.conejonegro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexr on 25/11/2017.
 */

public class verSalas extends AppCompatActivity {
    private ArrayList<Sala> salas;
    ConexionBD conexion = null;
    Sala s;
    Museo museo = Museo.getInstance();

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView list;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog= new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listasalas);

        Button btn1 = (Button)findViewById(R.id.salaBotonQR);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verSalas.this, lector.class));
                finish();
            }
        });

        Button btn2 = (Button)findViewById(R.id.salasconfiguracionBoton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verSalas.this, Configuracion.class));
                finish();
            }
        });



        //Comprabamos si se ha rellenado antes el singleton de museo para no volverlo ha hacer
        if(!museo.getCompleto()) {
            salas = new ArrayList<Sala>();
            try {
                conexion = new ConexionBD();
                ResultSet rsZona = conexion.hacerConsulta("SELECT * FROM ZONA");

                while (rsZona.next()) {
                    String idZona = rsZona.getString("idZona");
                    String nombre = rsZona.getString("nombre");
                    String imagen = rsZona.getString("imagen");
                    String descripcionSala = "";
                    int planta = 0;
                    ArrayList<Elemento> elementos = new ArrayList<Elemento>();

                    ResultSet rsSala = conexion.hacerConsulta("SELECT * FROM SALA WHERE idZona='" + idZona + "'");
                    if (rsSala.isBeforeFirst()) {
                        rsSala.next();
                        planta = rsSala.getInt("planta");
                    }

                    ResultSet rsDescripcionSala = conexion.hacerConsulta("SELECT * FROM `DESCRIPCION-ZONA` WHERE idZona='" + idZona + "'");
                    if (rsDescripcionSala.isBeforeFirst()) {
                        rsDescripcionSala.next();
                        descripcionSala = rsDescripcionSala.getString("texto");
                    }

                    ResultSet rsZonaElemento = conexion.hacerConsulta("SELECT * FROM ZONA_ELEMENTO WHERE idZona='" + idZona + "'");
                    if (rsZonaElemento.isBeforeFirst()) {
                        while (rsZonaElemento.next()) {
                            String idElemento = rsZonaElemento.getString("idElemento");
                            String nombreElemento = "";
                            String descripcionElemento = "";
                            String url_foto = "http://webappmuseo.ddns.net:8742/images/noimage.png";

                            ResultSet rsElemento = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='" + idElemento + "'");
                            if (rsElemento.isBeforeFirst()) {
                                while (rsElemento.next()) {
                                    nombreElemento = rsElemento.getString("nombre");

                                    ResultSet rsDescripcion = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='" + idElemento + "'");
                                    if (rsDescripcion.isBeforeFirst()) {
                                        rsDescripcion.next();
                                        descripcionElemento = rsDescripcion.getString("texto");
                                    }

                                    ResultSet rsMedio = conexion.hacerConsulta("SELECT * FROM MEDIO WHERE idElemento='" + idElemento + "'");
                                    if (rsMedio.isBeforeFirst()) {
                                        rsMedio.next();
                                        String idMedio = rsMedio.getString("idmedio");

                                        ResultSet rsFoto = conexion.hacerConsulta("SELECT * FROM FOTO WHERE idMedio='" + idMedio + "'");
                                        if (rsFoto.isBeforeFirst()) {
                                            rsFoto.next();
                                            url_foto = rsFoto.getString("url");
                                        }
                                    }
                                }
                            }
                            Elemento e = new Elemento(nombreElemento, descripcionElemento, url_foto, idElemento);
                            elementos.add(e);
                        }
                    }
                    s = new Sala(elementos, planta, nombre, descripcionSala, imagen, idZona);
                    salas.add(s);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            try {
                conexion.cerrarBasedeDatos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            museo.setSalas(salas);
            museo.setCompleto(true);
        }
        SalasFragment salasFragment = (SalasFragment) getSupportFragmentManager().findFragmentById(R.id.salas_container);
        if (salasFragment == null) {
            salasFragment = SalasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.salas_container, salasFragment).commit();
        }

    }
}
