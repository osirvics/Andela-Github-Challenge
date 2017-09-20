package com.album.andela_github_challenge;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

/**
 * Created by Victor on 3/7/2017.
 */

public class Utility {

    public static int getBlackWhiteColor(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness >= 0.5) {
            return Color.WHITE;
        } else return Color.BLACK;
    }

    public static int[] getAvailableColor(Context context, Palette palette) {
        int[] temp = new int[1];
        if (palette.getVibrantSwatch() != null) {
            temp[0] = palette.getVibrantSwatch().getRgb();
        }
        else if (palette.getDarkVibrantSwatch() != null) {
            temp[0] = palette.getDarkVibrantSwatch().getRgb();
        }
        else if (palette.getMutedSwatch() != null) {
            temp[0] = palette.getMutedSwatch().getRgb();

        }else if (palette.getLightMutedSwatch() != null) {
            temp[0] = palette.getLightMutedSwatch().getRgb();

        }
        else if (palette.getDarkMutedSwatch() != null) {
            temp[0] = palette.getDarkMutedSwatch().getRgb();
        }

        else{
            temp[0] = ContextCompat.getColor(context, R.color.colorPrimary);
        }
        return temp;
    }
}
