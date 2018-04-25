package com.example.trungspc.musicapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.trungspc.musicapp.fragments.DownloadFragment;
import com.example.trungspc.musicapp.fragments.FavoriteFragment;
import com.example.trungspc.musicapp.fragments.MusicTypeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicTypeFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new DownloadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
