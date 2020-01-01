package com.kit.utils.intent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author joeyzhao
 */
public class ParcelableString implements Parcelable {
    String value;

    public ParcelableString(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
    }

    protected ParcelableString(Parcel in) {
        this.value = in.readString();
    }

    public static final Creator<ParcelableString> CREATOR = new Creator<ParcelableString>() {
        @Override
        public ParcelableString createFromParcel(Parcel source) {
            return new ParcelableString(source);
        }

        @Override
        public ParcelableString[] newArray(int size) {
            return new ParcelableString[size];
        }
    };
}
