package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 王沛栋 on 2016/3/11.
 * ext_info 对应的就是提示语数组
 *
 */
public class Repair2 implements Parcelable{
    private int order_id;//private String 120private String ,
    private String phone;//private String 18837145615private String ,
    private String gz_postion;//private String \u5317\u4eac\u5e02\u5317\u4eac\u5e02\u4e1c\u57ce\u533a\u5317\u4eac\u5e02\u4e1c\u57ce\u533a\u897f\u957f\u5b89\u8857private String ,
    private String gz_desc;//private String 55555private String ,
    private int uid;//private String 25private String ,
    private int order_type_id;//private String 0private String ,
    private int project_id;//项目id,
    private int order_status;//订单状态
    private int order_cat;//订单类型id ,
    private String prev_img;//private String 0private String ,
    private String last_img;//private String 0private String ,
    private String device_name;//private String private String ,
    private String addtime;//private String 1457660094private String ,
    private String endtime;//private String 0private String ,
    private int repair_id;//private String 0private String ,
    private String kefu_id;//private String 0private String ,
    private String detail_desc;//private String private String ,
    private String solution_method;//private String private String ,
    private String device_id;//private String 0private String ,
    private String repair_price;//private String 0.00private String ,
    private String [] ext_info;//private String private String
    private String project_name;
    private String type_name;
    private String catname;
    private String comment;
    private int sign;
    private String repairname;

    protected Repair2(Parcel in) {
        order_id = in.readInt();
        phone = in.readString();
        gz_postion = in.readString();
        gz_desc = in.readString();
        uid = in.readInt();
        order_type_id = in.readInt();
        project_id = in.readInt();
        order_status = in.readInt();
        order_cat = in.readInt();
        prev_img = in.readString();
        last_img = in.readString();
        device_name = in.readString();
        addtime = in.readString();
        endtime = in.readString();
        repair_id = in.readInt();
        kefu_id = in.readString();
        detail_desc = in.readString();
        solution_method = in.readString();
        device_id = in.readString();
        repair_price = in.readString();
//        ext_info = in.readString();
        project_name = in.readString();
        type_name = in.readString();
        catname = in.readString();
        comment = in.readString();
        sign = in.readInt();
        repairname = in.readString();
    }

    public static final Creator<Repair2> CREATOR = new Creator<Repair2>() {
        @Override
        public Repair2 createFromParcel(Parcel in) {
            return new Repair2(in);
        }

        @Override
        public Repair2[] newArray(int size) {
            return new Repair2[size];
        }
    };

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGz_postion() {
        return gz_postion;
    }

    public void setGz_postion(String gz_postion) {
        this.gz_postion = gz_postion;
    }

    public String getGz_desc() {
        return gz_desc;
    }

    public void setGz_desc(String gz_desc) {
        this.gz_desc = gz_desc;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOrder_type_id() {
        return order_type_id;
    }

    public void setOrder_type_id(int order_type_id) {
        this.order_type_id = order_type_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getOrder_cat() {
        return order_cat;
    }

    public void setOrder_cat(int order_cat) {
        this.order_cat = order_cat;
    }

    public String getPrev_img() {
        return prev_img;
    }

    public void setPrev_img(String prev_img) {
        this.prev_img = prev_img;
    }

    public String getLast_img() {
        return last_img;
    }

    public void setLast_img(String last_img) {
        this.last_img = last_img;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(int repair_id) {
        this.repair_id = repair_id;
    }

    public String getKefu_id() {
        return kefu_id;
    }

    public void setKefu_id(String kefu_id) {
        this.kefu_id = kefu_id;
    }

    public String getDetail_desc() {
        return detail_desc;
    }

    public void setDetail_desc(String detail_desc) {
        this.detail_desc = detail_desc;
    }

    public String getSolution_method() {
        return solution_method;
    }

    public void setSolution_method(String solution_method) {
        this.solution_method = solution_method;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getRepair_price() {
        return repair_price;
    }

    public void setRepair_price(String repair_price) {
        this.repair_price = repair_price;
    }

    public String [] getExt_info() {
        return ext_info;
    }

    public void setExt_info(String [] ext_info) {
        this.ext_info = ext_info;
    }

    public String getRepairname() {
        return repairname;
    }

    public void setRepairname(String repairname) {
        this.repairname = repairname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeString(phone);
        dest.writeString(gz_postion);
        dest.writeString(gz_desc);
        dest.writeInt(uid);
        dest.writeInt(order_type_id);
        dest.writeInt(project_id);
        dest.writeInt(order_status);
        dest.writeInt(order_cat);
        dest.writeString(prev_img);
        dest.writeString(last_img);
        dest.writeString(device_name);
        dest.writeString(addtime);
        dest.writeString(endtime);
        dest.writeInt(repair_id);
        dest.writeString(kefu_id);
        dest.writeString(detail_desc);
        dest.writeString(solution_method);
        dest.writeString(device_id);
        dest.writeString(repair_price);
//        dest.writeString(ext_info);
        dest.writeString(project_name);
        dest.writeString(type_name);
        dest.writeString(catname);
        dest.writeString(comment);
        dest.writeInt(sign);
        dest.writeString(repairname);
    }
}
