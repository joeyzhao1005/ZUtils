package com.kit.model.shortcut;

import android.os.Parcel;
import android.os.Parcelable;

public class Extra {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    String name;
    Object value;



}