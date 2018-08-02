package com.xingguang.master.login.model;

import java.util.List;

/**
 * 创建日期：2018/7/23
 * 描述:
 * 作者:LiuYu
 */
public class KaoShiBean {


    /**
     * status : 1
     * msg : 发送成功！
     * data : []
     */

    private int status;
    private String msg;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
