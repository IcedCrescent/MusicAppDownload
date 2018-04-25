package com.example.trungspc.musicapp.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IMusicType {
    @GET("api")
    Call<MusicTypeResponse> getListMusicTypes();
}
