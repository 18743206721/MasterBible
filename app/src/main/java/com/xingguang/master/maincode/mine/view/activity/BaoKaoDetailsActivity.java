package com.xingguang.master.maincode.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.mine.model.BaoKaoDetailsBean;
import com.xingguang.master.maincode.mine.model.BaoKaoGuanLiBean;
import com.xingguang.master.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/30
 * 描述:报考详情,培训详情
 * 作者:LiuYu
 */
public class BaoKaoDetailsActivity extends ToolBarActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_bumeng)
    TextView etBumeng;
//    @BindView(R.id.iv_bumen)
//    ImageView ivBumen;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.et_province)
    TextView etProvince;
//    @BindView(R.id.iv_province)
//    ImageView ivProvince;
    @BindView(R.id.tv_ads)
    TextView tvAds;
    @BindView(R.id.et_idnum)
    TextView etIdnum;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.ll_baoming)
    LinearLayout llBaoming;

    int type = 0; //2是报考记录，1是培训记录
    int id;
    BaoKaoDetailsBean.DataBean dataBean = new BaoKaoDetailsBean.DataBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bao_kao_details;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type",0);
        id = getIntent().getIntExtra("id",0);
        if (type == 2){
            setToolBarTitle("报考记录");
        }else if (type ==1){
            setToolBarTitle("培训记录");
        }
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        load();

    }

    private void load() {
        OkGo.<String>post(HttpManager.Login_record)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("UserName", AppUtil.getUserId(this))
                .params("ListType", type)
                .params("InfoID", id)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BaoKaoDetailsBean bean = gson.fromJson(response.body().toString(), BaoKaoDetailsBean.class);
                        if (bean.getData() != null) {
                            dataBean = bean.getData();
                            tvName.setText(dataBean.getName());
                            //部门加工种
                            etBumeng.setText(dataBean.getDepartmentName()+" "+dataBean.getProfessionName());

                            etProvince.setText(dataBean.getProvince()+" "+dataBean.getCity()+" "+dataBean.getArea()+" ");
                            tvAds.setText(dataBean.getAddress());
                            etIdnum.setText(dataBean.getTel()); //身份证号
                            etPhone.setText(dataBean.getPhone());
                        }

                    }
                });
    }



    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        finish();
    }


}
