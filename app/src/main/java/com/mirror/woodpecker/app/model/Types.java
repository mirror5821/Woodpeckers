package com.mirror.woodpecker.app.model;

import java.util.List;

/**
 * Created by 王沛栋 on 2016-07-30.
 */
public class Types {
    private List<TypeList>  typelist;
    private List<ProjectList> projectlist;

    public class TypeList implements AddrBase{
        private int id;//":"13",
        private String type_name;//"

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getAddrName() {
            return type_name;
        }
    }
    public class ProjectList implements AddrBase{
        private int project_id;//":"3",
        private String project_name;//":"漯河市妇幼保健院",
        private String start_time;//":"0",
        private String end_time;//":"0",
        private String status;//":"0",
        private String duetime;//":"0",
        private String guantmoney;//":"0",
        private String picid;//":"0"

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDuetime() {
            return duetime;
        }

        public void setDuetime(String duetime) {
            this.duetime = duetime;
        }

        public String getGuantmoney() {
            return guantmoney;
        }

        public void setGuantmoney(String guantmoney) {
            this.guantmoney = guantmoney;
        }

        public String getPicid() {
            return picid;
        }

        public void setPicid(String picid) {
            this.picid = picid;
        }

        @Override
        public String getAddrName() {
            return project_name;
        }
    }

    public List<TypeList> getTypelist() {
        return typelist;
    }

    public void setTypelist(List<TypeList> typelist) {
        this.typelist = typelist;
    }

    public List<ProjectList> getProjectlist() {
        return projectlist;
    }

    public void setProjectlist(List<ProjectList> projectlist) {
        this.projectlist = projectlist;
    }
}
