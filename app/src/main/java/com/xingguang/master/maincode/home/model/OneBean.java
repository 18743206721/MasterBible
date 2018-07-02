package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/19
 * 描述:更多招工信息
 * 作者:LiuYu
 */
public class OneBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"PageNum":1,"List":[{"ID":1,"JobName":"招聘职位测试01","DiDian":"地点01","DaiYu":"面议","Sort":1,"AddDate":"2018-06-15T13:12:25"},{"ID":2,"JobName":"招聘职位02","DiDian":"地点02","DaiYu":"5000-12000","Sort":2,"AddDate":"2018-06-14T16:19:19"}]}
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
         * List : [{"ID":1,"JobName":"招聘职位测试01","DiDian":"地点01","DaiYu":"面议","Sort":1,"AddDate":"2018-06-15T13:12:25"},{"ID":2,"JobName":"招聘职位02","DiDian":"地点02","DaiYu":"5000-12000","Sort":2,"AddDate":"2018-06-14T16:19:19"}]
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
             * ID : 1
             * JobName : 招聘职位测试01
             * DiDian : 地点01
             * DaiYu : 面议
             * Sort : 1
             * AddDate : 2018-06-15T13:12:25
             */

            private int ID;
            private String JobName;
            private String DiDian;
            private String DaiYu;
            private int Sort;
            private String AddDate;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getJobName() {
                return JobName;
            }

            public void setJobName(String JobName) {
                this.JobName = JobName;
            }

            public String getDiDian() {
                return DiDian;
            }

            public void setDiDian(String DiDian) {
                this.DiDian = DiDian;
            }

            public String getDaiYu() {
                return DaiYu;
            }

            public void setDaiYu(String DaiYu) {
                this.DaiYu = DaiYu;
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
        }
    }
}
