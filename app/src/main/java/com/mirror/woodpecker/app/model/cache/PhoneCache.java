package com.mirror.woodpecker.app.model.cache;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by 王沛栋 on 2016-06-22.
 */
@Table(name="phone_cache")
public class PhoneCache {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "num")
    private String num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
