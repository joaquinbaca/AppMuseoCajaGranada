package com.example.dgp.conejonegro;

/**
 * Created by alexr on 04/11/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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


public class Configuracion extends AppCompatActivity implements Runnable{

    private String idioma = " ";
    private Spinner spinner;
    ConexionBD conexion = null;
    private ProgressDialog progressDialog;
    private int tipoHebra;
    private ArrayList<String> idiomas;
    private ArrayAdapter<String> adapter;
    private Button botonListaSalas;
    private Button botonQR;
    private Button botonRutas;
    private Button botonConfig;
    private Class objetivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        actualizarPantalla();

        spinner = (Spinner) findViewById(R.id.spIdioma);
        idiomas = new ArrayList<String>();

        progressDialog = ProgressDialog.show(this, "Cargando", "Descargando idiomas", true,
                false);

        tipoHebra = 1;
        Thread thread = new Thread(this);
        thread.start();
    }

    private void crearConfiguracion(){
        SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean lengSimple = ((CheckBox)findViewById(R.id.lenguajeSimple)).isChecked();
        boolean lengSignos = ((CheckBox)findViewById(R.id.lenguajeSignos)).isChecked();
        SharedPreferences.Editor editor = config.edit();
        editor.putString("idioma", idioma);
        editor.putBoolean("lenguajeSimple", lengSimple);
        editor.putBoolean("lenguajeSignos", lengSignos);
        editor.commit();
        Log.d("Sonido", idioma);
        Usuario.initInstance(idioma,lengSimple,lengSignos);
    }

    private void comprobarConfiguracion(){
        if(Usuario.getInstance()!=null) {
            Usuario usuario = Usuario.getInstance();
            idioma = usuario.getIdioma();
            ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
            int spinnerPosition = myAdap.getPosition(usuario.getIdioma());
            spinner.setSelection(spinnerPosition);
            ((CheckBox)findViewById(R.id.lenguajeSimple)).setChecked(usuario.getLenguajeSimple());
            ((CheckBox)findViewById(R.id.lenguajeSignos)).setChecked(usuario.getLenguajeSignos());
        }
    }

    public void actualizarIdioma() throws SQLException, ClassNotFoundException {

        Log.d("idioma",idioma);
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

        TextView mTextView3 = (TextView)findViewById(R.id.configuracionTextoLenguajeSimple);
        mTextView3.setText(config.getString("configuracionTextoLenguajeSimple", "Activar lenguaje simple"));

        TextView mTextView4 = (TextView)findViewById(R.id.configuracionTextoLenguajeSignos);
        mTextView4.setText(config.getString("configuracionTextoLenguajeSignos", "Activar lenguaje de signos"));

        traducirBotones();
    }

    public void traducirBotones(){
        SharedPreferences config = getSharedPreferences("traducciones", Context.MODE_PRIVATE);

        botonListaSalas = (Button)findViewById(R.id.salaBotonSalas);

        botonQR = (Button)findViewById(R.id.salaBotonQR);

        botonRutas = (Button)findViewById(R.id.salasBotonRutas);

        botonConfig = (Button)findViewById(R.id.salasconfiguracionBoton);

        TextView mTextView = (TextView)findViewById(R.id.principalTexto);
        mTextView.setText(config.getString("principalTexto", "Configuración"));

        TextView mTextView2 = (TextView)findViewById(R.id.principalTextoQR);
        mTextView2.setText(config.getString("principalBotonQR", "Escanear QR"));

        TextView mTextView3 = (TextView)findViewById(R.id.principalTextoRutas);
        mTextView3.setText(config.getString("principalBotonRutas", "Ver Rutas"));

        TextView mTextView4 = (TextView)findViewById(R.id.principalTextoSalas);
        mTextView4.setText(config.getString("principalBotonSalas", "Ver Salas"));

    }


    @Override
    public void run() {
        switch(tipoHebra){
            case 1:
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
                break;
            case 2:
                try {
                    actualizarIdioma();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                crearConfiguracion();
                SharedPreferences config=getSharedPreferences("config", Context.MODE_PRIVATE);
                boolean lengSimple = config.getBoolean("lenguajeSimple", false);
                boolean lengSignos = config.getBoolean("lenguajeSignos", false);
                Museo.getInstance(idioma, lengSimple, lengSignos);
                break;
        }
        handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(tipoHebra){
                case 1:
                    despuesDeCargar();
                    break;
                case 2:
                    actualizarPantalla();
                    break;
                case 3:
                    startActivity(new Intent(Configuracion.this, objetivo));
                    finish();
                    break;
            }
            progressDialog.dismiss();
        }
    };

    private void despuesDeCargar(){

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idiomas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idioma = parent.getItemAtPosition(position).toString();


                progressDialog = ProgressDialog.show(Configuracion.this, "Cargando", "Aplicando idioma", true,
                        false);

                tipoHebra = 2;
                Thread thread = new Thread(Configuracion.this);
                thread.start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonListaSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(Configuracion.this, "Cargando", "Descargando archivos del museo", true,
                        false);

                tipoHebra = 3;
                objetivo = verSalas.class;
                Thread thread = new Thread(Configuracion.this);
                thread.start();
            }
        });

        botonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(Configuracion.this, "Cargando", "Descargando archivos del museo", true,
                        false);

                tipoHebra = 3;
                objetivo = lector.class;
                Thread thread = new Thread(Configuracion.this);
                thread.start();
            }
        });

        botonRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Principal.this, Configuracion.class));
                //finish();
            }
        });


        comprobarConfiguracion();

    }

}