package com.ziroudev.animewall.animwall;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class ActividadDetalle extends AppCompatActivity {

    public static final String EXTRA_PARAM_IMG = "com.ziroudev.extra.ID";
    public static final String EXTRA_PARAM_NOM = "com.ziroudev.extra.NOM";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private ImageView imagenExtendida;
    private String URLPasada;
    private String NOMPasada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtener el Imagen con el identificador establecido en la actividad principallll
        imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);
        URLPasada = getIntent().getStringExtra(EXTRA_PARAM_IMG);
        NOMPasada = getIntent().getStringExtra(EXTRA_PARAM_NOM);
        cargarImagenExtendida();


        //BUTTON FLOATING 1
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Wallpaper Añadido", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                ponerWallpaper();
            }
        });

        //BUTTON FLOATING 2
//        FloatingActionButton des = (FloatingActionButton) findViewById(R.id.BtnDescargar);
//        des.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                descargarWallpaper();
//            }
//        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void cargarImagenExtendida() {
        Picasso.with(getApplicationContext())
                .load(getIntent().getStringExtra(EXTRA_PARAM_IMG))
                .placeholder(R.drawable.ic_menu_camera)
                .into(imagenExtendida);
    }

    public void ponerWallpaper(){

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


    public void descargarWallpaper(){
        Log.d("test","entra en click");
        Log.d("test","urlpasda"+URLPasada);
        new DownloadFile().execute(URLPasada);
    }


    class DownloadFile extends AsyncTask<String,Integer,Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(ActividadDetalle.this);// Change Mainactivity.this with your activity name.
        String strFolderName;
        InputStream input;
        OutputStream output;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }
        @Override
        protected Long doInBackground(String... aurl) {
            int count;

            try {
                URL url = new URL((String) aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                String targetFileName="bg_"+NOMPasada+".jpg";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();

                File PATH = new File(getExternalFilesDir("wallpaper"), targetFileName);

                input = new BufferedInputStream(url.openStream());
                output = new FileOutputStream(PATH);
                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress ((int)(total*100/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if(mProgressDialog.getProgress()==mProgressDialog.getMax()){
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_SHORT).show();
            }
        }

        protected void onPostExecute(String result) {
        }

    }


}
