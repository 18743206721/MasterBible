package com.xingguang.master.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.util.CountDownRTimerUtil;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/6/1
 * 描述:忘记密码第一步
 * 作者:LiuYu
 */
public class ForgetOneActivity extends ToolBarActivity implements CountDownRTimerUtil.CountDownTimerListener {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_sms)
    TextView tvSms;
    @BindView(R.id.register_mss)
    EditText registerMss;
    @BindView(R.id.tv_getmss)
    TextView tvGetmss;
    @BindView(R.id.rl_get_messs)
    LinearLayout rlGetMesss;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_nophone)
    TextView tvNophone;
    @BindView(R.id.tv_nosms)
    TextView tvNosms;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initView() {
        setToolBarTitle("找回密码");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        util = new CountDownRTimerUtil(ForgetOneActivity.this, this);
    }

    @OnClick({R.id.rl_get_messs, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_get_messs:
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtils.showToast(ForgetOneActivity.this, "请输入您的手机号");
                } else if (etPhone.getText().length() != 11) {
                    ToastUtils.showToast(ForgetOneActivity.this, "请填写11位手机号");
                } else {
                    tvGetmss.setEnabled(false);
                    sendSMSClient();
                }
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    ToastUtils.showToast(ForgetOneActivity.this, "请输入您的手机号");
                } else if (etPhone.getText().length() != 11) {
                    ToastUtils.showToast(ForgetOneActivity.this, "请填写11位手机号");
                } else if (TextUtils.isEmpty(registerMss.getText().toString())) {
                    ToastUtils.showToast(ForgetOneActivity.this, "请输入验证码");
                } else {
                    forgetload();
                }
                break;
        }
    }

    /**
     * 忘记密码第一步，走验证验证码接口
     */
    private void forgetload() {

        startActivity(new Intent(ForgetOneActivity.this,ForgetTwoActivity.class));
        finish();
    }

    private void sendSMSClient() {
        tvGetmss.setTextColor(Color.rgb(81, 87, 104));
        rlGetMesss.setBackgroundResource(R.drawable.corners5_solidblack);
        Message msgs = mHandler.obtainMessage();
        msgs.what = REG_EMS;
        msgs.sendToTarget();
    }


    @Override
    public void countDownTimerListener(String time) {
        tvGetmss.setText(time);
    }

    @Override
    public void countDownTimerFinish() {
        tvGetmss.setEnabled(true);
        tvGetmss.setTextColor(Color.parseColor("#005FBB"));
        rlGetMesss.setBackgroundResource(R.drawable.btn_register_bg);
    }


}
