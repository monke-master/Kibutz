package com.monke.identity;

import android.os.Parcel;
import android.os.Parcelable;

public class IdentityModel implements Parcelable {

    private Identity identity;

    public IdentityModel(Identity identity) {
        this.identity = identity;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(identity.getId());
        out.writeString(identity.getName());
        out.writeString(identity.getType().name());
        out.writeString(identity.getOppositeId());
        out.writeString(identity.getIconUrl());
    }

    public static final Parcelable.Creator<IdentityModel> CREATOR
            = new Parcelable.Creator<IdentityModel>() {
        public IdentityModel createFromParcel(Parcel in) {
            return new IdentityModel(in);
        }

        public IdentityModel[] newArray(int size) {
            return new IdentityModel[size];
        }
    };

    public Identity getIdentity() {
        return identity;
    }

    private IdentityModel(Parcel in) {
        identity.setId(in.readString());
        identity.setName(in.readString());
        identity.setType(Identity.Type.valueOf(in.readString()));
        identity.setOppositeId(in.readString());
        identity.setIconUrl(in.readString());
    }
}
