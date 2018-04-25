package com.example.trungspc.musicapp.database;

public class TopSongModel {
    String url;
    String image;
    String song;
    String artist;

    public TopSongModel(String url, String image, String song, String artist) {
        this.url = url;
        this.image = image;
        this.song = song;
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getSong() {
        return song;
    }

    public String getArtist() {
        return artist;
    }
}
