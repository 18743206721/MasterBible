package com.xingguang.master.maincode.mine.model;

/**
 * 创建日期：2018/7/23
 * 描述:考试详情
 * 作者:LiuYu
 */
public class KaoShiInfoBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"ID":29,"Title":"考试机构名称01","Content":"考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01","Sort":1,"AddDate":"2018-07-23T10:05:24","FormatAddDate":"2018-07-23","LastID":0,"NextID":30}
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
         * ID : 29
         * Title : 考试机构名称01
         * Content : 考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01
         * Sort : 1
         * AddDate : 2018-07-23T10:05:24
         * FormatAddDate : 2018-07-23
         * LastID : 0
         * NextID : 30
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
