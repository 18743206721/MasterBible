package com.xingguang.master.maincode.classifly.model;

import java.util.List;

/**
 * 创建日期：2018/7/23
 * 描述:考试机构
 * 作者:LiuYu
 */
public class KaoshiJIgouBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"PageNum":1,"List":[{"ID":29,"Title":"考试机构名称01","Content":"考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01","Sort":1,"AddDate":"2018-07-23T10:05:24","formatAddDate":"2018-07-23"},{"ID":30,"Title":"考试机构名称02","Content":"考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02","Sort":2,"AddDate":"2018-07-23T10:05:39","formatAddDate":"2018-07-23"},{"ID":31,"Title":"考试机构03","Content":"考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03","Sort":3,"AddDate":"2018-07-23T00:00:00","formatAddDate":"2018-07-23"}]}
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
         * PageNum : 1
         * List : [{"ID":29,"Title":"考试机构名称01","Content":"考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01","Sort":1,"AddDate":"2018-07-23T10:05:24","formatAddDate":"2018-07-23"},{"ID":30,"Title":"考试机构名称02","Content":"考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02考试机构名称02","Sort":2,"AddDate":"2018-07-23T10:05:39","formatAddDate":"2018-07-23"},{"ID":31,"Title":"考试机构03","Content":"考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03考试机构名称03","Sort":3,"AddDate":"2018-07-23T00:00:00","formatAddDate":"2018-07-23"}]
         */

        private int PageNum;
        private java.util.List<ListBean> List;

        public int getPageNum() {
            return PageNum;
        }

        public void setPageNum(int PageNum) {
            this.PageNum = PageNum;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * ID : 29
             * Title : 考试机构名称01
             * Content : 考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01考试机构名称01
             * Sort : 1
             * AddDate : 2018-07-23T10:05:24
             * formatAddDate : 2018-07-23
             */

            private int ID;
            private String Title;
            private String Content;
            private int Sort;
            private String AddDate;
            private String formatAddDate;

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
                return formatAddDate;
            }

            public void setFormatAddDate(String formatAddDate) {
                this.formatAddDate = formatAddDate;
            }
        }
    }
}
