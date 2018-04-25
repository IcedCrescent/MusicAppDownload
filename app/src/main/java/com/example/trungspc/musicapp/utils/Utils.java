package com.example.trungspc.musicapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class Utils {
    public static void openFragment(android.support.v4.app.FragmentManager fragmentManager, int layoutID, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
