package com.example.trungspc.musicapp.activites;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trungspc.musicapp.R;
import com.example.trungspc.musicapp.adapters.ViewPagerAdapter;
import com.example.trungspc.musicapp.database.TopSongModel;
import com.example.trungspc.musicapp.events.OnClickTopSong;
import com.example.trungspc.musicapp.fragments.MainPlayer;
import com.example.trungspc.musicapp.utils.MusicHandle;
import com.example.trungspc.musicapp.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv_topsong)
    ImageView ivTopsong;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.sb_mini)
    SeekBar sbMini;
    @BindView(R.id.rl_mini)
    RelativeLayout rlMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        tablayout.getTabAt(0).getIcon().setAlpha(255);
        tablayout.getTabAt(1).getIcon().setAlpha(100);
        tablayout.getTabAt(2).getIcon().setAlpha(100);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(255);
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        sbMini.setPadding(0, 0, 0, 0);

        setupPermission();

    }

    @Subscribe(sticky =  true)
    public void onReceivedTopSong(OnClickTopSong onClickTopSong) {
        TopSongModel topSongModel = onClickTopSong.topSongModel;

        rlMini.setVisibility(View.VISIBLE);
        tvSong.setText(topSongModel.getSong());
        tvArtist.setText(topSongModel.getArtist());
        if (topSongModel.getImage() == null)
            Picasso.get().load(R.drawable.no_network).transform(new CropCircleTransformation()).into(ivTopsong);
        else
            Picasso.get().load(topSongModel.getImage()).transform(new CropCircleTransformation()).into(ivTopsong);

        MusicHandle.getSearchSong(topSongModel, this);
        MusicHandle.updateUIRealTime(sbMini, fbPlay, null, null, ivTopsong);
    }

    @OnClick({R.id.fb_play, R.id.rl_mini})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fb_play:
                MusicHandle.playPause();
                break;
            case R.id.rl_mini:
                Utils.openFragment(getSupportFragmentManager(), R.id.rl_main, new MainPlayer());
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }
    }

    private void setupPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission Required")
                        .setMessage("Without write permission, this app can't save songs to your device. Grant permission?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}
