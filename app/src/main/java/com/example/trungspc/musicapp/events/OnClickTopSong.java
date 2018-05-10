package com.example.trungspc.musicapp.events;

import com.example.trungspc.musicapp.database.TopSongModel;

public class OnClickTopSong {
    public TopSongModel topSongModel;

    public OnClickTopSong(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }
}
