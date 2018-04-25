package com.example.trungspc.musicapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongViewHolder> {
    List<TopSongModel> topSongModels = new ArrayList<>();

    public TopSongAdapter(List<TopSongModel> topSongModels) {
        this.topSongModels = topSongModels;
    }

    @NonNull
    @Override
    public TopSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_topsong, parent, false);
        return new TopSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSongViewHolder holder, int position) {
        holder.setData(topSongModels.get(position));
    }

    @Override
    public int getItemCount() {
        return topSongModels.size();
    }


    public class TopSongViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_topsong)
        ImageView ivTopSong;
        @BindView(R.id.tv_song)
        TextView tvSong;
        @BindView(R.id.tv_artist)
        TextView tvArtist;

        public TopSongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(TopSongModel topSongModel) {
            Picasso.get().load(topSongModel.getImage()).transform(new CropCircleTransformation()).into(ivTopSong);
            tvSong.setText(topSongModel.getSong());
            tvArtist.setText(topSongModel.getArtist());
        }
    }
}
