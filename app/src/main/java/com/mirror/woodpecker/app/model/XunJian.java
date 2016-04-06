package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public class XunJian implements Parcelable{
    private int project_id;//private String 10private String ,
    private String project_name;//private String 安阳移动private String ,
    private String start_time;//private String 1457452800private String ,
    private String end_time;//private String 1459353600private String ,
    private String status;//private String 0private String ,
    private String duetime;//private String 1606406400private String ,
    private String guantmoney;//private String 8000private String
    private String picid;

    protected XunJian(Parcel in) {
        project_id = in.readInt();
        project_name = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        status = in.readString();
        duetime = in.readString();
        guantmoney = in.readString();
        picid = in.readString();
    }

    public static final Creator<XunJian> CREATOR = new Creator<XunJian>() {
        @Override
        public XunJian createFromParcel(Parcel in) {
            return new XunJian(in);
        }

        @Override
        public XunJian[] newArray(int size) {
            return new XunJian[size];
        }
    };

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public String getGuantmoney() {
        return guantmoney;
    }

    public void setGuantmoney(String guantmoney) {
        this.guantmoney = guantmoney;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(project_id);
        dest.writeString(project_name);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(status);
        dest.writeString(duetime);
        dest.writeString(guantmoney);
        dest.writeString(picid);
    }
}
