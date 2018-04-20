package com.example.lucas.myitune.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lucas.myitune.R;
import com.example.lucas.myitune.fetcher.DownloadJsonTask;
import com.example.lucas.myitune.fetcher.IMusicSearch;
import com.example.lucas.myitune.fetcher.IMusicSearchListener;
import com.example.lucas.myitune.fetcher.ItuneApiSearch;
import com.example.lucas.myitune.models.IMusicListener;
import com.example.lucas.myitune.models.IOnFavoriteListener;
import com.example.lucas.myitune.models.Music;
import com.example.lucas.myitune.models.MusicAdapter;
import com.example.lucas.myitune.repository.ManageFavorite;

import java.util.ArrayList;

/**
 * Created by Lucas on 19/04/2018.
 */

public class MusicSearchFragment extends Fragment implements IOnFavoriteListener, AdapterView.OnItemClickListener, IMusicSearchListener{

    static final String ITUNES_SEARCH_URL = "https://itunes.apple.com/search?term=";

    IMusicListener listener;
    ProgressDialog spinner;


    ArrayList<Music> musics = new ArrayList<Music>();
    ListView listView;
    EditText editTextSearch;
    ImageButton buttonSearch;
    MusicAdapter adapter;

    public IMusicListener getListener() {
        return listener;
    }

    public void setListener(IMusicListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.music_search,null );

        ManageFavorite.setListener( this );

        listView = (ListView) v.findViewById( R.id.listViewMusics );
        adapter = new MusicAdapter( getActivity(),musics );
        listView.setAdapter( adapter );
        listView.setOnItemClickListener(this );

        /**************************************************************/
        editTextSearch = (EditText) v.findViewById( R.id.editTextSearch );
        buttonSearch = (ImageButton) v.findViewById( R.id.imageButtonSearch );
        buttonSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMusics( editTextSearch.getText().toString() );
            }
        } );
        /*************************************************************/

        return v;
    }



    public void searchMusics(String text){
        //String url = ITUNES_SEARCH_URL+text.replace( " ","%20" );

        spinner = new ProgressDialog( getActivity() );

        spinner.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        spinner.setTitle( "Loading songs of " + text );
        spinner.setMessage( "Find songs in progress..." );
        spinner.setCancelable( false );
        spinner.show();

        /*DownloadJsonTask jsonTask = new DownloadJsonTask( listView,musics,spinner,getActivity() );
        jsonTask.execute( url );*/

        ItuneApiSearch ituneApiSearch = new ItuneApiSearch();
        ituneApiSearch.searchMusic( text,this );

    }

    public void loadFavoris(){
        musics = ManageFavorite.load( getActivity() );
        refresh();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        listener.onMusicSelected( musics.get(position));
    }

    @Override
    public void OnFavorisChange(Music m, boolean isFavoris) {

    }
/*
    @Override
    public void onMusicSelected(Music music) {

    }

    @Override
    public void searchMusic(String text, IMusicListener listener) {

    }*/

    public void refresh(){
        adapter = new MusicAdapter( getActivity(),musics );
        listView.setAdapter( adapter );
    }

    @Override
    public void onMusicResult(ArrayList<Music> results, Exception e) {
        spinner.dismiss();
        if (e==null){
            musics = results ;
            refresh();
        }
    }
}
