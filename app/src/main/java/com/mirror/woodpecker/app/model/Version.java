package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/4/13.
 */
public class Version implements Parcelable{
    private int svnid;//private String 1private String ,
    private String svnname;//private String 1.0private String ,
    private String downurl;//null,
    private int is_update;//private String 0private String

    protected Version(Parcel in) {
        svnid = in.readInt();
        svnname = in.readString();
        downurl = in.readString();
        is_update = in.readInt();
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

    public int getSvnid() {
        return svnid;
    }

    public void setSvnid(int svnid) {
        this.svnid = svnid;
    }

    public String getSvnname() {
        return svnname;
    }

    public void setSvnname(String svnname) {
        this.svnname = svnname;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(svnid);
        dest.writeString(svnname);
        dest.writeString(downurl);
        dest.writeInt(is_update);
    }
}
