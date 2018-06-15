package com.xingguang.master.login.model;

import java.util.List;

/**
 * 创建日期：2018/6/15
 * 描述:
 * 作者:LiuYu
 */
public class LoginBean {


    /**
     * result : OK
     * data : [{"ID":2,"UserName":"15588888888","UserPass":"202CB962AC59075B964B07152D234B70","phone":null,"Email":null,"AddDate":"2018-06-13T10:05:39","IsOnline":1,"Del":0,"YEPrice":null,"Team":null,"Pic":null,"IsLeader":0}]
     */

    private String result;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 2
         * UserName : 15588888888
         * UserPass : 202CB962AC59075B964B07152D234B70
         * phone : null
         * Email : null
         * AddDate : 2018-06-13T10:05:39
         * IsOnline : 1
         * Del : 0
         * YEPrice : null
         * Team : null
         * Pic : null
         * IsLeader : 0
         */

        private int ID;
        private String UserName;
        private String UserPass;
        private Object phone;
        private Object Email;
        private String AddDate;
        private int IsOnline;
        private int Del;
        private Object YEPrice;
        private Object Team;
        private Object headPic;
        private int IsLeader;

        public Object getHeadPic() {
            return headPic;
        }

        public void setHeadPic(Object headPic) {
            this.headPic = headPic;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUserPass() {
            return UserPass;
        }

        public void setUserPass(String UserPass) {
            this.UserPass = UserPass;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }

        public int getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(int IsOnline) {
            this.IsOnline = IsOnline;
        }

        public int getDel() {
            return Del;
        }

        public void setDel(int Del) {
            this.Del = Del;
        }

        public Object getYEPrice() {
            return YEPrice;
        }

        public void setYEPrice(Object YEPrice) {
            this.YEPrice = YEPrice;
        }

        public Object getTeam() {
            return Team;
        }

        public void setTeam(Object Team) {
            this.Team = Team;
        }

        public int getIsLeader() {
            return IsLeader;
        }

        public void setIsLeader(int IsLeader) {
            this.IsLeader = IsLeader;
        }
    }
}
