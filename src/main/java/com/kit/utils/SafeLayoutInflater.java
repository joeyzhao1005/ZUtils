package com.kit.utils;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

/**
 * @author joeyzhao
 */
public class SafeLayoutInflater {

    @Nullable
    public static LayoutInflater from(@Nullable Context context) {
        if (context == null) {
            return null;
        }

        try {
            return LayoutInflater.from(context);
        } catch (Exception e) {
            return null;
        }
    }
}
