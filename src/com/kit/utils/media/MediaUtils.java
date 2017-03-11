package com.kit.utils.media;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.kit.utils.ResWrapper;
import com.kit.utils.StringUtils;
import com.kit.utils.log.ZogUtils;

/**
 * Created by Zhao on 16/8/1.
 */
public class MediaUtils {


    /**
     * @param filePath 文件路径，like XXX/XXX/XX.mp3
     * @return 专辑封面bitmap
     * @Description 获取专辑封面
     */
    @TargetApi(10)
    public static Drawable createAlbumArt(final String filePath) {
        Bitmap bitmap = null;
        //能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath); //设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Drawable drawable = null;
        if (bitmap != null) {
            drawable = new BitmapDrawable(ResWrapper.getInstance().getResources(), bitmap);
        }
        return drawable;
    }

    public static void launchMusicPlayer(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= 15) {
            Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MUSIC);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                launchMusicPlayerBellowApi15(context);
            }
        } else {
            launchMusicPlayerBellowApi15(context);
        }

    }


    private static void launchMusicPlayerBellowApi15(Context context) {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");//Min SDK 8
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e1) {
            Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            try {
                context.startActivity(it);
            } catch (ActivityNotFoundException e2) {
            }
        }
    }

    public static void playMusic(String dir) {
        if (StringUtils.isEmptyOrNullOrNullStr(dir))
            return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Intent it = new Intent(Intent.ACTION_VIEW);
            it.setDataAndType(Uri.parse("file://" + dir), "audio/mp3");
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            try {
                ResWrapper.getInstance().getContext().startActivity(it);
            } catch (ActivityNotFoundException e) {
                ZogUtils.showException(e);
            }
        } else {
            Context context = ResWrapper.getInstance().getContext();
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, dir);
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            Intent it = new Intent(Intent.ACTION_VIEW);
            it.addCategory("android.intent.category.APP_MUSIC");
            it.setDataAndType(uri, "audio/*");
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            Intent chooser = Intent.createChooser(it,
                    "");

            try {
                ResWrapper.getInstance().getContext().startActivity(it);
            } catch (ActivityNotFoundException e) {
                ZogUtils.showException(e);
            }
        }
    }

    public static void playVideo(String url) {
        if (StringUtils.isEmptyOrNullOrNullStr(url))
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "video/mp4");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            ResWrapper.getInstance().getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ZogUtils.showException(e);
        }
    }
}
