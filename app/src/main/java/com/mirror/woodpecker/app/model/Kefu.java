package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016/4/13.
 */
public class Kefu  implements AddrBase{
    private int id;
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAddrName() {
        return username;
    }
}
