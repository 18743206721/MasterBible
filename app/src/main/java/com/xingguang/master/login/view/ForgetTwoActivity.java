package com.xingguang.master.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/6/1
 * 描述:找回密码第二步
 * 作者:LiuYu
 */
public class ForgetTwoActivity extends ToolBarActivity {

    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwdtwo)
    EditText etPwdtwo;
    @BindView(R.id.iv_visgone)
    ImageView ivVisgone;
    @BindView(R.id.tv_nosms)
    TextView tvNosms;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private boolean isshow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_two;
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
    }

    @OnClick({R.id.btn_commit,R.id.iv_visgone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_visgone:
                isshow = !isshow;
                if (isshow) {//显示明文--设置为可见的密码
                    ivVisgone.setImageResource(R.mipmap.login_bluevis);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivVisgone.setImageResource(R.mipmap.login_vis);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(etPwd.getText().toString())) {
                    ToastUtils.showToast(ForgetTwoActivity.this, "请输入您的新密码");
                } else if (TextUtils.isEmpty(etPwdtwo.getText().toString())) {
                    ToastUtils.showToast(ForgetTwoActivity.this, "请确认您的密码");
                } else if (!(etPwd.getText().toString()).equals(etPwdtwo.getText().toString())) {
                    tvNosms.setVisibility(View.VISIBLE);
                    etPwdtwo.setText("");
                } else {
                    commit();
                }
                break;
        }
    }


    private void commit() {
        tvNosms.setVisibility(View.GONE);
        //跳转到
        Intent intent = new Intent();
        intent.setClass(ForgetTwoActivity.this,ForgetThreeActivity.class);
        startActivity(intent);
        finish();
    }


}
