package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/19
 * 描述: 行业资讯更多列表
 * 作者:LiuYu
 */
public class TwoBean {
    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":10,"Title":"电工荒的背后是什么？","Content":"电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？","Sort":1,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":11,"Title":"行业资讯测试01","Content":"行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01","Sort":2,"AddDate":"2018-06-05T17:05:33","formatAddDate":"2018-06-05"},{"ID":12,"Title":"行业资讯测试02","Content":"行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02   行业资讯测试02行业资讯测试02行业资讯测试02","Sort":3,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":13,"Title":"测试资讯测试标题01","Content":"测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01      测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01","Sort":4,"AddDate":"2018-06-06T00:00:00","formatAddDate":"2018-06-06"}]
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
         * Title : 电工荒的背后是什么？
         * Content : 电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？
         * Sort : 1
         * AddDate : 2018-06-05T00:00:00
         * formatAddDate : 2018-06-05
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
