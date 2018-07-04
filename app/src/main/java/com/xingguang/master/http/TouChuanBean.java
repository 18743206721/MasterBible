package com.xingguang.master.http;

/**
 * 创建日期：2018/7/3
 * 描述:
 * 作者:LiuYu
 */
public class TouChuanBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"Title":null,"Text":null,"InfoID":2,"TypeID":1}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Title : null
         * Text : null
         * InfoID : 2
         * TypeID : 1
         */

        private String Title;
        private String Text;
        private int InfoID;
        private int TypeID;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public int getInfoID() {
            return InfoID;
        }

        public void setInfoID(int infoID) {
            InfoID = infoID;
        }

        public int getTypeID() {
            return TypeID;
        }

        public void setTypeID(int typeID) {
            TypeID = typeID;
        }
    }
}