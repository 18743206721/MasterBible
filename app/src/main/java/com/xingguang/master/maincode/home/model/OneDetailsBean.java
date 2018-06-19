package com.xingguang.master.maincode.home.model;

/**
 * 创建日期：2018/6/19
 * 描述:招工详情
 * 作者:LiuYu
 */
public class OneDetailsBean {
    /**
     * status : 1
     * msg : SUCCESS
     * data : {"Content":"<p>\r\n\t招聘职位测试01招聘职位测试01招聘职位测试01招聘职位测试01\r\n<\/p>\r\n<p>\r\n\t&nbsp; 招聘职位测试01招聘职位测试01招聘职位测试01招聘职位测试01\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t招聘职位测试01招聘职位测试01招聘职位测试01\r\n<\/p>","DiDian":"地点01","GZNianXian":"","XueLi":"","ShiJian":"","DaiYu":"面议","BeiZhu":"True","ID":1,"JobName":"招聘职位测试01","Request":"","Sort":1,"AddDate":"2018-06-15T13:12:25"}
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
         * Content : <p>
         招聘职位测试01招聘职位测试01招聘职位测试01招聘职位测试01
         </p>
         <p>
         &nbsp; 招聘职位测试01招聘职位测试01招聘职位测试01招聘职位测试01
         </p>
         <p>
         <br />
         </p>
         <p>
         招聘职位测试01招聘职位测试01招聘职位测试01
         </p>
         * DiDian : 地点01
         * GZNianXian :
         * XueLi :
         * ShiJian :
         * DaiYu : 面议
         * BeiZhu : True
         * ID : 1
         * JobName : 招聘职位测试01
         * Request :
         * Sort : 1
         * AddDate : 2018-06-15T13:12:25
         */

        private String Content;
        private String DiDian;
        private String GZNianXian;
        private String XueLi;
        private String ShiJian;
        private String DaiYu;
        private String BeiZhu;
        private int ID;
        private String JobName;
        private String Request;
        private int Sort;
        private String AddDate;

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getDiDian() {
            return DiDian;
        }

        public void setDiDian(String DiDian) {
            this.DiDian = DiDian;
        }

        public String getGZNianXian() {
            return GZNianXian;
        }

        public void setGZNianXian(String GZNianXian) {
            this.GZNianXian = GZNianXian;
        }

        public String getXueLi() {
            return XueLi;
        }

        public void setXueLi(String XueLi) {
            this.XueLi = XueLi;
        }

        public String getShiJian() {
            return ShiJian;
        }

        public void setShiJian(String ShiJian) {
            this.ShiJian = ShiJian;
        }

        public String getDaiYu() {
            return DaiYu;
        }

        public void setDaiYu(String DaiYu) {
            this.DaiYu = DaiYu;
        }

        public String getBeiZhu() {
            return BeiZhu;
        }

        public void setBeiZhu(String BeiZhu) {
            this.BeiZhu = BeiZhu;
        }

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

        public String getRequest() {
            return Request;
        }

        public void setRequest(String Request) {
            this.Request = Request;
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
