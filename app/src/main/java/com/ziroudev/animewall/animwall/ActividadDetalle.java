package com.ziroudev.animewall.animwall;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.IOException;


public class ActividadDetalle extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "com.ziroudev.extra.ID";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private ImageView imagenExtendida;
    private String URLPasada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtener el Imagen con el identificador establecido en la actividad principallll
        imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);
        URLPasada = getIntent().getStringExtra(EXTRA_PARAM_ID);
        cargarImagenExtendida();


//        BUTTON FLOATING
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Wallpaper Añadido", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                ponerWallpaper();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    public void cargarImagenExtendida() {
        Picasso.with(getApplicationContext())
                .load(getIntent().getStringExtra(EXTRA_PARAM_ID))
                .placeholder(R.drawable.ic_menu_camera)
                .into(imagenExtendida);
    }

    public void ponerWallpaper(){
        //cargo la imagen usando picasso
        Picasso.with(getApplicationContext()).load(URLPasada).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap result = bitmap;
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wallpaperManager.setBitmap(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("test", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("test", "Prepare Load");
            }
        });

    }


    }
