package com.xingguang.master.maincode.mine.model;

/**
 * 创建日期：2018/6/21
 * 描述:报考详情
 * 作者:LiuYu
 */
public class BaoKaoDetailsBean {


    /**
     * status : 1
     * msg : SUCCESS
     * data : {"ID":2,"UserID":0,"DepartmentID":1,"DepartmentName":"安监局","ProfessionID":11,"ProfessionName":"安监局瓦工","UserName":"aa","Name":"姓名测试","Province":"吉林省","City":"长春市","Area":"绿园区","Address":"详细地址","Phone":"15844058872","Tel":"2222222222222","IsOnline":1,"AddDate":"2018-06-19T16:03:23"}
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
         * ID : 2
         * UserID : 0
         * DepartmentID : 1
         * DepartmentName : 安监局
         * ProfessionID : 11
         * ProfessionName : 安监局瓦工
         * UserName : aa
         * Name : 姓名测试
         * Province : 吉林省
         * City : 长春市
         * Area : 绿园区
         * Address : 详细地址
         * Phone : 15844058872
         * Tel : 2222222222222
         * IsOnline : 1
         * AddDate : 2018-06-19T16:03:23
         */

        private int ID;
        private int UserID;
        private int DepartmentID;
        private String DepartmentName;
        private int ProfessionID;
        private String ProfessionName;
        private String UserName;
        private String Name;
        private String Province;
        private String City;
        private String Area;
        private String Address;
        private String Phone;
        private String Tel;
        private int IsOnline;
        private String AddDate;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public int getProfessionID() {
            return ProfessionID;
        }

        public void setProfessionID(int ProfessionID) {
            this.ProfessionID = ProfessionID;
        }

        public String getProfessionName() {
            return ProfessionName;
        }

        public void setProfessionName(String ProfessionName) {
            this.ProfessionName = ProfessionName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getArea() {
            return Area;
        }

        public void setArea(String Area) {
            this.Area = Area;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public int getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(int IsOnline) {
            this.IsOnline = IsOnline;
        }

        public String getAddDate() {
            return AddDate;
        }

        public void setAddDate(String AddDate) {
            this.AddDate = AddDate;
        }
    }
}


