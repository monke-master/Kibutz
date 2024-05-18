package com.monke.rental;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ContactsModel implements Parcelable {

    private Contacts contacts;

    public ContactsModel(Contacts contacts) {
        this.contacts = contacts;
    }

    public Contacts getContacts() {
        return contacts;
    }

    protected ContactsModel(Parcel in) {
        contacts.setEmail(in.readString());
        contacts.setPhoneNumber(in.readString());
        contacts.setTelegramLogin(in.readString());
    }

    public static final Creator<ContactsModel> CREATOR = new Creator<ContactsModel>() {
        @Override
        public ContactsModel createFromParcel(Parcel in) {
            return new ContactsModel(in);
        }

        @Override
        public ContactsModel[] newArray(int size) {
            return new ContactsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(contacts.getEmail());
        dest.writeString(contacts.getPhoneNumber());
        dest.writeString(contacts.getTelegramLogin());
    }
}
