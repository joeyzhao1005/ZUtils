package com.kit.utils;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.Nullable;

import com.kit.app.application.AppMaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author joeyzhao
 */
public class AssetsUtils extends AssetsTxt2String {
    @Nullable
    public static String getFromAssets(String fileName) {
        Context context = AppMaster.getInstance().getAppContext();
        if (!isFileExists(fileName)) {
            return null;
        }
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line + " \n";
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    public static String getFromAssetsNoNewLines(String fileName) {
        Context context = AppMaster.getInstance().getAppContext();
        if (!isFileExists(fileName)) {
            return null;
        }
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 判断assets文件夹下的文件是否存在
     *
     * @return false 不存在    true 存在
     */
    public static boolean isFileExists(String filename) {
        AssetManager assetManager = AppMaster.getInstance().getAppContext().getResources().getAssets();
        try {
            String[] names = assetManager.list("");
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(filename.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
