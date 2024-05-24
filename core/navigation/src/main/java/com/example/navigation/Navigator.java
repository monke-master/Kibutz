package com.example.navigation;

import androidx.fragment.app.Fragment;

public abstract class Navigator {

    protected static Fragment mainFragment;

    abstract public void closeMainFragment();

    public static void setMainFragment(Fragment mainFragment) {
        Navigator.mainFragment = mainFragment;
    }
}
