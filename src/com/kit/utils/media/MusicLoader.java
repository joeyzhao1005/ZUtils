package com.kit.utils.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;

import com.kit.utils.ResWrapper;
import com.kit.utils.log.ZogUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicLoader {

    private static final String TAG = "com.example.nature.MusicLoader";

    private static List<MusicInfo> musicList = new ArrayList<MusicInfo>();

    private static MusicLoader musicLoader;

    private static ContentResolver contentResolver;
    //Uri，指向external的database
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and _display_name <> 'audio' and is_music > 0 ";
    private String sortOrder = Media.DATA;

    public static MusicLoader getInstance() {
        if (musicLoader == null) {
            contentResolver = ResWrapper.getInstance().getContext().getContentResolver();
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    private MusicLoader() {                                                                                                               //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中

    }

    public List<MusicInfo> getMusicList() {
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
        if (cursor == null) {
            ZogUtils.e("Music Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            ZogUtils.e("Music Loader cursor.moveToFirst() returns false.");
        } else {
            cursor.moveToFirst();

            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            do {
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicInfo musicInfo = new MusicInfo(id, title);
                musicInfo.setAlbum(album);
                musicInfo.setDuration(duration);
                musicInfo.setSize(size);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicList.add(musicInfo);

            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();

        return musicList;
    }


    public List<MusicInfo> getMusicInfoByName(String name) {
        where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and _display_name like '%"
                + name
                + "%' and is_music > 0 ";
        Cursor cursor = contentResolver.query(contentUri, projection, where, null, sortOrder);
        if (cursor == null) {
            ZogUtils.e("Music Loader cursor == null.");
        } else if (!cursor.moveToFirst()) {
            ZogUtils.e("Music Loader cursor.moveToFirst() returns false.");
        } else {
            cursor.moveToFirst();

            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            do {
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicInfo musicInfo = new MusicInfo(id, title);
                musicInfo.setAlbum(album);
                musicInfo.setDuration(duration);
                musicInfo.setSize(size);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicList.add(musicInfo);

            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();

        return musicList;
    }

    public Uri getMusicUriById(long id) {
        Uri uri = ContentUris.withAppendedId(contentUri, id);
        return uri;
    }


}
