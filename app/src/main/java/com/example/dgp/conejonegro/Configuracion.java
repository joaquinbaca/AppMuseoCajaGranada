package com.example.dgp.conejonegro;

/**
 * Created by alexr on 04/11/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Configuracion extends AppCompatActivity {

    private String idioma = " ";
    private Spinner spinner;
    ConexionBD conexion = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        spinner = (Spinner) findViewById(R.id.spIdioma);
        ArrayList<String> idiomas = new ArrayList<String>();

        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM idiomas");
            while(rs.next()){
                idiomas.add(rs.getString("idioma"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idiomas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idioma = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        comprobarConfiguracion();

        Button btn = (Button)findViewById(R.id.config);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar en la clase de contenidos persistentes
                crearConfiguracion();

                try {
                    actualizarIdioma();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(Configuracion.this, Principal.class));
                try {
                    conexion.cerrarBasedeDatos();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    private void crearConfiguracion(){
        SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean subtitulos = ((CheckBox)findViewById(R.id.Subtitulos)).isChecked();
        boolean lengSimple = ((CheckBox)findViewById(R.id.lenguajeSimple)).isChecked();
        boolean lengSignos = ((CheckBox)findViewById(R.id.lenguajeSignos)).isChecked();
        boolean sonido = ((CheckBox)findViewById(R.id.Sonido)).isChecked();
        SharedPreferences.Editor editor = config.edit();
        editor.putString("idioma", idioma);
        editor.putBoolean("subtitulos", subtitulos);
        editor.putBoolean("lenguajeSimple", lengSimple);
        editor.putBoolean("lenguajeSignos", lengSignos);
        editor.putBoolean("sonido", sonido);
        editor.commit();
        Log.d("Sonido", idioma);
        Usuario.initInstance(idioma,subtitulos,lengSimple,lengSignos,sonido);
    }

    private void comprobarConfiguracion(){
        if(Usuario.getInstance()!=null) {
            Usuario usuario = Usuario.getInstance();
            idioma = usuario.getIdioma();
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(usuario.getIdioma());
            spinner.setSelection(spinnerPosition);
            ((CheckBox)findViewById(R.id.Subtitulos)).setChecked(usuario.getSubtitulos());
            ((CheckBox)findViewById(R.id.lenguajeSimple)).setChecked(usuario.getLenguajeSimple());
            ((CheckBox)findViewById(R.id.lenguajeSignos)).setChecked(usuario.getLenguajeSignos());
            ((CheckBox)findViewById(R.id.Sonido)).setChecked(usuario.getSonido());
        }
    }

    public void actualizarIdioma() throws SQLException, ClassNotFoundException {

        SharedPreferences config=getSharedPreferences("traducciones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = config.edit();

        ConexionBD conexion = null;
        conexion = new ConexionBD();
        Log.d("HOLAXD-1", idioma);
        Log.d("consulta", "SELECT  Sitio, "+idioma+" FROM traducciones");
        ResultSet rs = conexion.hacerConsulta("SELECT  Sitio, "+idioma+" FROM traducciones");

        while(rs.next()){
            editor.putString(rs.getString("Sitio"), rs.getString(idioma));
            Log.d("HOLAXD", rs.getString("Sitio"));
            Log.d("HOLAXD", rs.getString(idioma));
        }
        editor.commit();
    }
}