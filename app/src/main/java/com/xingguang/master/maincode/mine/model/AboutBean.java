package com.xingguang.master.maincode.mine.model;

/**
 * 创建日期：2018/6/20
 * 描述:关于我们
 * 作者:LiuYu
 */
public class AboutBean {


    /**
     * status : 1
     * msg : SUCCESS
     * data : {"ID":1,"ClassName":"关于我们","Content":"<p>\r\n\t<span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span> \r\n<\/p>\r\n<p>\r\n\t&nbsp; <span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span><span id=\"lblclassname\">关于我们<\/span> \r\n<\/p>","EmailAddress":"123456789@qq.com","Phone":"13800001111","Copyright":"Copyright "}
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
         * ID : 1
         * ClassName : 关于我们
         * Content : <p>
         <span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span>
         </p>
         <p>
         &nbsp; <span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span><span id="lblclassname">关于我们</span>
         </p>
         * EmailAddress : 123456789@qq.com
         * Phone : 13800001111
         * Copyright : Copyright
         */

        private int ID;
        private String ClassName;
        private String Content;
        private String EmailAddress;
        private String Phone;
        private String Copyright;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getEmailAddress() {
            return EmailAddress;
        }

        public void setEmailAddress(String EmailAddress) {
            this.EmailAddress = EmailAddress;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getCopyright() {
            return Copyright;
        }

        public void setCopyright(String Copyright) {
            this.Copyright = Copyright;
        }
    }
}
