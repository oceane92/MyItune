package com.example.lucas.myitune.fetcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lucas on 18/04/2018.
 */

public class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

    ImageView myView ;

    @Override
    protected Bitmap doInBackground(String... strings) {

        return download_Image( strings[0] );
    }

    public DownloadImageTask(ImageView myView) {
        this.myView = myView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        myView.setImageBitmap( bitmap );
        myView.invalidate();
    }

    private Bitmap download_Image(String url){
        Bitmap bm = null ;

        try{
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream( is );
            bm = BitmapFactory.decodeStream( bis );
            bis.close();
            is.close();

        }catch (IOException e){
            Log.e("Hub","Error getting the image from server : "+e.getMessage().toString());
        }
        return bm;
    }


}
