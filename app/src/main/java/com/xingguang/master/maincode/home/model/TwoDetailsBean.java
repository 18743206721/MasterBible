package com.xingguang.master.maincode.home.model;

/**
 * 创建日期：2018/6/19
 * 描述:行业资讯详情页面
 * 作者:LiuYu
 */
public class TwoDetailsBean {
    /**
     * status : 1
     * msg : SUCCESS
     * data : {"ID":13,"Title":"测试资讯测试标题01","Content":"<p>\r\n\t测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01\r\n<\/p>\r\n<p>\r\n\t&nbsp;&nbsp;测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01\r\n<\/p>","Sort":4,"AddDate":"2018-06-06T00:00:00","FormatAddDate":"2018-06-06","LastID":12,"NextID":0}
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
         * ID : 13
         * Title : 测试资讯测试标题01
         * Content : <p>
         测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01
         </p>
         <p>
         &nbsp;&nbsp;测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01
         </p>
         * Sort : 4
         * AddDate : 2018-06-06T00:00:00
         * FormatAddDate : 2018-06-06
         * LastID : 12
         * NextID : 0
         */

        private int ID;
        private String Title;
        private String Content;
        private int Sort;
        private String AddDate;
        private String FormatAddDate;
        private int LastID;
        private int NextID;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }

        public String getFormatAddDate() {
            return FormatAddDate;
        }

        public void setFormatAddDate(String FormatAddDate) {
            this.FormatAddDate = FormatAddDate;
        }

        public int getLastID() {
            return LastID;
        }

        public void setLastID(int LastID) {
            this.LastID = LastID;
        }

        public int getNextID() {
            return NextID;
        }

        public void setNextID(int NextID) {
            this.NextID = NextID;
        }
    }
}
