package com.mirror.woodpecker.app.util;


import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Message;

import org.xutils.ex.DbException;

import java.util.List;

import dev.mirror.library.android.util.DateUtil;


/**
 * Created by 王沛栋 on 2016/1/27.
 */
public class MessageUtil {

    /**
     * 保存推送
     * @param title
     * @param content
     */
    public static void savePushMessage(String title,String content){
        String time = DateUtil.getStringToday();
        Message m = new Message();
        m.setContent(content);
        m.setTitle(title);
        m.setCreateTime(time);

        try {
            AppContext.mDB().save(m);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取推送消息列表
     * @return
     */
    public static List<Message> getMessageList(){
        try {

            return AppContext.mDB().findAll(Message.class);//查询所有的用户
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取推送详情
     * @param id
     * @return
     */
    public static Message getMessageById(int id){
        try {
            return (AppContext.mDB().findById(Message.class,"id"+id));
        }catch (Exception e){
            return null;
        }
    }
}
