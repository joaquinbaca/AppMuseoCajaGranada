package com.example.dgp.conejonegro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class RutaAdapter extends ArrayAdapter<Ruta> {

    public RutaAdapter(Context context, ArrayList<Ruta> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_ruta,
                    parent,
                    false);
        }

        // Referencias UI.
        TextView nombre = (TextView) convertView.findViewById(R.id.ruta_nombre);
        TextView descripcion = (TextView) convertView.findViewById(R.id.ruta_descripcion);

        // Lead actual.
        Ruta ruta = getItem(position);

        // Setup.
        //Glide.with(getContext()).load(sala.getImage()).into(foto);
        nombre.setText(ruta.getNombre());
        descripcion.setText(ruta.getDescripcion());


        return convertView;
    }
}
