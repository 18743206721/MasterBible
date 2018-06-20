package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/20
 * 描述:
 * 作者:LiuYu
 */
public class ProgramsBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":7,"ClassName":"培训项目01","data":[{"ID":14,"Title":"项目培训01测试标题01","Content":"项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01","Sort":1,"AddDate":"2018-06-06T15:57:40","formatAddDate":"2018-06-06"},{"ID":16,"Title":"测试测试测试","Content":"&lt;p&gt;\r\n\t测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\r\n&lt;/p&gt;\r\n&lt;p&gt;\r\n\t测试测试测试测试测试测试测试测试测试\r\n&lt;/p&gt;","Sort":2,"AddDate":"2018-06-19T14:00:21","formatAddDate":"2018-06-19"}]},{"ID":8,"ClassName":"培训项目02","data":[{"ID":15,"Title":"培训项目02测试标题01","Content":"培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01","Sort":2,"AddDate":"2018-06-14T17:21:40","formatAddDate":"2018-06-14"}]},{"ID":0,"ClassName":"培训报名","data":[{"ID":1,"Name":"安监局","data":[{"ID":10,"Name":"安监局电工"},{"ID":11,"Name":"安监局瓦工"},{"ID":12,"Name":"安监局技工"},{"ID":21,"Name":"安监局测试试题"}]},{"ID":2,"Name":"质监局","data":[{"ID":13,"Name":"质监局电工"},{"ID":14,"Name":"质监局瓦工"},{"ID":16,"Name":"质监局技工"},{"ID":17,"Name":"质监局技工01"}]}]}]
     */

    private int status;
    private String msg;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * ID : 7
         * ClassName : 培训项目01
         * data : [{"ID":14,"Title":"项目培训01测试标题01","Content":"项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01","Sort":1,"AddDate":"2018-06-06T15:57:40","formatAddDate":"2018-06-06"},{"ID":16,"Title":"测试测试测试","Content":"&lt;p&gt;\r\n\t测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\r\n&lt;/p&gt;\r\n&lt;p&gt;\r\n\t测试测试测试测试测试测试测试测试测试\r\n&lt;/p&gt;","Sort":2,"AddDate":"2018-06-19T14:00:21","formatAddDate":"2018-06-19"}]
         */

        private int ID;
        private String ClassName;
        private List<DataBean> data;

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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * ID : 14
             * Title : 项目培训01测试标题01
             * Content : 项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01
             * Sort : 1
             * AddDate : 2018-06-06T15:57:40
             * formatAddDate : 2018-06-06
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
