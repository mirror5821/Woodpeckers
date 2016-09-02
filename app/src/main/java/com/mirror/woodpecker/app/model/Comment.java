package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016-08-08.
 */
public class Comment {

    private int id;//":"14",
    private String content;//":"Hhhh",
    private int uid;//":"2",
    private int order_id;//":"63",
    private String addtime;//":"1459324828"

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
