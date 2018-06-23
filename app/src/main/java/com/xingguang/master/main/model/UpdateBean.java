package com.xingguang.master.main.model;

/**
 * 创建日期：2018/6/21
 * 描述: 版本更新
 * 作者:LiuYu
 */
public class UpdateBean {
    /**
     * VersionName : V4.1.1
     * VersionUrl : 版本url
     * Content : <p>
     <span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span>
     </p>
     <p>
     <span style="color:#333333;font-family:&quot;">&nbsp;&nbsp;<span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span><span style="color:#333333;font-family:&quot;">版本描述</span></span>
     </p>
     */

    private String VersionName;
    private String VersionUrl;
    private String Content;

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String VersionName) {
        this.VersionName = VersionName;
    }

    public String getVersionUrl() {
        return VersionUrl;
    }

    public void setVersionUrl(String VersionUrl) {
        this.VersionUrl = VersionUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
