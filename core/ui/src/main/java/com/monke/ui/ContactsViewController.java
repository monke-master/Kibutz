package com.monke.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.ViewGroup;

import com.monke.rental.Contacts;

import java.util.HashSet;
import java.util.List;

public class ContactsViewController {

    private Activity activity;
    private ViewGroup root;
    private Contacts contacts;

    public ContactsViewController(Activity activity, ViewGroup root, Contacts contacts) {
        this.activity = activity;
        this.root = root;
        this.contacts = contacts;
    }

    public void initViews() {
        root.findViewById(R.id.btn_email).setOnClickListener(v -> {
            openEmail(contacts.getEmail());
        });

        root.findViewById(R.id.btn_tg).setOnClickListener(v -> {
            openTelegram(activity, contacts.getTelegramLogin());
        });

        root.findViewById(R.id.btn_phone).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contacts.getPhoneNumber(), null));
            activity.startActivity(intent);
        });
    }

    public void openTelegram(Activity activity, String userName) {
        Intent general = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.com/" + userName));
        HashSet<String> generalResolvers = new HashSet<>();
        List<ResolveInfo> generalResolveInfo = activity.getPackageManager().queryIntentActivities(general, 0);
        for (ResolveInfo info : generalResolveInfo) {
            if (info.activityInfo.packageName != null) {
                generalResolvers.add(info.activityInfo.packageName);
            }
        }

        Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + userName));
        int goodResolver = 0;
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(telegram, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName != null && !generalResolvers.contains(info.activityInfo.packageName)) {
                    goodResolver++;
                    telegram.setPackage(info.activityInfo.packageName);
                }
            }
        }

        if (goodResolver != 1) {
            telegram.setPackage(null);
        }
        if (telegram.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(telegram);
        }
    }

    public void openEmail(String address) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }
}
