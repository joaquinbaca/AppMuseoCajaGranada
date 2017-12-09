package com.example.dgp.conejonegro;

/**
 * Created by alexr on 04/11/2017.
 */
import android.app.ProgressDialog;
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
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Configuracion extends AppCompatActivity {

    private String idioma = " ";
    private Spinner spinner;
    ConexionBD conexion = null;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog= new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        actualizarPantalla();

        spinner = (Spinner) findViewById(R.id.spIdioma);
        ArrayList<String> idiomas = new ArrayList<String>();

        try {
            conexion = new ConexionBD();
            ResultSet rs = conexion.hacerConsulta("SELECT * FROM IDIOMA");
            while(rs.next()){
                idiomas.add(rs.getString("nombre"));
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
                try {
                    actualizarIdioma(conexion);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                actualizarPantalla();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        comprobarConfiguracion();

        Button btn = (Button)findViewById(R.id.configuracionBoton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar en la clase de contenidos persistentes
                crearConfiguracion();


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

    public void actualizarIdioma(ConexionBD conexion) throws SQLException, ClassNotFoundException {


        SharedPreferences config=getSharedPreferences("traducciones", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = config.edit();
        ResultSet rs = conexion.hacerConsulta("SELECT  idIdioma FROM IDIOMA WHERE nombre = '" + idioma + "'" );
        rs.next();
        int id_idioma = rs.getInt("idIdioma");
        rs = conexion.hacerConsulta("SELECT  nombreElemento, traduccion FROM INTERFAZANDROID WHERE idIdioma = '" + id_idioma + "'");

        while(rs.next()){
            editor.putString(rs.getString("nombreElemento"), rs.getString("traduccion"));
        }
        editor.commit();

    }

    public void actualizarPantalla(){
        SharedPreferences config=getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        TextView mTextView = (TextView)findViewById(R.id.configuracionTextoIdioma);
        mTextView.setText(config.getString("configuracionTextoIdioma", "Idioma"));

        TextView mTextView2 = (TextView)findViewById(R.id.configuracionTextoSubtitulos);
        mTextView2.setText(config.getString("configuracionTextoSubtitulos", "Activar subtítulos"));

        TextView mTextView3 = (TextView)findViewById(R.id.configuracionTextoLenguajeSimple);
        mTextView3.setText(config.getString("configuracionTextoLenguajeSimple", "Activar lenguaje simple"));

        TextView mTextView4 = (TextView)findViewById(R.id.configuracionTextoLenguajeSignos);
        mTextView4.setText(config.getString("configuracionTextoLenguajeSignos", "Activar lenguaje de signos"));

        TextView mTextView5 = (TextView)findViewById(R.id.configuracionTextoSonido);
        mTextView5.setText(config.getString("configuracionTextoSonido", "Activar sonido"));

        Button button = (Button)findViewById(R.id.configuracionBoton);
        button.setText(config.getString("configuracionBoton", "VOLVER"));
    }
}