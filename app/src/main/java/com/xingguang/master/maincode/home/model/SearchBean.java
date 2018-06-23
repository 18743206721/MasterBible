package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/22
 * 描述:搜索bean
 * 作者:LiuYu
 */
public class SearchBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":"14","title":"项目培训01测试标题01","type":"6","typename":"培训项目"},{"ID":"15","title":"培训项目02测试标题01","type":"6","typename":"培训项目"}]
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
         * ID : 14
         * title : 项目培训01测试标题01
         * type : 6
         * typename : 培训项目
         */

        private String ID;
        private String title;
        private String type;
        private String typename;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }
    }
}
