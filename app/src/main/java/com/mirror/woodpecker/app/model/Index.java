package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/5/9.
 */
public class Index implements Parcelable{
    public Index(){

    }
    private int id;//":"2",
    private String path;//":
    private String url;

    protected Index(Parcel in) {
        id = in.readInt();
        path = in.readString();
        url = in.readString();
    }

    public static final Creator<Index> CREATOR = new Creator<Index>() {
        @Override
        public Index createFromParcel(Parcel in) {
            return new Index(in);
        }

        @Override
        public Index[] newArray(int size) {
            return new Index[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(url);
    }
}
