package com.example.trungspc.musicapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IMusicType {
    @GET("api")
    Call<MusicTypeResponse> getListMusicTypes();

    @GET("https://itunes.apple.com/us/rss/topsongs/limit=50/genre={idMusicType}/explicit=true/json")
    Call<TopSongResponse> getTopSong(@Path("idMusicType") String idMusicType);

    @GET("")
}
