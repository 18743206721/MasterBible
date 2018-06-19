package com.xingguang.master.maincode.home.model;

import java.util.List;

/**
 * 创建日期：2018/6/15
 * 描述: 首页实体类数据
 * 作者:LiuYu
 */
public class HomeBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : {"bannerimg":[{"ID":1,"AdsName":"图片一","Url":"#","imgpath":"/UpLoadFiles/Ads/201865154629.png","Sort":1,"AddDate":"2018-06-05T15:46:29"},{"ID":2,"AdsName":"图片二","Url":"#","imgpath":"/UpLoadFiles/Ads/201865154645.png","Sort":2,"AddDate":"2018-06-05T15:46:45"}],"adsense1":[{"LinkName":"广告位上","Url":"6","Title":"8","Column1":"/UpLoadFiles/Ads/201865155925.png","Sort":1,"AddDate":"2018-06-14T16:07:18.9"}],"adsense2":[{"LinkName":"广告位中","Url":"5","Title":"","Column1":"/UpLoadFiles/Ads/201865155934.png","Sort":2,"AddDate":"2018-06-14T16:07:28.237"}],"adsense3":[{"LinkName":"广告位下","Url":"1","Title":"","Column1":"/UpLoadFiles/Ads/201865155942.png","Sort":3,"AddDate":"2018-06-14T16:07:33.693"}],"recruitinfo":[{"JobName":"招聘职位02","DiDian":"地点02","DaiYu":"5000-12000","Sort":2,"AddDate":"2018-06-14T16:19:19"}],"information":[{"ID":10,"Title":"电工荒的背后是什么？","Content":"电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？","Sort":1,"AddDate":"2018-06-05T00:00:00"},{"ID":11,"Title":"行业资讯测试01","Content":"行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01行业资讯测试01","Sort":2,"AddDate":"2018-06-05T17:05:33"},{"ID":12,"Title":"行业资讯测试02","Content":"行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02行业资讯测试02   行业资讯测试02行业资讯测试02行业资讯测试02","Sort":3,"AddDate":"2018-06-05T00:00:00"},{"ID":13,"Title":"测试资讯测试标题01","Content":"测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01      测试资讯测试标题01测试资讯测试标题01测试资讯测试标题01","Sort":4,"AddDate":"2018-06-06T09:08:23"}],"cultivate":[{"ID":14,"Title":"项目培训01测试标题01","Content":"项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01","Sort":1,"AddDate":"2018-06-06T15:57:40"},{"ID":15,"Title":"培训项目02测试标题01","Content":"培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01培训项目02测试标题01","Sort":2,"AddDate":"2018-06-14T17:21:40"}]}
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
        private List<BannerimgBean> bannerimg;
        private List<Adsense1Bean> adsense1;
        private List<Adsense2Bean> adsense2;
        private List<Adsense3Bean> adsense3;
        private List<RecruitinfoBean> recruitinfo;
        private List<InformationBean> information;
        private List<CultivateBean> cultivate;

        public List<BannerimgBean> getBannerimg() {
            return bannerimg;
        }

        public void setBannerimg(List<BannerimgBean> bannerimg) {
            this.bannerimg = bannerimg;
        }

        public List<Adsense1Bean> getAdsense1() {
            return adsense1;
        }

        public void setAdsense1(List<Adsense1Bean> adsense1) {
            this.adsense1 = adsense1;
        }

        public List<Adsense2Bean> getAdsense2() {
            return adsense2;
        }

        public void setAdsense2(List<Adsense2Bean> adsense2) {
            this.adsense2 = adsense2;
        }

        public List<Adsense3Bean> getAdsense3() {
            return adsense3;
        }

        public void setAdsense3(List<Adsense3Bean> adsense3) {
            this.adsense3 = adsense3;
        }

        public List<RecruitinfoBean> getRecruitinfo() {
            return recruitinfo;
        }

        public void setRecruitinfo(List<RecruitinfoBean> recruitinfo) {
            this.recruitinfo = recruitinfo;
        }

        public List<InformationBean> getInformation() {
            return information;
        }

        public void setInformation(List<InformationBean> information) {
            this.information = information;
        }

        public List<CultivateBean> getCultivate() {
            return cultivate;
        }

        public void setCultivate(List<CultivateBean> cultivate) {
            this.cultivate = cultivate;
        }

        public static class BannerimgBean {
            /**
             * ID : 1
             * AdsName : 图片一
             * Url : #
             * imgpath : /UpLoadFiles/Ads/201865154629.png
             * Sort : 1
             * AddDate : 2018-06-05T15:46:29
             */

            private int ID;
            private String AdsName;
            private String Url;
            private String imgpath;
            private int Sort;
            private String AddDate;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getAdsName() {
                return AdsName;
            }

            public void setAdsName(String AdsName) {
                this.AdsName = AdsName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getImgpath() {
                return imgpath;
            }

            public void setImgpath(String imgpath) {
                this.imgpath = imgpath;
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

        public static class Adsense1Bean {
            /**
             * LinkName : 广告位上
             * Url : 6
             * Title : 8
             * Column1 : /UpLoadFiles/Ads/201865155925.png
             * Sort : 1
             * AddDate : 2018-06-14T16:07:18.9
             */

            private String LinkName;
            private String Url;
            private String Title;
            private String Column1;
            private int Sort;
            private String AddDate;

            public String getLinkName() {
                return LinkName;
            }

            public void setLinkName(String LinkName) {
                this.LinkName = LinkName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getColumn1() {
                return Column1;
            }

            public void setColumn1(String Column1) {
                this.Column1 = Column1;
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

        public static class Adsense2Bean {
            /**
             * LinkName : 广告位中
             * Url : 5
             * Title :
             * Column1 : /UpLoadFiles/Ads/201865155934.png
             * Sort : 2
             * AddDate : 2018-06-14T16:07:28.237
             */

            private String LinkName;
            private String Url;
            private String Title;
            private String Column1;
            private int Sort;
            private String AddDate;

            public String getLinkName() {
                return LinkName;
            }

            public void setLinkName(String LinkName) {
                this.LinkName = LinkName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getColumn1() {
                return Column1;
            }

            public void setColumn1(String Column1) {
                this.Column1 = Column1;
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

        public static class Adsense3Bean {
            /**
             * LinkName : 广告位下
             * Url : 1
             * Title :
             * Column1 : /UpLoadFiles/Ads/201865155942.png
             * Sort : 3
             * AddDate : 2018-06-14T16:07:33.693
             */

            private String LinkName;
            private String Url;
            private String Title;
            private String Column1;
            private int Sort;
            private String AddDate;

            public String getLinkName() {
                return LinkName;
            }

            public void setLinkName(String LinkName) {
                this.LinkName = LinkName;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getColumn1() {
                return Column1;
            }

            public void setColumn1(String Column1) {
                this.Column1 = Column1;
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

        public static class RecruitinfoBean {
            /**
             * JobName : 招聘职位02
             * DiDian : 地点02
             * DaiYu : 5000-12000
             * Sort : 2
             * AddDate : 2018-06-14T16:19:19
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

        public static class InformationBean {
            /**
             * ID : 10
             * Title : 电工荒的背后是什么？
             * Content : 电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？电工荒的背后是什么？
             * Sort : 1
             * AddDate : 2018-06-05T00:00:00
             */

            private int ID;
            private String Title;
            private String Content;
            private int Sort;
            private String AddDate;

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
        }

        public static class CultivateBean {
            /**
             * ID : 14
             * Title : 项目培训01测试标题01
             * Content : 项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01项目培训01测试标题01
             * Sort : 1
             * AddDate : 2018-06-06T15:57:40
             */

            private int ID;
            private String Title;
            private String Content;
            private int Sort;
            private String AddDate;

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
        }
    }
}
