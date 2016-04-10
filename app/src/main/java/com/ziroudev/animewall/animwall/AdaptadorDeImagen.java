package com.ziroudev.animewall.animwall;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Rekky on 25/03/2016.
 */
public class AdaptadorDeImagen extends BaseAdapter{
    private Context context;
    private Imagen[] urls;

    public AdaptadorDeImagen(Context context, Imagen[] urls){
        this.context = context;
        this.urls = urls;
        Log.d("hola", "adapter:"+urls[0].getIdDrawable());
    }

    @Override
    public int getCount() {return urls.length;}

    @Override
    public Imagen getItem(int position) {
        return urls[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenCoche = (ImageView) view.findViewById(R.id.imagen_coche);
        TextView nombreCoche = (TextView) view.findViewById(R.id.nombre_coche);

        final Imagen item = getItem(position);
        Glide.with(context)
            .load(urls[position].getIdDrawable())
            .into(imagenCoche);

        nombreCoche.setText(item.getNombre());

        return view;
    }
}
