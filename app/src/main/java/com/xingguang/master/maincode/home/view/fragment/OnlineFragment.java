package com.xingguang.master.maincode.home.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ClearEditText;
import com.xingguang.master.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/29
 * 描述:在线留言
 * 作者:LiuYu
 */
public class OnlineFragment extends ToolBarFragment {

    @BindView(R.id.et_name)
    ClearEditText etName;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.id_editor_detail_font_count)
    ImageView idEditorDetailFontCount;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
            }
        });
        setToolBarTitle("在线留言");

        init();

    }


    private void init() {


    }


    @Override
    protected void lazyLoad() {

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        if (AppUtil.isFastDoubleClick(1000)) {
            return;
        }
        if (validate()) {
            CommitClient();
        }
    }

    private void CommitClient() {
        MainActivity.instance.setBg(1);
        MainActivity.instance.setToNewsFragment();
    }

    public Boolean validate() {
        if (etName.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请填写名字");
            return false;
        } else if (etPhone.getText().length() == 0 ){
            ToastUtils.showToast(getActivity(), "请填写联系方式");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(getActivity(), "请填写11位手机号");
            return false;
        } else if (edContent.getText().toString().length() == 0) {
            ToastUtils.showToast(getActivity(), "请填写内容");
            return false;
        } else {
            return true;
        }
    }



}
