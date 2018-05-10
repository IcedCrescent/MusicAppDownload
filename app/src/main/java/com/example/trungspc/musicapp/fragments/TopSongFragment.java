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

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.adapters.TopSongAdapter;
import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.example.trungspc.musicapp.network.IMusicService;
import com.example.trungspc.musicapp.network.RetrofitInstance;
import com.example.trungspc.musicapp.network.TopSongResponse;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    MusicTypeModel musicTypeModel;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView avLoading;

    public TopSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicTypeModel = (MusicTypeModel) getArguments().getSerializable("music_type_model");
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


        topSongAdapter = new TopSongAdapter(topSongModels);
        rvTopSongs.setAdapter(topSongAdapter);
        rvTopSongs.setLayoutManager(new LinearLayoutManager((getContext())));
        rvTopSongs.setItemAnimator(new SlideInLeftAnimator());

        loadTopSong();

        return view;
    }

    private void loadTopSong() {
        IMusicService musicService = RetrofitInstance.getRetrofitInstance().create(IMusicService.class);
        musicService.getTopSong(musicTypeModel.getId()).enqueue(new Callback<TopSongResponse>() {
            @Override
            public void onResponse(Call<TopSongResponse> call, Response<TopSongResponse> response) {
                avLoading.hide();
                List<TopSongResponse.Feed.Entry> entries = response.body().getFeed().getEntry();
                for (TopSongResponse.Feed.Entry entry : entries) {
                    TopSongModel topSongModel = new TopSongModel(
                            "",
                            entry.getImages().get(2).getLabel(),
                            entry.getName().getLabel(),
                            entry.getArtist().getLabel()
                    );
                    topSongModels.add(topSongModel);
                    topSongAdapter.notifyItemChanged(entries.indexOf(entry));
                }
            }

            @Override
            public void onFailure(Call<TopSongResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
