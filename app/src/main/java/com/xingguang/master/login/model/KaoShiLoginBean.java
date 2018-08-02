package com.xingguang.master.login.model;

import java.util.List;

/**
 * 创建日期：2018/7/23
 * 描述:身份登录
 * 作者:LiuYu
 */
public class KaoShiLoginBean {

    /**
     * status : 1
     * msg : 登陆成功！
     * data : [{"UserName":"18743206721","Name":"测试姓名","IDnumber":"2202625198809180202"}]
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
         * UserName : 18743206721
         * Name : 测试姓名
         * IDnumber : 2202625198809180202
         */

        private String UserName;
        private String Name;
        private String IDnumber;

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

        public String getIDnumber() {
            return IDnumber;
        }

        public void setIDnumber(String IDnumber) {
            this.IDnumber = IDnumber;
        }
    }
}
