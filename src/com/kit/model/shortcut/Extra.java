package com.kit.model.shortcut;

import android.os.Parcel;
import android.os.Parcelable;

public class Extra implements Parcelable{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String name;
    String value;

    public Extra() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    protected Extra(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<Extra> CREATOR = new Creator<Extra>() {
        @Override
        public Extra createFromParcel(Parcel source) {
            return new Extra(source);
        }

        @Override
        public Extra[] newArray(int size) {
            return new Extra[size];
        }
    };
}