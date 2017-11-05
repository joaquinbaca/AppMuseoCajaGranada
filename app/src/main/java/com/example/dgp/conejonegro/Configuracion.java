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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


public class Configuracion extends AppCompatActivity {

    private String idioma = " ";
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        spinner = (Spinner) findViewById(R.id.spIdioma);

        String[] items = new String[] { "Español", "Ingles", "Frances", "Italiano" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
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

        Button btn = (Button)findViewById(R.id.botonConfig);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardar en la clase de contenidos persistentes
                crearConfiguracion();
                startActivity(new Intent(Configuracion.this, Principal.class));
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
}