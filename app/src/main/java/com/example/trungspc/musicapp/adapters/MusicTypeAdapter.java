package com.example.trungspc.musicapp.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.activites.MainActivity;
import com.example.trungspc.musicapp.database.MusicTypeModel;
import com.example.trungspc.musicapp.fragments.TopSongFragment;
import com.example.trungspc.musicapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypesViewHolder> {
    List<MusicTypeModel> musicTypeModels = new ArrayList<>();
    Context context;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModels, Context context) {
        this.musicTypeModels = musicTypeModels;
        this.context = context;
    }

    //create itemView, call only once
    @NonNull
    @Override
    public MusicTypesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_music, parent, false);
        return new MusicTypesViewHolder(itemView);
    }

    //load data
    @Override
    public void onBindViewHolder(@NonNull MusicTypesViewHolder holder, int position) {
        holder.setData(musicTypeModels.get(position));
    }

    @Override
    public int getItemCount() {
        return musicTypeModels.size();
    }

    public class MusicTypesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_music_type)
        ImageView ivMusicType;
        @BindView(R.id.tv_music_type)
        TextView tvMusicType;

        public MusicTypesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //load data into view
        public void setData(final MusicTypeModel musicTypeModel) {
//            ivMusicType.setImageResource(musicTypeModel.getImageID());
            Picasso.get().load(musicTypeModel.getImageID()).into(ivMusicType);
            tvMusicType.setText(musicTypeModel.getName());
            ivMusicType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopSongFragment topSongFragment = new TopSongFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("music_type_model", musicTypeModel);
                    topSongFragment.setArguments(bundle);
                    Utils.openFragment(((MainActivity) context).getSupportFragmentManager(), R.id.ll_main, topSongFragment);
                }
            });
        }
    }
}
