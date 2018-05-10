package com.example.trungspc.musicapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Utils {
    public static void openFragment(android.support.v4.app.FragmentManager fragmentManager, int layoutID, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static String formatTime(int time) {
        int min = time/60000;
        int sec = (time % 60000)/1000;
        return String.format("%02d:%02d", min, sec);
    }

    public static void rotateImage(ImageView ivAlbum, boolean isPlaying) {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        if (isPlaying) {
            if (ivAlbum.getAnimation() == null) {
                ivAlbum.startAnimation(rotateAnimation);
            }
        } else {
            ivAlbum.setAnimation(null);
        }
    }
}
