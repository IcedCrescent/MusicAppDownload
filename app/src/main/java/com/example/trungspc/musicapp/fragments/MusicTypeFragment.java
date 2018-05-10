package com.example.trungspc.musicapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.adapters.MusicTypeAdapter;
import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.network.IMusicService;
import com.example.trungspc.musicapp.network.MusicTypeResponse;
import com.example.trungspc.musicapp.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeFragment extends Fragment {
    private static final String TAG = "MusicTypeFragment";
    @BindView(R.id.rv_music_types)
    RecyclerView rvMusicTypes;
    Unbinder unbinder;

    MusicTypeAdapter musicTypeAdapter;
    List<MusicTypeModel> musicTypeModels = new ArrayList<>();
    Context context;

    public MusicTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_music_type, container, false);
        unbinder = ButterKnife.bind(this, view);

        musicTypeAdapter = new MusicTypeAdapter(musicTypeModels, getContext());
        rvMusicTypes.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0) ? 2 : 1;
            }
        });
        //rvMusicTypes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMusicTypes.setLayoutManager(gridLayoutManager);
        context = getContext();
        loadData();
        return view;
    }

    private void loadData() {
        IMusicService musicType = RetrofitInstance.getRetrofitInstance().create(IMusicService.class);
        musicType.getListMusicTypes().enqueue(new Callback<MusicTypeResponse>() {
            @Override
            public void onResponse(Call<MusicTypeResponse> call, Response<MusicTypeResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().getSubgenres().toString());
                List<MusicTypeResponse.Subgenre> subgenreList = response.body().getSubgenres();
                for (MusicTypeResponse.Subgenre subgenre: subgenreList) {
                    MusicTypeModel musicTypeModel = new MusicTypeModel(
                            subgenre.getId(),
                            subgenre.getTranslation_key(),
                            context.getResources().getIdentifier("genre_x2_" + subgenre.getId(), "raw", context.getPackageName()));
                            musicTypeModels.add(musicTypeModel);
                }
                musicTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MusicTypeResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
