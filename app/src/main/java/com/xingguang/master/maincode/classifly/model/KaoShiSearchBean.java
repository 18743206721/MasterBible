package com.xingguang.master.maincode.classifly.model;

import java.util.List;

/**
 * 创建日期：2018/7/23
 * 描述:考试项目分类搜索
 * 作者:LiuYu
 */
public class KaoShiSearchBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":10,"Name":"安监局电工","ClassPic":"/UpLoadFiles/Ads/tpl201861294819.png","PIC":"tpl201861294819.png"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 10
         * Name : 安监局电工
         * ClassPic : /UpLoadFiles/Ads/tpl201861294819.png
         * PIC : tpl201861294819.png
         */

        private int ID;
        private String Name;
        private String ClassPic;
        private String PIC;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getClassPic() {
            return ClassPic;
        }

        public void setClassPic(String ClassPic) {
            this.ClassPic = ClassPic;
        }

        public String getPIC() {
            return PIC;
        }

        public void setPIC(String PIC) {
            this.PIC = PIC;
        }
    }
}
