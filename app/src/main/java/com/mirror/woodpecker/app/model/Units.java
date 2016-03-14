package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016/3/8.
 *
 * 项目单位的实体类
 */
public class Units implements AddrBase{
    private int id;//":"2",
    private String name;//":"郑大一附院"

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddrName() {
        return name;
    }
}
