package com.xingguang.master.main.model;

/**
 * 创建日期：2018/6/21
 * 描述: 版本更新
 * 作者:LiuYu
 */
public class UpdateBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"VersionName":"1.0","VersionUrl":"http://192.168.0.150:8035/down/app-debug.apk","Content":"1、解决崩溃,2、解决发热严重,3、解决卡"}
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
         * VersionName : 1.0
         * VersionUrl : http://192.168.0.150:8035/down/app-debug.apk
         * Content : 1、解决崩溃,2、解决发热严重,3、解决卡
         */

        private String VersionName;
        private String VersionUrl;
        private String Content;

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String VersionName) {
            this.VersionName = VersionName;
        }

        public String getVersionUrl() {
            return VersionUrl;
        }

        public void setVersionUrl(String VersionUrl) {
            this.VersionUrl = VersionUrl;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }
    }
}
