package com.xingguang.master.http;

/**
 * 创建日期：2018/7/24
 * 描述:
 * 作者:LiuYu
 */
public class MsgBean {

    /**
     * status : 1
     * msg : 提交成功！
     * data : {"exampaperID":"18072409235614837683"}
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
         * exampaperID : 18072409235614837683
         */

        private String exampaperID;

        public String getExampaperID() {
            return exampaperID;
        }

        public void setExampaperID(String exampaperID) {
            this.exampaperID = exampaperID;
        }
    }
}
