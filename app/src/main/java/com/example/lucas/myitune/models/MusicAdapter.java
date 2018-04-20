package com.example.lucas.myitune.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucas.myitune.repository.ManageFavorite;
import com.example.lucas.myitune.R;
import com.example.lucas.myitune.fetcher.DownloadImageTask;

import java.util.ArrayList;

/**
 * Created by Lucas on 17/04/2018.
 */

public class MusicAdapter extends BaseAdapter {

    private Activity context ;
    public ArrayList<Music> musics;

    public MusicAdapter(Activity context, ArrayList<Music> musics) {
        this.context = context;
        this.musics = musics;
    }


    @Override
    public int getCount() {
        return this.musics.size();
    }

    @Override
    public Object getItem(int position) {
        return this.musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.musics.get(position).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View rowView = inflater.inflate( R.layout.music_item,null );


        ImageView imageViewCover = (ImageView) rowView.findViewById( R.id.imageViewViewCover );
        DownloadImageTask downloadImageTask = new DownloadImageTask( imageViewCover) ;
        downloadImageTask.execute(this.musics.get(position).getCover());
        TextView textView = (TextView) rowView.findViewById( R.id.textViewMusicInfo );
        textView.setText( this.musics.get(position).toString() );

        ImageView imageViewFav = (ImageView) rowView.findViewById( R.id.imageViewFavoris);

        if (ManageFavorite.isFavorite( context, musics.get(position) )){
            imageViewFav.setImageResource( android.R.drawable.star_on );
        }else {
            imageViewFav.setImageResource( android.R.drawable.star_off );
        }

        return rowView;
    }
}
