package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/3/15.
 */
public class Repairman implements AddrBase ,Parcelable{
    private int uid;//":"2",
    private String nickname;//":"smm"

    protected Repairman(Parcel in) {
        uid = in.readInt();
        nickname = in.readString();
    }

    public static final Creator<Repairman> CREATOR = new Creator<Repairman>() {
        @Override
        public Repairman createFromParcel(Parcel in) {
            return new Repairman(in);
        }

        @Override
        public Repairman[] newArray(int size) {
            return new Repairman[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getAddrName() {
        return nickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(nickname);
    }
}
