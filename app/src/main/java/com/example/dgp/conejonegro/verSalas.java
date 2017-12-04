package com.example.dgp.conejonegro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.Array;
import java.sql.ResultSet;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listasalas);

       /* list = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        listItems.add("hola");
        adapter.notifyDataSetChanged();

        listItems.add("alex");
        adapter.notifyDataSetChanged();
        */
        Button btn = (Button)findViewById(R.id.listaSalasMenu);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verSalas.this, Principal.class));
                try {
                    conexion.cerrarBasedeDatos();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        salas = new ArrayList<Sala>();
        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM SALA");
            //String descripcion = rs.getString("texto");
            ArrayList<Elemento> elementos= new ArrayList<Elemento>();
            while(rs.next()) {
                int planta = rs.getInt("planta");
                String idZona = rs.getString("idZona");
                ResultSet rs2 = conexion.hacerConsulta("SELECT * FROM ZONA WHERE idZona='"+idZona+"'");
                rs2.next();
                String imagen = rs2.getString("imagen");
                String nombre = rs2.getString("nombre");

                ResultSet rs4 = conexion.hacerConsulta("SELECT * FROM `DESCRIPCION-ZONA` WHERE idZona='"+idZona+"'");
                /*listItems.add(nombre);
                adapter.notifyDataSetChanged();
                */
                String descripcion="";
                while(rs4.next()) {
                    descripcion = rs4.getString("texto");
                }
                ResultSet rs1 = conexion.hacerConsulta("SELECT * FROM `ZONA-ELEMENTO` WHERE idZona='"+idZona+"'");
                while(rs1.next()){
                    String idElemento = rs1.getString("idElemento");
                    ResultSet rsel = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+idElemento+"'");
                    while(rsel.next()){
                        String nombreElemento = rsel.getString("nombre");
                        ResultSet rsdesc = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='"+idElemento+"'");
                        String descripcionElemento="";
                        while(rsdesc.next()) {
                            descripcionElemento = rsdesc.getString("texto");
                        }
                        ResultSet rsmedio = conexion.hacerConsulta("SELECT * FROM MEDIO WHERE idElemento='"+idElemento+"'");
                        String idMedio;
                        String url_foto;
                        if(rsmedio.isBeforeFirst()) {
                            rsmedio.next();
                            idMedio = rsmedio.getString("idmedio");
                            ResultSet rsfoto = conexion.hacerConsulta("SELECT * FROM FOTO WHERE idElemento='"+idMedio+"'");
                            rsfoto.next();
                            url_foto = rsfoto.getString("url");
                        }
                        else{
                            url_foto = "http://webappmuseo.ddns.net:8742/images/noimage.png";
                        }
                        Elemento e = new Elemento(nombreElemento, descripcionElemento, url_foto, idElemento);
                        elementos.add(e);
                    }
                }
                s = new Sala(elementos, planta, nombre, descripcion, imagen, idZona);
                salas.add(s);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        museo.setSalas(salas);
        SalasFragment salasFragment = (SalasFragment) getSupportFragmentManager().findFragmentById(R.id.salas_container);
        if (salasFragment == null) {
            salasFragment = SalasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.salas_container, salasFragment).commit();
        }

    }
}
