package com.mirror.woodpecker.app.util;

import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.cache.PhoneCache;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by 王沛栋 on 2016-06-22.
 * 数据库操作类
 */
public class DBUtil {
    /**
     * 存储录入的手机号码
     * @param num
     */
    public static void savePhoneCache(String num){
        DbManager db = x.getDb(AppContext.getDaoConfig());

        PhoneCache p = new PhoneCache();
        p.setNum(num);

        try {
            db.save(p);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机号码
     * @return
     */
    public static List<PhoneCache> getPhoneCache(){
        DbManager db = x.getDb(AppContext.getDaoConfig());
        try {
            return db.selector(PhoneCache.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
