package com.xingguang.master.main.model;

import java.util.List;

/**
 * 创建日期：2018/6/23
 * 描述:推送
 * 作者:LiuYu
 */
public class TuisongBean {


    /**
     * status : 1
     * msg : 操作成功！
     * data : [{"ID":1,"ClientID":"e605a0db5ce3cca9b76b012978064940","AddDate":"2018-06-23T13:30:11"}]
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
         * ID : 1
         * ClientID : e605a0db5ce3cca9b76b012978064940
         * AddDate : 2018-06-23T13:30:11
         */

        private int ID;
        private String ClientID;
        private String AddDate;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getClientID() {
            return ClientID;
        }

        public void setClientID(String ClientID) {
            this.ClientID = ClientID;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }
    }
}
