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
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvaro on 30/11/2017.
 */

public class SalaAdapter extends ArrayAdapter<Sala>{

    public SalaAdapter(Context context, ArrayList<Sala> objects) {
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
                    R.layout.list_item_sala,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView foto = (ImageView) convertView.findViewById(R.id.sala_foto);
        TextView nombre = (TextView) convertView.findViewById(R.id.sala_nombre);
        TextView descripcion = (TextView) convertView.findViewById(R.id.sala_descripcion);

        // Lead actual.
        Sala sala = getItem(position);

        // Setup.
        //Glide.with(getContext()).load(sala.getImage()).into(foto);
        nombre.setText(sala.getNombre());
        descripcion.setText(sala.getDescripcion());

        URL imageUrl = null;
        try {
            imageUrl = new URL(sala.getImagen());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            Bitmap loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            foto.setImageBitmap(loadedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
