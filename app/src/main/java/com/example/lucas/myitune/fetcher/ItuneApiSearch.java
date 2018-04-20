package com.example.lucas.myitune.fetcher;

import com.example.lucas.myitune.models.IMusicListener;
import com.example.lucas.myitune.models.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lucas on 20/04/2018.
 */

public class ItuneApiSearch implements IMusicSearch,IJsonTaskListener {


    static final String ITUNES_SEARCH_URL = "https://itunes.apple.com/search?term=";
    IMusicSearchListener listener;


    @Override
    public void searchMusic(String text, IMusicSearchListener listener) {

        this.listener = listener ;
        DownloadJsonTask downloadJsonTask = new DownloadJsonTask(this);
        downloadJsonTask.execute( ITUNES_SEARCH_URL+text.replace( " ","%20" ) );
    }

    @Override
    public void onRequestResult(JSONObject result, Exception e) {

        ArrayList<Music> musics = new ArrayList<>(  );

        try{

          JSONArray tracks = result.getJSONArray( "results" );
            for (int i =0 ;i<tracks.length();i++) {
                JSONObject track = tracks.getJSONObject( i );
                Music m = new Music();
                m.setArtist( track.getString( "artistName" ) );
                m.setTitle( track.getString( "trackName" ) );
                m.setCover( track.getString( "artworkUrl100" ) );
                m.setLink( track.getString( "trackViewUrl" ) );
                m.setPreview( track.getString( "previewUrl" ) );

                musics.add( m );
            }

            listener.onMusicResult( musics,null );
            }catch (JSONException el){

            el.printStackTrace();
            listener.onMusicResult( null,el );
        }

    }
}
