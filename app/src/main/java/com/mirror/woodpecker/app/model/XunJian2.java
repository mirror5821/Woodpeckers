package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public class XunJian2 implements Parcelable{
    private int picid;//17private String ,
    private String url;///Uploads/Picture/appimg/A477C1C801215.jpgprivate String ,
    private int project_id;//12private String ,
    private String addtime;//1467618056private String ,
    private String project_name;//九院private String

    public int getPicid() {
        return picid;
    }

    public void setPicid(int picid) {
        this.picid = picid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    protected XunJian2(Parcel in) {
        picid = in.readInt();
        url = in.readString();
        project_id = in.readInt();
        addtime = in.readString();
        project_name = in.readString();
    }

    public static final Creator<XunJian2> CREATOR = new Creator<XunJian2>() {
        @Override
        public XunJian2 createFromParcel(Parcel in) {
            return new XunJian2(in);
        }

        @Override
        public XunJian2[] newArray(int size) {
            return new XunJian2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(picid);
        dest.writeString(url);
        dest.writeInt(project_id);
        dest.writeString(addtime);
        dest.writeString(project_name);
    }
}
