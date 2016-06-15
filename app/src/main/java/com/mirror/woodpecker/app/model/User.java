package com.mirror.woodpecker.app.model;

/**
 * Created by 王沛栋 on 2016/3/10.
 * 用户的实体类
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
    private int role_id;

    private String password;//private String 917946d9ca02fc80a1264e66a8469b48private String ,
    private String reg_time;//private String 1449560478private String ,
    private String reg_ip;//private String 2130706433private String ,
    private String last_login_time;//private String 1458631087private String ,
    private String last_login_ip;//private String 29402902private String ,
    private String update_time;//private String 1449560478private String ,
    private String companyname;//null,
    private String projectname;//null,
    private String systemname;//null,
    private int jobtype;//3,
    private String jobname;//private String 普通会员private String ,
    private int count;//private String 0private String ,
    private int donecount;//private String 0private String ,
    private int oncount;//0
    private String headimg;

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

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getReg_ip() {
        return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public int getJobtype() {
        return jobtype;
    }

    public void setJobtype(int jobtype) {
        this.jobtype = jobtype;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDonecount() {
        return donecount;
    }

    public void setDonecount(int donecount) {
        this.donecount = donecount;
    }

    public int getOncount() {
        return oncount;
    }

    public void setOncount(int oncount) {
        this.oncount = oncount;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
