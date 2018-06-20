package com.xingguang.master.maincode.mine.model;

import java.util.List;

/**
 * 创建日期：2018/6/20
 * 描述:报考管理bean
 * 作者:LiuYu
 */
public class BaoKaoGuanLiBean {

    /**
     * status : 1
     * msg : SUCCESS
     * data : [{"ID":4,"UserName":"18743206721","DepartmentID":2,"ProfessionID":14,"DepartmentName":"质监局","ProfessionName":"质监局瓦工","FormatAddDate":"2018-06-20"},{"ID":2,"UserName":"aa","DepartmentID":1,"ProfessionID":11,"DepartmentName":"安监局","ProfessionName":"安监局瓦工","FormatAddDate":"2018-06-19"},{"ID":1,"UserName":"aa","DepartmentID":1,"ProfessionID":11,"DepartmentName":"安监局","ProfessionName":"安监局瓦工","FormatAddDate":"2018-06-19"}]
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
         * ID : 4
         * UserName : 18743206721
         * DepartmentID : 2
         * ProfessionID : 14
         * DepartmentName : 质监局
         * ProfessionName : 质监局瓦工
         * FormatAddDate : 2018-06-20
         */

        private int ID;
        private String UserName;
        private int DepartmentID;
        private int ProfessionID;
        private String DepartmentName;
        private String ProfessionName;
        private String FormatAddDate;

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

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public int getProfessionID() {
            return ProfessionID;
        }

        public void setProfessionID(int ProfessionID) {
            this.ProfessionID = ProfessionID;
        }

        public String getDepartmentName() {
            return DepartmentName;
        }

        public void setDepartmentName(String DepartmentName) {
            this.DepartmentName = DepartmentName;
        }

        public String getProfessionName() {
            return ProfessionName;
        }

        public void setProfessionName(String ProfessionName) {
            this.ProfessionName = ProfessionName;
        }

        public String getFormatAddDate() {
            return FormatAddDate;
        }

        public void setFormatAddDate(String FormatAddDate) {
            this.FormatAddDate = FormatAddDate;
        }
    }
}
