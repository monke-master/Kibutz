package com.monke.ui;

import android.view.ViewGroup;

import com.monke.rental.Contacts;

public class ContactsViewController {

    private ViewGroup root;
    private Contacts contacts;

    public ContactsViewController(ViewGroup root, Contacts contacts) {
        this.root = root;
    }

    public void initViews() {
        root.findViewById(R.id.btn_email).setOnClickListener(v -> {

        });

        root.findViewById(R.id.btn_tg).setOnClickListener(v -> {

        });

        root.findViewById(R.id.btn_phone).setOnClickListener(v -> {

        });
    }
}
