package com.example.dgp.conejonegro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
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

        SalasFragment leadsFragment = (SalasFragment) getSupportFragmentManager().findFragmentById(R.id.salas_container);

        if (leadsFragment == null) {
            leadsFragment = SalasFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.salas_container, leadsFragment).commit();
        }


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

                ResultSet rs4 = conexion.hacerConsulta("SELECT * FROM `DESCIPCION-ZONA` WHERE idZona='"+idZona+"'");
                /*listItems.add(nombre);
                adapter.notifyDataSetChanged();
                */
                rs4.next();
                String descripcion = rs4.getString("texto");
                ResultSet rs1 = conexion.hacerConsulta("SELECT * FROM `ZONA-ELEMENTO` WHERE idZona='"+idZona+"'");
                while(rs1.next()){
                    String idElemento = rs1.getString("idElemento");
                    ResultSet rsel = conexion.hacerConsulta("SELECT * FROM ELEMENTO WHERE idElemento='"+idElemento+"'");
                    while(rsel.next()){
                        String nombreElemento = rsel.getString("nombre");
                        ResultSet rsdesc = conexion.hacerConsulta("SELECT * FROM DESCRIPCION WHERE idElemento='"+idElemento+"'");
                        String descripcionElemento = rsdesc.getString("texto");
                        Elemento e = new Elemento(nombreElemento, descripcionElemento);
                        elementos.add(e);
                    }
                }
                s = new Sala(elementos, planta, nombre, descripcion, imagen);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        salas.add(s);
    }
}
