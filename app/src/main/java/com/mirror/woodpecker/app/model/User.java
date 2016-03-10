package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016/3/10.
 */
public class User {
    public User(){

    }
    private int id;//private String 25private String ,
    private String username;//private String 18837145615private String ,
    private String email;//private String mirror5821@163.comprivate String ,
    private String mobile;//private String 18837145615private String ,
    private int status;//1,
    private int sign;//private String 1private String ,
    private int companyid;//private String 3private String ,
    private int projectid;//private String 0private String ,
    private int systemid;//private String 0private String


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public int getSystemid() {
        return systemid;
    }

    public void setSystemid(int systemid) {
        this.systemid = systemid;
    }
}
