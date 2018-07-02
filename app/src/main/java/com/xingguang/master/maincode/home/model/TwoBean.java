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
     * data : {"PageNum":1,"List":[{"ID":10,"Title":"电工荒的背后是什么？","Content":"电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？","Sort":1,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":11,"Title":"行业资讯测试01","Content":"行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01","Sort":2,"AddDate":"2018-06-05T17:05:33","formatAddDate":"2018-06-05"},{"ID":12,"Title":"行业资讯测试02","Content":"行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02   行业资讯测试02行业资讯测试02行业资讯测试02","Sort":3,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":13,"Title":"测试资讯测试标题01","Content":"测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01      测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01","Sort":4,"AddDate":"2018-06-06T00:00:00","formatAddDate":"2018-06-06"},{"ID":27,"Title":"行业资讯信息推送","Content":"行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送","Sort":99,"AddDate":"2018-06-29T10:14:44","formatAddDate":"2018-06-29"},{"ID":26,"Title":"一样的","Content":"","Sort":99,"AddDate":"2018-06-27T09:16:06","formatAddDate":"2018-06-27"}]}
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
         * List : [{"ID":10,"Title":"电工荒的背后是什么？","Content":"电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？","Sort":1,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":11,"Title":"行业资讯测试01","Content":"行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01","Sort":2,"AddDate":"2018-06-05T17:05:33","formatAddDate":"2018-06-05"},{"ID":12,"Title":"行业资讯测试02","Content":"行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02   行业资讯测试02行业资讯测试02行业资讯测试02","Sort":3,"AddDate":"2018-06-05T00:00:00","formatAddDate":"2018-06-05"},{"ID":13,"Title":"测试资讯测试标题01","Content":"测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01      测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01","Sort":4,"AddDate":"2018-06-06T00:00:00","formatAddDate":"2018-06-06"},{"ID":27,"Title":"行业资讯信息推送","Content":"行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送行业资讯信息推送","Sort":99,"AddDate":"2018-06-29T10:14:44","formatAddDate":"2018-06-29"},{"ID":26,"Title":"一样的","Content":"","Sort":99,"AddDate":"2018-06-27T09:16:06","formatAddDate":"2018-06-27"}]
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
}
