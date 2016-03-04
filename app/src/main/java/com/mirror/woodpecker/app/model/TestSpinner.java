package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016/3/4.
 */
public class TestSpinner implements AddrBase{
    private int id;
    private String name;

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
