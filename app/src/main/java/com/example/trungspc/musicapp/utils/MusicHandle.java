package com.example.trungspc.musicapp.utils;

import android.util.Log;

import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.example.trungspc.musicapp.network.IMusicService;
import com.example.trungspc.musicapp.network.RetrofitInstance;
import com.example.trungspc.musicapp.network.SearchSongResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicHandles {
    private static final String TAG = "MusicHandles";
    public static void getSearchSong(TopSongModel topSongModel) {
        IMusicService musicService = RetrofitInstance.getRetrofitInstance().create(IMusicService.class);
        musicService.getSearchSong(topSongModel.getSong() + " " + topSongModel.getArtist()).enqueue(new Callback<SearchSongResponse>() {
            @Override
            public void onResponse(Call<SearchSongResponse> call, Response<SearchSongResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().data.url);
            }

            @Override
            public void onFailure(Call<SearchSongResponse> call, Throwable t) {

            }
        });
    }
}
