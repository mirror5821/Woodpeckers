package com.mirror.woodpecker.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class About implements AddrBase ,Parcelable{
    public About(){

    }
    private int id;//private String 39private String ,
    private String name;//private String gywmprivate String ,
    private String title;//private String \u5544\u6728\u9e1f\u5e73\u53f0\u4ecb\u7ecdprivate String ,
    private int pid;//private String 0private String ,
    private int sort;//private String 0private String ,
    private int list_row;//private String 10private String ,
    private String meta_title;//private String private String ,
    private String keywords;//private String private String ,
    private String description;//private String private String ,
    private String model;//private String 2private String ,
    private String type;//private String 2,1,3private String ,
    private String link_id;//private String 0private String ,
    private String create_time;//private String 1448270900private String ,
    private String update_time;//private String 1454146122private String ,
    private String status;//private String 1private String ,
    private int icon;//1,
    private Info info;
    private List<Other> list;
    private String aurl;


    protected About(Parcel in) {
        id = in.readInt();
        name = in.readString();
        title = in.readString();
        pid = in.readInt();
        sort = in.readInt();
        list_row = in.readInt();
        meta_title = in.readString();
        keywords = in.readString();
        description = in.readString();
        model = in.readString();
        type = in.readString();
        link_id = in.readString();
        create_time = in.readString();
        update_time = in.readString();
        status = in.readString();
        icon = in.readInt();
        aurl = in.readString();
    }

    public static final Creator<About> CREATOR = new Creator<About>() {
        @Override
        public About createFromParcel(Parcel in) {
            return new About(in);
        }

        @Override
        public About[] newArray(int size) {
            return new About[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeInt(pid);
        dest.writeInt(sort);
        dest.writeInt(list_row);
        dest.writeString(meta_title);
        dest.writeString(keywords);
        dest.writeString(description);
        dest.writeString(model);
        dest.writeString(type);
        dest.writeString(link_id);
        dest.writeString(create_time);
        dest.writeString(update_time);
        dest.writeString(status);
        dest.writeInt(icon);
        dest.writeString(aurl);
    }

    public static class Info implements Parcelable{
        private int id;//":"17",
        private int parse;//":"0",
        private String content;//":"
        private String template;//":"",
        private String bookmark;//":"0"
        private String aurl;

        protected Info(Parcel in) {
            id = in.readInt();
            parse = in.readInt();
            content = in.readString();
            template = in.readString();
            bookmark = in.readString();
            aurl = in.readString();
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParse() {
            return parse;
        }

        public void setParse(int parse) {
            this.parse = parse;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getAurl() {
            return aurl;
        }

        public void setAurl(String aurl) {
            this.aurl = aurl;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(parse);
            dest.writeString(content);
            dest.writeString(template);
            dest.writeString(bookmark);
            dest.writeString(aurl);
        }
    }


    public static class Other implements Parcelable{
        private int id;//":"60",
        private String img;//":"\/Uploads\/Picture\/Link\/2016-01-29\/56ab1c69a6549.jpg",
        private String link;//":"\
        private String title;//":"2016\u4e2d\u56fd\u56fd\u9645\u667a\u80fd\u5efa\u7b51\u5c55\u89c8\u4f1a"

        private Info info;
        private String aurl;


        protected Other(Parcel in) {
            id = in.readInt();
            img = in.readString();
            link = in.readString();
            title = in.readString();
            info = in.readParcelable(Info.class.getClassLoader());
            aurl = in.readString();
        }

        public static final Creator<Other> CREATOR = new Creator<Other>() {
            @Override
            public Other createFromParcel(Parcel in) {
                return new Other(in);
            }

            @Override
            public Other[] newArray(int size) {
                return new Other[size];
            }
        };

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        public String getAurl() {
            return aurl;
        }

        public void setAurl(String aurl) {
            this.aurl = aurl;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(img);
            dest.writeString(link);
            dest.writeString(title);
            dest.writeParcelable(info, flags);
            dest.writeString(aurl);
        }
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getList_row() {
        return list_row;
    }

    public void setList_row(int list_row) {
        this.list_row = list_row;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Other> getList() {
        return list;
    }

    public void setList(List<Other> list) {
        this.list = list;
    }

    public String getAurl() {
        return aurl;
    }

    public void setAurl(String aurl) {
        this.aurl = aurl;
    }

    @Override
    public String getAddrName() {
        return title;
    }
}
