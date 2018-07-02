package com.xingguang.master.login.model;

/**
 * 创建日期：2018/6/13
 * 描述:
 * 作者:LiuYu
 */
public class SmsBean {

    /**
     * result : OK
     * IdenCode : 04012
     */

    private String result;
    private String IdenCode;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIdenCode() {
        return IdenCode;
    }

    public void setIdenCode(String IdenCode) {
        this.IdenCode = IdenCode;
    }
}
