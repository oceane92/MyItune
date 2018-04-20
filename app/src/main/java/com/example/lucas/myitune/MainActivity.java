package com.example.lucas.myitune;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lucas.myitune.fetcher.DownloadJsonTask;
import com.example.lucas.myitune.fragments.MusicDetailsFragment;
import com.example.lucas.myitune.fragments.MusicSearchFragment;
import com.example.lucas.myitune.models.IMusicListener;
import com.example.lucas.myitune.models.IOnFavoriteListener;
import com.example.lucas.myitune.models.Music;
import com.example.lucas.myitune.models.MusicAdapter;
import com.example.lucas.myitune.repository.ManageFavorite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMusicListener {

    MusicSearchFragment searchFragment;
    MusicDetailsFragment detailsFragment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.main_activity );

        if (findViewById( R.id.frameLayout )!= null){//telephon
            setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
            searchFragment = new MusicSearchFragment();
            detailsFragment = new MusicDetailsFragment();
            getFragmentManager().beginTransaction().add( R.id.frameLayout,searchFragment ).commit();
        }else{
        //tablet
            searchFragment = (MusicSearchFragment) getFragmentManager().findFragmentById( R.id.fragmentSearch );
            detailsFragment = (MusicDetailsFragment) getFragmentManager().findFragmentById( R.id.fragmentDetail );

            getFragmentManager().beginTransaction().hide( detailsFragment ).commit();

        }

        searchFragment.setListener( this );

        /* searchFragment = new MusicSearchFragment();
        searchFragment.setListener( this );
        musicDetailsFragment = new MusicDetailsFragment();

        getFragmentManager().beginTransaction().add(R.id.frameLayout,searchFragment).commit();*/




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        searchFragment.loadFavoris();
        return super.onOptionsItemSelected( item );
    }
//******************************************************************
    @Override
    public void onBackPressed() {
        if(findViewById( R.id.frameLayout)!=null && detailsFragment.isVisible() ){
            getFragmentManager().beginTransaction()
                    .hide( detailsFragment )
                    .show( searchFragment ).commit();
        }

        else{
            super.onBackPressed();
        }
        //
    }

    //***********************************

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onMusicSelected(Music music) {

        if(findViewById( R.id.frameLayout )!=null){

        if (detailsFragment.isAdded()){
            getFragmentManager().beginTransaction().
                    hide( searchFragment ).
                    show( detailsFragment )
                    .commit();
        }else {
            getFragmentManager().beginTransaction()
                    .hide(searchFragment)
                    .add( R.id.frameLayout,detailsFragment )
                    .commit();
        }
        }else{
            getFragmentManager().beginTransaction()
                    .show( detailsFragment )
                    .commit() ;
        }
       detailsFragment.updateView( music );



    }
}
