package com.xingguang.master.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
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
import com.xingguang.master.login.model.KaoShiBean;
import com.xingguang.master.login.model.KaoShiLoginBean;
import com.xingguang.master.login.model.LoginBean;
import com.xingguang.master.login.model.SmsBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownRTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 考试系统登录页
 */
public class KaoshiLoginActivity extends BaseActivity implements CountDownRTimerUtil.CountDownTimerListener {

    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.xtab_login)
    XTabLayout xtabLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.log_mss)
    EditText logMss;
    @BindView(R.id.tv_logmss)
    TextView tvLogmss;
    @BindView(R.id.rl_log_messs)
    LinearLayout rlLogMesss;
    @BindView(R.id.ll_vis_sms)
    LinearLayout llVisSms;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.et_rgsname)
    EditText etRgsname;
    @BindView(R.id.et_rgsidcard)
    EditText etRgsidcard;
    @BindView(R.id.et_rgsphone)
    EditText etRgsphone;
    @BindView(R.id.rgs_mss)
    EditText rgsMss;
    @BindView(R.id.tv_rgsmss)
    TextView tvRgsmss;
    @BindView(R.id.rl_rgs_messs)
    LinearLayout rlRgsMesss;
    @BindView(R.id.ll_rgs_sms)
    LinearLayout llRgsSms;
    @BindView(R.id.ll_rgs)
    LinearLayout llRgs;
    @BindView(R.id.tv_login)
    TextView tvLogin;

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
        return R.layout.activity_kaoshi_login;
    }

    @Override
    protected void initView() {
        init();
    }

    private void init() {
        util = new CountDownRTimerUtil(KaoshiLoginActivity.this, this);
        xtabLogin.addTab(xtabLogin.newTab().setText("登录"));
        xtabLogin.addTab(xtabLogin.newTab().setText("注册"));

        xtabLogin.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                String text = (String) tab.getText();
                if (text.equals("登录")) {
                    llLogin.setVisibility(View.VISIBLE);
                    llRgs.setVisibility(View.GONE);
                    tvLogin.setText("登 录");
                    type = 0;
                } else {
                    llLogin.setVisibility(View.GONE);
                    llRgs.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.tv_login, R.id.rl_log_messs, R.id.iv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.rl_log_messs://获取登录验证码
                if (type == 0) {
                    if (validatessms()) {
                        tvLogmss.setEnabled(false);
                        sendSMSClient(etPhone.getText().toString());
                    }
                } else if (type == 1) {
                    if (validatessms()) {
                        tvRgsmss.setEnabled(false);
                        sendSMSClientrgs(etRgsphone.getText().toString());
                    }
                }
                break;
            case R.id.iv_finish:
                finish();
                break;
        }
    }

    //注册时候发送验证码
    private void sendSMSClientrgs(String text) {
        OkGo.<String>post(HttpManager.ShenRGS)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", text)
                .params("MethodCode", "yzm")
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiBean smsBean = gson.fromJson(response.body().toString(), KaoShiBean.class);
                        ToastUtils.showToast(KaoshiLoginActivity.this, smsBean.getMsg());
                        tvRgsmss.setTextColor(Color.rgb(81, 87, 104));
                        rlRgsMesss.setBackgroundResource(R.drawable.corners5_solidblack);
                        Message msgs = mHandler.obtainMessage();
                        msgs.what = 1;
                        msgs.sendToTarget();
                        tvRgsmss.setEnabled(false);
                        rlRgsMesss.setEnabled(false);
                    }
                });
    }

    //注册
    private void loadregister() {
        OkGo.<String>post(HttpManager.ShenRGS)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", etPhone.getText().toString())
                .params("MethodCode", "zc")  //验证码发送：yzm，注册：zc）
                .params("Name", etRgsname.getText().toString())
                .params("IDnumber", etRgsidcard.getText().toString())
                .params("IdenCode", rgsMss.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiBean smsBean = gson.fromJson(response.body().toString(), KaoShiBean.class);
                        if (smsBean.getStatus() == 1) {
                            xtabLogin.getTabAt(0).select();   //设置TabLayout选中登录选项。
                            llVisSms.setVisibility(View.GONE);
                            tvLogin.setText("登 录");
                            type = 0;
                        }
                        ToastUtils.showToast(KaoshiLoginActivity.this, smsBean.getMsg());
                    }
                });

    }

    //登录
    private void loadlogin() {
        OkGo.<String>post(HttpManager.ShenLogin)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", etPhone.getText().toString())
                .params("MethodCode", "Login")
                .params("IdenCode", logMss.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiLoginBean loginBean = gson.fromJson(response.body().toString(), KaoShiLoginBean.class);
                        if (loginBean.getData().size() != 0) {
                            SharedPreferencesUtils.put(KaoshiLoginActivity.this, SharedPreferencesUtils.SHENFENID, loginBean.getData().get(0).getUserName());
//                            if (loginBean.getData().get(0).getTeam() != null) {
//                                SharedPreferencesUtils.put(KaoshiLoginActivity.this, SharedPreferencesUtils.USERADS, loginBean.getData().get(0).getTeam());
//                            }
                            finish();
                        }
                        ToastUtils.showToast(KaoshiLoginActivity.this, loginBean.getMsg());
                    }
                });
    }

    //登录验证码
    private void sendSMSClient(String text) {
        OkGo.<String>post(HttpManager.ShenLogin)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", text)
                .params("MethodCode", "yzm")
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiBean smsBean = gson.fromJson(response.body().toString(), KaoShiBean.class);
                        ToastUtils.showToast(KaoshiLoginActivity.this, smsBean.getMsg());
                        tvLogmss.setTextColor(Color.rgb(81, 87, 104));
                        rlLogMesss.setBackgroundResource(R.drawable.corners5_solidblack);
                        Message msgs = mHandler.obtainMessage();
                        msgs.what = 1;
                        msgs.sendToTarget();
                        tvLogmss.setEnabled(false);
                        rlLogMesss.setEnabled(false);

                    }
                });
    }

    private boolean validatesRegister() {
        if (etRgsname.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的姓名");
            return false;
        } else if (etRgsidcard.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的18位身份证号");
            return false;
        } else if (etRgsphone.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的手机号");
            return false;
        } else if (etRgsphone.getText().length() != 11) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请填写11位手机号");
            return false;
        } else if (rgsMss.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入验证码");
            return false;
        } else {
            return true;
        }
    }

    private boolean validates1() {
        if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的手机号");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请填写11位手机号");
            return false;
        } else if (logMss.getText().length() == 0) {
            ToastUtils.showToast(KaoshiLoginActivity.this, "请输入验证码");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatessms() {
        if (type == 0) {
            if (etPhone.getText().length() == 0) {
                ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的手机号");
                return false;
            } else if (etPhone.getText().length() != 11) {
                ToastUtils.showToast(KaoshiLoginActivity.this, "请填写11位手机号");
                return false;
            } else {
                return true;
            }
        } else {
            if (etRgsphone.getText().length() == 0) {
                ToastUtils.showToast(KaoshiLoginActivity.this, "请输入您的手机号");
                return false;
            } else if (etRgsphone.getText().length() != 11) {
                ToastUtils.showToast(KaoshiLoginActivity.this, "请填写11位手机号");
                return false;
            } else {
                return true;
            }
        }

    }

    @Override
    public void countDownTimerListener(String time) {
        if (type == 0) {
            tvLogmss.setText(time);
        } else if (type == 1) {
            tvRgsmss.setText(time);
        }

    }

    @Override
    public void countDownTimerFinish() {
        if (type == 0) {
            tvLogmss.setEnabled(true);
            rlLogMesss.setEnabled(true);
            tvLogmss.setTextColor(Color.parseColor("#005FBB"));
            rlLogMesss.setBackgroundResource(R.drawable.btn_register_bg);
        } else if (type == 1) {
            tvRgsmss.setEnabled(true);
            rlRgsMesss.setEnabled(true);
            tvRgsmss.setTextColor(Color.parseColor("#005FBB"));
            rlRgsMesss.setBackgroundResource(R.drawable.btn_register_bg);
        }
    }


}
