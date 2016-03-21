package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class Complain implements Parcelable,AddrBase{
    private int id;//private String 1private String ,
    private String content;//private String 5private String ,
    private String addtime;//private String 1458528739private String ,
    private int uid;//private String 25private String ,
    private int pid;//private String 0private String
    private String uname;//":"18837145615"

    protected Complain(Parcel in) {
        id = in.readInt();
        content = in.readString();
        addtime = in.readString();
        uid = in.readInt();
        pid = in.readInt();
        uname = in.readString();
    }

    public static final Creator<Complain> CREATOR = new Creator<Complain>() {
        @Override
        public Complain createFromParcel(Parcel in) {
            return new Complain(in);
        }

        @Override
        public Complain[] newArray(int size) {
            return new Complain[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeString(addtime);
        dest.writeInt(uid);
        dest.writeInt(pid);
        dest.writeString(uname);
    }

    @Override
    public String getAddrName() {
        return uname+":"+content;
    }
}
