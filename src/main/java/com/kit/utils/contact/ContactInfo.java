package com.kit.utils.contact;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Zhao on 16/8/9.
 */
public class ContactInfo {

    private String displayName;
    private List<String> number;

    private Bitmap avatar;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
}
