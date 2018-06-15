package com.xingguang.master.http;

/**
 * 创建日期：2018/6/13
 * 描述:
 * 作者:LiuYu
 */
public class HttpManager {

    //线上
    public static final String BASE_URL = "http://192.168.0.150:8035/";
    //本地测试
//    public static final String BASE_URL = "http://192.168.0.230/index.php/";


    //登录接口
    public static final String Login= BASE_URL+"UserLogin.aspx";

    //注册发送验证码
    public static final String sendSms= BASE_URL+"Login_zc.aspx";

    //退出登录
    public static final String NonLogin= BASE_URL+"NonLogin.aspx";

    //忘记密码
    public static final String Login_xgmm= BASE_URL+"Login_xgmm.aspx";

    //首页
    public static final String index= BASE_URL+"index.aspx";

}
