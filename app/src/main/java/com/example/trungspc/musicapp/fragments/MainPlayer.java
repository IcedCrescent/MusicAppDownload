package com.example.trungspc.musicapp.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.example.trungspc.musicapp.events.OnClickTopSong;
import com.example.trungspc.musicapp.utils.MusicHandle;
import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayer extends Fragment {
    private static final String TAG = "MainPlayer";
    private final String DOWNLOAD_FOLDER = "Downloaded Music";
    private ThinDownloadManager downloadManager;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_prev_track)
    ImageView ivPrevTrack;
    @BindView(R.id.iv_next_track)
    ImageView ivNextTrack;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.sb_duration)
    SeekBar sbDuration;
    Unbinder unbinder;

    TopSongModel topSongModel;
    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.fab_play)
    FloatingActionButton fabPlay;

    public MainPlayer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_player, container, false);
        unbinder = ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(sticky = true)
    public void onReceivedTopSong(OnClickTopSong onClickTopSong) {
        topSongModel = onClickTopSong.topSongModel;

        tvSong.setText(topSongModel.getSong());
        tvArtist.setText(topSongModel.getArtist());

        if (topSongModel.getImage() == null)
            Picasso.get().load(R.drawable.no_network).transform(new CropCircleTransformation()).into(ivAlbum);
        else
            Picasso.get().load(topSongModel.getImage()).transform(new CropCircleTransformation()).into(ivAlbum);

        MusicHandle.updateUIRealTime(sbDuration, fabPlay, tvCurrentTime, tvDuration, ivAlbum);
    }

    @OnClick({R.id.iv_back, R.id.iv_download, R.id.iv_prev_track, R.id.fab_play, R.id.iv_next_track})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.iv_download:
                downloadTask();
                break;
            case R.id.iv_prev_track:
                break;
            case R.id.fab_play:
                MusicHandle.playPause();
                break;
            case R.id.iv_next_track:
                break;
        }
    }

    private void downloadTask() {
        Log.d(TAG, "downloadTask: presssed");
        Uri downloadUri = Uri.parse(MusicHandle.downloadUrl.trim());
        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root, DOWNLOAD_FOLDER);
        //if (!folder.exists())
        folder.mkdirs();
        String fileName = String.format("%s-%s.mp3", MusicHandle.songName, MusicHandle.songArtist);
        Uri destinationUri = Uri.parse(folder.toString()+"/"+fileName);
        Log.d(TAG, "downloadTask: " + destinationUri);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH)
                .setStatusListener(new DownloadStatusListenerV1() {
                    @Override
                    public void onDownloadComplete(DownloadRequest downloadRequest) {
                        Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();
                        ivDownload.setEnabled(true);
                        ivDownload.setAlpha(1f);
                    }

                    @Override
                    public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                        Toast.makeText(getContext(), "Download Failed", Toast.LENGTH_SHORT).show();
                        ivDownload.setEnabled(true);
                        ivDownload.setAlpha(1f);
                        Log.d(TAG, "onDownloadFailed: " + errorMessage);
                    }

                    @Override
                    public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                        ivDownload.setEnabled(false);
                        ivDownload.setAlpha(0.5f);
                    }
                });
        downloadManager = new ThinDownloadManager();
        downloadManager.add(downloadRequest);
        ivDownload.setEnabled(false);
        ivDownload.setAlpha(0.5f);
    }
}
