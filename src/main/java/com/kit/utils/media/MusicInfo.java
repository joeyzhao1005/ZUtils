package com.kit.utils.media;

import android.os.Parcel;
import android.os.Parcelable;

//下面是自定义的一个MusicInfo子类，实现了Parcelable，为的是可以将整个MusicInfo的ArrayList在Activity和Service中传送，=_=!!,但其实不用
public class MusicInfo implements Parcelable {
    private long id;
    private String title;
    private String album;
    private int duration;
    private long size;
    private String artist;
    private String url;

    public MusicInfo() {

    }

    public MusicInfo(long pId, String pTitle) {
        id = pId;
        title = pTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(artist);
        dest.writeString(url);
        dest.writeInt(duration);
        dest.writeLong(size);
    }

    public static final Parcelable.Creator<MusicInfo>
            CREATOR = new Creator<MusicInfo>() {

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }

        @Override
        public MusicInfo createFromParcel(Parcel source) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setId(source.readLong());
            musicInfo.setTitle(source.readString());
            musicInfo.setAlbum(source.readString());
            musicInfo.setArtist(source.readString());
            musicInfo.setUrl(source.readString());
            musicInfo.setDuration(source.readInt());
            musicInfo.setSize(source.readLong());
            return musicInfo;
        }
    };
}