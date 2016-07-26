package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016-07-26.
 */
public class Pay implements Parcelable{
    public Pay(){

    }

    private int id;//21private String ,
    private String uid;//2private String ,
    private String playname;//smmprivate String ,
    private String money;//100.00private String ,
    private String phone;//14752896350private String ,
    private String playurl;//0private String ,
    private String addtime;//1467819947private String ,
    private String playtype;//2private String ,
    private String status;//0private String ,
    private String kefuid;//20private String ,
    private String order_id;//1478523690private String ,
    private String username;//王桦private String

    protected Pay(Parcel in) {
        id = in.readInt();
        uid = in.readString();
        playname = in.readString();
        money = in.readString();
        phone = in.readString();
        playurl = in.readString();
        addtime = in.readString();
        playtype = in.readString();
        status = in.readString();
        kefuid = in.readString();
        order_id = in.readString();
        username = in.readString();
    }

    public static final Creator<Pay> CREATOR = new Creator<Pay>() {
        @Override
        public Pay createFromParcel(Parcel in) {
            return new Pay(in);
        }

        @Override
        public Pay[] newArray(int size) {
            return new Pay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(uid);
        dest.writeString(playname);
        dest.writeString(money);
        dest.writeString(phone);
        dest.writeString(playurl);
        dest.writeString(addtime);
        dest.writeString(playtype);
        dest.writeString(status);
        dest.writeString(kefuid);
        dest.writeString(order_id);
        dest.writeString(username);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlayname() {
        return playname;
    }

    public void setPlayname(String playname) {
        this.playname = playname;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPlaytype() {
        return playtype;
    }

    public void setPlaytype(String playtype) {
        this.playtype = playtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKefuid() {
        return kefuid;
    }

    public void setKefuid(String kefuid) {
        this.kefuid = kefuid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
