package com.example.lucas.myitune.fetcher;

import com.example.lucas.myitune.models.Music;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lucas on 20/04/2018.
 */

public interface IMusicSearchListener {

    public void onMusicResult(ArrayList<Music> results, Exception e);
}
