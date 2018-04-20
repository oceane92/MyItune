package com.example.lucas.myitune.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Lucas on 17/04/2018.
 */

public class Music implements Serializable {
    private String artist;
    private String title;
    private  String cover ;
    private String preview;
    private String link;

    private boolean isFavoris;

    public Music(){}

    public Music(String artist, String title, String cover,String preview,String link) {
        this.artist = artist;
        this.title = title;
        this.cover = cover;
        this.preview = preview;
        this.link = link ;
        this.isFavoris = false;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return getArtist()+"-"+getTitle();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isFavoris() {
        return isFavoris;
    }

    public void setFavoris(boolean favoris) {
        isFavoris = favoris;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Music music = (Music) o;

        if (isFavoris != music.isFavoris) return false;
        if (!artist.equals( music.artist )) return false;
        if (!title.equals( music.title )) return false;
        if (!cover.equals( music.cover )) return false;
        if (!preview.equals( music.preview )) return false;
        return link.equals( music.link );
    }



    @Override
    public int hashCode() {

        return Objects.hash(artist,title,cover,preview,link,isFavoris);
    }
}
