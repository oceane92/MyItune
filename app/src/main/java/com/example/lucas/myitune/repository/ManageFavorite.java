package com.example.lucas.myitune.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lucas.myitune.models.IOnFavoriteListener;
import com.example.lucas.myitune.models.Music;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lucas on 19/04/2018.
 */

public class ManageFavorite {

    public static IOnFavoriteListener listener ;

    public static IOnFavoriteListener getListener() {
        return listener;
    }

    public static void setListener(IOnFavoriteListener listener) {
        ManageFavorite.listener = listener;
    }

    private static void save (Context context, ArrayList<Music> musics){

        SharedPreferences appSharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        // SharedPreferences -> contenenant android qui permet de stocker des données utilisateurs
        SharedPreferences.Editor prefsEditor = appSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson( musics );
        prefsEditor.putString( "musicsFavorites",json );
        prefsEditor.commit();
    }

    public static ArrayList<Music> load(Context context){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences( context.getApplicationContext() );
        Gson gson = new Gson();
        String json = appSharedPrefs.getString( "musicsFavorites","" );
        List<Music> musics ;

        if (json.equals( "" )){
            musics  = new ArrayList<Music>( );
        }else {
            Music[] favoritesMusics = gson.fromJson( json,Music[].class );
            musics = Arrays.asList(favoritesMusics);
            musics = new ArrayList<Music>( musics );
        }
        return (ArrayList<Music>) musics ;
    }

    public static void add(Context context,Music music){
        ArrayList<Music> favorites = load( context );
        if (!favorites.contains( music )){
            favorites.add( music );
        }
        save( context,favorites );
    }




    public static void remove(Context context,Music music){
        ArrayList<Music> favorites = load( context );
        favorites.remove( music );
        listener.OnFavorisChange( music, false );
        save( context,favorites );
    }

    public static boolean isFavorite(Context context,Music music){
        ArrayList<Music> favorites = load( context );
        return favorites.contains( music );
    }
}
