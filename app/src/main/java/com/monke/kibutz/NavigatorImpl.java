package com.monke.kibutz;

import androidx.navigation.fragment.NavHostFragment;

import com.example.navigation.Navigator;

import javax.inject.Inject;

public class NavigatorImpl extends Navigator {


    @Inject
    public NavigatorImpl() {

    }

    @Override
    public void closeMainFragment() {
        NavHostFragment.findNavController(mainFragment).navigate(R.id.close_main_fragment);
    }
}
