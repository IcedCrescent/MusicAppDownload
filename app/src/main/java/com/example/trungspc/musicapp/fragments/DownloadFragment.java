package com.example.trungspc.musicapp.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.adapters.TopSongAdapter;
import com.example.trungspc.musicapp.database.TopSongModel;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    private static final String TAG = "DownloadFragment";

    private final String DOWNLOAD_FOLDER = "Downloaded Music";

    TopSongAdapter topSongAdapter;
    List<TopSongModel> topSongModels;
    @BindView(R.id.rv_downloaded)
    RecyclerView rvDownloaded;
    Unbinder unbinder;

    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        unbinder = ButterKnife.bind(this, view);
        topSongModels = new ArrayList<>();
        topSongAdapter = new TopSongAdapter(topSongModels);
        rvDownloaded.setAdapter(topSongAdapter);
        rvDownloaded.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDownloaded.setItemAnimator(new SlideInDownAnimator());

        loadDownloadedSong();
        return view;
    }

    private void loadDownloadedSong() {
        File fileDir = new File(Environment.getExternalStorageDirectory().toString(), DOWNLOAD_FOLDER);
        if (!fileDir.exists() || !fileDir.isDirectory())
            return;
        String[] listFiles = fileDir.list();
        File[] files = fileDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                String[] fileTokenlized = listFiles[i].split("-");
                TopSongModel topSongModel = new TopSongModel(
                        files[i].toString(),
                        null,
                        fileTokenlized[0],
                        fileTokenlized[1].substring(0, fileTokenlized[1].indexOf("."))
                );
                topSongModels.add(topSongModel);
                topSongAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
