package com.example.lucas.myitune.fetcher;

import com.example.lucas.myitune.models.IMusicListener;

/**
 * Created by Lucas on 20/04/2018.
 */

public interface IMusicSearch {
    public void searchMusic(String text, IMusicSearchListener listener);
}
