package com.monke.kibutz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.monke.kibutz.databinding.ActivityMainBinding;

public class KibutzMainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}