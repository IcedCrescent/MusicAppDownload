package com.example.trungspc.musicapp.utils;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.example.trungspc.musicapp.network.IMusicService;
import com.example.trungspc.musicapp.network.LocationResponse;
import com.example.trungspc.musicapp.network.RetrofitInstance;
import com.example.trungspc.musicapp.network.SearchSongResponse;

import hybridmediaplayer.HybridMediaPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicHandle {
    private static final String TAG = "MusicHandle";

    private static HybridMediaPlayer hybridMediaPlayer;
    private static boolean keepUpdate = true;
    public static String downloadUrl;
    public static String songName;
    public static String songArtist;
    public static void getSearchSong(TopSongModel topSongModel, final Context context) {
        songName = topSongModel.getSong();
        songArtist = topSongModel.getArtist();
        IMusicService musicService = RetrofitInstance.getRetrofitInstance().create(IMusicService.class);
        musicService.getSearchSong(topSongModel.getSong() + " " + topSongModel.getArtist()).enqueue(new Callback<SearchSongResponse>() {
            @Override
            public void onResponse(Call<SearchSongResponse> call, Response<SearchSongResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().data.url);
                String url = response.body().data.url;
                getLocation(url, context);
            }

            @Override
            public void onFailure(Call<SearchSongResponse> call, Throwable t) {

            }
        });
    }

    private static void getLocation(String url, final Context context) {

        Log.wtf(TAG, downloadUrl);
        IMusicService musicService = RetrofitInstance.getRetrofitXMLInstance().create(IMusicService.class);
        musicService.getLocation(url.split("=")[1]).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().track.location.trim());
                downloadUrl = response.body().track.location;

                if (hybridMediaPlayer != null) {
                    if (hybridMediaPlayer.isPlaying()) {
                        hybridMediaPlayer.pause();
                    }
                    hybridMediaPlayer.release();
                }

                hybridMediaPlayer = HybridMediaPlayer.getInstance(context);
                hybridMediaPlayer.setDataSource(response.body().track.location.trim());
                hybridMediaPlayer.prepare();
                hybridMediaPlayer.setOnPreparedListener(new HybridMediaPlayer.OnPreparedListener() {


                    @Override
                    public void onPrepared(HybridMediaPlayer hybridMediaPlayer) {
                        hybridMediaPlayer.play();
                    }
                });
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
    }
    public static void playPause() {
        if (hybridMediaPlayer != null) {
            if (hybridMediaPlayer.isPlaying())
                hybridMediaPlayer.pause();
            else
                hybridMediaPlayer.play();
        }
    }

    public static void updateUIRealTime(final SeekBar seekBar, final FloatingActionButton floatingActionButton, final TextView tvCurrent, final TextView tvDuraion, final ImageView ivAlbum) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //update
                if (hybridMediaPlayer != null && keepUpdate) {
                    seekBar.setMax(hybridMediaPlayer.getDuration());
                    seekBar.setProgress(hybridMediaPlayer.getCurrentPosition());

                    if (hybridMediaPlayer.isPlaying())
                        floatingActionButton.setImageResource(R.drawable.ic_pause_white_24dp);
                    else
                        floatingActionButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);

                    if (tvCurrent != null) {
                        tvCurrent.setText(Utils.formatTime(hybridMediaPlayer.getCurrentPosition()));
                        tvDuraion.setText(Utils.formatTime(hybridMediaPlayer.getDuration()));
                    }

                    Utils.rotateImage(ivAlbum, hybridMediaPlayer.isPlaying());
                }

                //100ms run code
                handler.postDelayed(this, 100);
            }
        };
        runnable.run();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                keepUpdate = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                hybridMediaPlayer.seekTo(seekBar.getProgress());
                keepUpdate = true;
            }
        });
    }
}
