package com.example.navigation;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;

public abstract class Navigator {

    protected static Fragment mainFragment;

    abstract public void closeMainFragment();

    public static void setMainFragment(Fragment mainFragment) {
        Navigator.mainFragment = mainFragment;
    }

    public static NavOptions.Builder DEFAULT_OPTIONS =
            new NavOptions.Builder()
                    .setEnterAnim(ru.monke.anim.R.anim.push_left_in)
                    .setExitAnim(ru.monke.anim.R.anim.push_left_out)
                    .setPopEnterAnim(ru.monke.anim.R.anim.push_right_in)
                    .setPopEnterAnim(ru.monke.anim.R.anim.push_right_out);
}
