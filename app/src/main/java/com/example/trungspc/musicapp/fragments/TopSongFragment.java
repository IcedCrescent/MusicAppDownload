package com.example.trungspc.musicapp.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungspc.musicapp.MainPlayer;
import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.adapters.TopSongAdapter;
import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongFragment extends Fragment {
    private static final String TAG = "TopSongFragment";

    @BindView(R.id.iv_music_type)
    ImageView ivMusicType;
    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    TopSongAdapter topSongAdapter;
    List<TopSongModel> topSongModels = new ArrayList<>();
    @BindView(R.id.rv_top_songs)
    RecyclerView rvTopSongs;

    public TopSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        unbinder = ButterKnife.bind(this, view);

        MusicTypeModel musicTypeModel = (MusicTypeModel) getArguments().getSerializable("music_type_model");
        Picasso.get().load(musicTypeModel.getImageID()).into(ivMusicType);
        tvMusicType.setText(musicTypeModel.getName());

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    toolbar.setBackground(getResources().getDrawable(R.drawable.custom_gradient_text_title));
                } else {
                    toolbar.setBackground(null);
                }
                Log.d(TAG, "onOffsetChanged: " + verticalOffset);
            }
        });

        TopSongModel topSongModel = new TopSongModel(
                "",
                "https://i.pinimg.com/originals/47/b8/18/47b818a82212e9d2c486b4e3d8fb68f9.jpg",
                "Levels",
                "Avicii");
        topSongModels.add(topSongModel);
        topSongAdapter = new TopSongAdapter(topSongModels);
        rvTopSongs.setAdapter(topSongAdapter);
        rvTopSongs.setLayoutManager(new LinearLayoutManager((getContext())));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
