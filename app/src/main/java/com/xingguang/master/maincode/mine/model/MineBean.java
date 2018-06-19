package com.xingguang.master.maincode.mine.model;

import java.util.List;

/**
 * 创建日期：2018/6/19
 * 描述: 修改个人信息
 * 作者:LiuYu
 */
public class MineBean {
    /**
     * result : 提交成功！
     * data : [{"ID":2,"UserName":"15588888888","UserPass":"202CB962AC59075B964B07152D234B70","phone":null,"Email":"男","AddDate":"2018-06-13T10:05:39","IsOnline":1,"Del":0,"YEPrice":"测试昵称","Team":"测试地址","Pic":"18061909270753439.jpg","IsLeader":0}]
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
         * Email : 男
         * AddDate : 2018-06-13T10:05:39
         * IsOnline : 1
         * Del : 0
         * YEPrice : 测试昵称
         * Team : 测试地址
         * Pic : 18061909270753439.jpg
         * IsLeader : 0
         */

        private int ID;
        private String UserName;
        private String UserPass;
        private Object phone;
        private String Email;
        private String AddDate;
        private int IsOnline;
        private int Del;
        private String YEPrice;
        private String Team;
        private String Pic;
        private int IsLeader;
        private String headPic;

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
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

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
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

        public String getYEPrice() {
            return YEPrice;
        }

        public void setYEPrice(String YEPrice) {
            this.YEPrice = YEPrice;
        }

        public String getTeam() {
            return Team;
        }

        public void setTeam(String Team) {
            this.Team = Team;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }

        public int getIsLeader() {
            return IsLeader;
        }

        public void setIsLeader(int IsLeader) {
            this.IsLeader = IsLeader;
        }
    }
}
