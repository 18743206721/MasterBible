package com.xingguang.master.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.login.model.LoginBean;
import com.xingguang.master.login.model.SmsBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownRTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements CountDownRTimerUtil.CountDownTimerListener {

    @BindView(R.id.xtab_login)
    XTabLayout xtabLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_yes)
    ImageView ivYes;
    @BindView(R.id.ll_yes)
    LinearLayout llYes;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ll_vis_forget)
    LinearLayout llVisForget;
    @BindView(R.id.register_mss)
    EditText registerMss;
    @BindView(R.id.tv_getmss)
    TextView tvGetmss;
    @BindView(R.id.rl_get_messs)
    LinearLayout rlGetMesss;
    @BindView(R.id.ll_vis_sms)
    LinearLayout llVisSms;
    @BindView(R.id.iv_visgone)
    ImageView iv_visgone;
    @BindView(R.id.iv_finish)
    ImageView iv_finish;

    int type = 0;
    private boolean isshow;
    private CountDownRTimerUtil util;
    private static final int REG_EMS = 0x0004;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REG_EMS:
                    util.restart();
                    break;
            }
        }
    };
    private boolean ispwd;
    private String code; // 验证码

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        init();

    }

    private void init() {
        util = new CountDownRTimerUtil(LoginActivity.this, this);
        xtabLogin.addTab(xtabLogin.newTab().setText("登录"));
        xtabLogin.addTab(xtabLogin.newTab().setText("注册"));

        xtabLogin.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                String text = (String) tab.getText();
                if (text.equals("登录")) {
                    llVisSms.setVisibility(View.GONE);
                    llVisForget.setVisibility(View.VISIBLE);
                    tvLogin.setText("登 录");
                    type = 0;
                } else {
                    llVisSms.setVisibility(View.VISIBLE);
                    llVisForget.setVisibility(View.GONE);
                    tvLogin.setText("注 册");
                    type = 1;
                }
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

    }


    @OnClick({R.id.ll_yes, R.id.tv_login, R.id.rl_get_messs, R.id.iv_visgone, R.id.tv_forget,R.id.iv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_yes: //勾选记住密码
                isshow = !isshow;
                if (isshow) {
                    ivYes.setImageResource(R.mipmap.login_no);
                } else {
                    ivYes.setImageResource(R.mipmap.login_yes);
                }
                break;
            case R.id.tv_login://登录或者注册
                if (type == 0) { //登录
                    if (AppUtil.isFastDoubleClick(1000)) {
                        return;
                    }
                    if (validates1()) {
                        loadlogin();
                    }
                } else { // 注册
                    if (AppUtil.isFastDoubleClick(1000)) {
                        return;
                    }
                    if (validatesRegister()) {
                        loadregister();
                    }
                }
                break;
            case R.id.rl_get_messs://获取验证码
                if (validates1()) {
                    tvGetmss.setEnabled(false);
                    sendSMSClient();
                }
                break;
            case R.id.iv_visgone://明文
                ispwd = !ispwd;
                if (ispwd) {//显示明文--设置为可见的密码
                    iv_visgone.setImageResource(R.mipmap.login_bluevis);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    iv_visgone.setImageResource(R.mipmap.login_vis);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
            case R.id.tv_forget://忘记密码
                startActivity(new Intent(LoginActivity.this, ForgetOneActivity.class));
                break;
            case R.id.iv_finish:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtil.getUserId(LoginActivity.this).equals("")) {
            etPhone.setText("");
        } else {
            etPhone.setText(AppUtil.getUserId(LoginActivity.this));
        }
    }

    /**
     * 注册接口
     */
    private void loadregister() {
        OkGo.<String>post(HttpManager.sendSms)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", etPhone.getText().toString())
                .params("UserPass", etPwd.getText().toString())
                .params("IdenCode", registerMss.getText().toString())
                .params("MethodCode", "zc")  //验证码发送：yzm，注册：zc）
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        SmsBean smsBean = gson.fromJson(response.body().toString(), SmsBean.class);
                        if (smsBean.getMsg().equals("OK")){
                            xtabLogin.getTabAt(0).select();   //设置TabLayout选中登录选项。
                            llVisSms.setVisibility(View.GONE);
                            llVisForget.setVisibility(View.VISIBLE);
                            tvLogin.setText("登 录");
                            type = 0;
                        }
                        ToastUtils.showToast(LoginActivity.this, smsBean.getResult());
                    }
                });
    }

    /**
     * 登录接口
     */
    private void loadlogin() {
        OkGo.<String>post(HttpManager.Login)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", etPhone.getText().toString())
                .params("UserPass", etPwd.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body().toString(), LoginBean.class);
                        if (loginBean.getData().size() != 0) {
                            SharedPreferencesUtils.put(LoginActivity.this, SharedPreferencesUtils.USERID, loginBean.getData().get(0).getUserName());
                            if (loginBean.getData().get(0).getYEPrice() != null) {
                                SharedPreferencesUtils.put(LoginActivity.this, SharedPreferencesUtils.USERNAME, loginBean.getData().get(0).getYEPrice());
                            }
                            if (loginBean.getData().get(0).getHeadPic() != null) {
                                SharedPreferencesUtils.put(LoginActivity.this, SharedPreferencesUtils.USERIMAGE, HttpManager.BASE_URL + loginBean.getData().get(0).getHeadPic());
                            }
                            //性别
                            if (loginBean.getData().get(0).getEmail() != null) {
                                SharedPreferencesUtils.put(LoginActivity.this, SharedPreferencesUtils.USERSEX, loginBean.getData().get(0).getEmail());
                            }

                            //地区
                            if (loginBean.getData().get(0).getTeam() != null) {
                                SharedPreferencesUtils.put(LoginActivity.this, SharedPreferencesUtils.USERADS, loginBean.getData().get(0).getTeam());
                            }
                            finish();
                        }
                        ToastUtils.showToast(LoginActivity.this, loginBean.getResult());

                    }
                });
    }

    private boolean validatesRegister() {
        if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(LoginActivity.this, "请输入您的手机号");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(LoginActivity.this, "请填写11位手机号");
            return false;
        } else if (etPwd.getText().length() == 0) {
            ToastUtils.showToast(LoginActivity.this, "请输入密码");
            return false;
        } else if (registerMss.getText().length() == 0) {
            ToastUtils.showToast(LoginActivity.this, "请输入验证码");
            return false;
        } else {
            return true;
        }
    }

    private boolean validates1() {
        if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(LoginActivity.this, "请输入您的手机号");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(LoginActivity.this, "请填写11位手机号");
            return false;
        } else if (etPwd.getText().length() == 0) {
            ToastUtils.showToast(LoginActivity.this, "请输入密码");
            return false;
        } else {
            return true;
        }
    }


    private void sendSMSClient() {
        OkGo.<String>post(HttpManager.sendSms)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", etPhone.getText().toString())
                .params("MethodCode", "yzm")
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        SmsBean smsBean = gson.fromJson(response.body().toString(), SmsBean.class);
                        ToastUtils.showToast(LoginActivity.this, smsBean.getResult());

                        tvGetmss.setTextColor(Color.rgb(81, 87, 104));
                        rlGetMesss.setBackgroundResource(R.drawable.corners5_solidblack);
                        Message msgs = mHandler.obtainMessage();
                        msgs.what = 1;
                        msgs.sendToTarget();
                        tvGetmss.setEnabled(false);
                        rlGetMesss.setEnabled(false);
                    }
                });
    }


    @Override
    public void countDownTimerListener(String time) {
        tvGetmss.setText(time);
    }

    @Override
    public void countDownTimerFinish() {
        tvGetmss.setEnabled(true);
        rlGetMesss.setEnabled(true);
        tvGetmss.setTextColor(Color.parseColor("#005FBB"));
        rlGetMesss.setBackgroundResource(R.drawable.btn_register_bg);
    }


}

