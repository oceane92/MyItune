package com.example.lucas.myitune.fetcher;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lucas on 18/04/2018.
 */

public class DownloadJsonTask extends AsyncTask<String,Void,String> {

    /*private ListView listView;
    private ArrayList<Music> musics;
    private ProgressDialog spiner;
    private Context context;*/


    IJsonTaskListener listener;

    public DownloadJsonTask(IJsonTaskListener listener) {
        this.listener = listener;
    }

    /*  public DownloadJsonTask(ListView listView, ArrayList<Music> musics, ProgressDialog spinner, Context context) {
        this.listView = listView;
        this.musics = musics;
        this.spiner = spinner;
        this.context = context;
    }*/

    private String download(String url){
        String result="";

        try{
            URL aURL = new URL( url ) ;
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            result = convertStreamToString( is );
            is.close();
        }catch (IOException e){
            Log.e("Hub","Error getting the image from server"+e.getMessage()) ;
        }

        return result ;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader( new InputStreamReader( is ) );
        StringBuilder sb = new StringBuilder(  );
        String line ;

        try{
            while ((line = reader.readLine())!=null){
                sb.append( line ).append( "\n" );
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    @Override
    protected void onPostExecute(String s) {

        //musics.clear();

        try {
            JSONObject jsonObject = new JSONObject( s );
            listener.onRequestResult( jsonObject,null );


           /* JSONArray tracks = jsonObject.getJSONArray( "results" );
            for (int i =0 ;i<tracks.length();i++){
                JSONObject track = tracks.getJSONObject( i );
                Music m = new Music(  );
                m.setArtist( track.getString( "artistName" ) );
                m.setTitle( track.getString( "trackName" ) );
                m.setCover( track.getString( "artworkUrl100" ) );
                m.setLink( track.getString( "trackViewUrl" ) );
                m.setPreview( track.getString( "previewUrl" ) );

                musics.add(m);

                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                spiner.dismiss();

            }*/


        }catch (JSONException e){
            e.printStackTrace();
            listener.onRequestResult( null,e );
        }


    }

    @Override
    protected String doInBackground(String... strings) {
        return download( strings[0] );
    }
}
