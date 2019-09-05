package com.kit.utils;

import android.graphics.Bitmap;
import androidx.palette.graphics.Palette;

import com.kit.utils.log.Zog;

public class PaletteUtils {

    public static int getColor(Bitmap bitmap, int defaultColor) {
        if (bitmap == null) {
            return defaultColor;
        }
        try {
            Palette palette = Palette.from(bitmap).generate();
            Palette.Swatch swatch = palette.getVibrantSwatch();
            Palette.Swatch swatchDark = palette.getDarkVibrantSwatch();
            Palette.Swatch swatchLight = palette.getLightVibrantSwatch();

            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
            Palette.Swatch mutedSwatchDark = palette.getDarkMutedSwatch();
            Palette.Swatch mutedSwatchLight = palette.getLightMutedSwatch();


            Integer themeColor = null;
            if (swatch != null) {
                themeColor = swatch.getRgb();
            }

            if (themeColor != null) {
                return themeColor;
            }

            if (mutedSwatch != null) {
                themeColor = mutedSwatch.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }

            if (swatchDark != null) {
                themeColor = swatchDark.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (mutedSwatchDark != null) {
                themeColor = mutedSwatchDark.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (swatchLight != null) {
                themeColor = swatchLight.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (mutedSwatchLight != null) {
                themeColor = mutedSwatchLight.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            } else {
                themeColor = defaultColor;
            }
            return themeColor;
        } catch (Exception e) {
            Zog.showException(e);
        }

        return defaultColor;
    }
}
