package com.example.lucas.myitune.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lucas.myitune.R;
import com.example.lucas.myitune.fetcher.DownloadImageTask;
import com.example.lucas.myitune.models.Music;
import com.example.lucas.myitune.repository.ManageFavorite;

import java.io.IOException;

/**
 * Created by Lucas on 17/04/2018.
 */

public class MusicDetailsFragment extends Fragment implements View.OnClickListener {

    ImageView imageViewCover;
    TextView textViewArtist;
    TextView textViewTitle;
    Button buttonListen ;
    Button buttonStore;
    RadioButton buttonFavYes;
    RadioButton buttonFavNo ;
    Music music ;

    private MediaPlayer player = new MediaPlayer();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.music_detail,null );

        imageViewCover = (ImageView) v.findViewById( R.id.imageViewDetailCover );
        textViewArtist = (TextView) v.findViewById( R.id.textViewDetailArtist );
        textViewTitle = (TextView) v.findViewById( R.id.textViewDetailTitle ) ;
        buttonListen = (Button) v.findViewById( R.id.buttonLister );
        buttonStore = (Button) v.findViewById( R.id.buttonStore );
        buttonFavYes = (RadioButton) v.findViewById( R.id.radioFavButtonOui );
        buttonFavNo = (RadioButton) v.findViewById( R.id.radioFavButtonNon );

        buttonListen.setOnClickListener( this );
        buttonStore.setOnClickListener( this );

        updateView(music);


        return v;
    }

    public void updateView(final Music m){
        music = m ;
        if (textViewArtist == null || m==null){
            return ;
        }
        textViewTitle.setText( m.getTitle() );
        textViewArtist.setText( m.getArtist() );
        DownloadImageTask downloadImageTask = new DownloadImageTask( imageViewCover );
        downloadImageTask.execute( m.getCover() );

        buttonFavYes.setOnClickListener( new View.OnClickListener() {
            //Log.e( "","tdfyugiuyijokptyuijok" );

            @Override
            public void onClick(View view) {
                ManageFavorite.add(getActivity(),music);
            }
        } );

        buttonFavNo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManageFavorite.remove( getActivity(),music );
            }
        } );

        if(ManageFavorite.isFavorite( getActivity(),music )){
            buttonFavYes.setChecked( true );
            buttonFavNo.setChecked( false );
        }else {
            buttonFavNo.setChecked( true );
            buttonFavYes.setChecked( false );
        }

    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v ;
            if (button.equals( buttonListen )){
                if(!player.isPlaying()){
                    player.reset();
                    try{
                        player.setDataSource( getActivity() , Uri.parse( music.getPreview() ));
                        player.prepare();
                        player.start();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else {
                    player.stop();
                }
            }
            else {
                Intent intent = new Intent( Intent.ACTION_VIEW,Uri.parse( music.getLink() ) );
                startActivity( intent );
            }

    }
}
