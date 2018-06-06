package com.xingguang.master.maincode.enter.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.JsonBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownRTimerUtil;
import com.xingguang.master.util.GetJsonDataUtil;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/25
 * 描述:考试报名
 * 作者:LiuYu
 */
public class EnterFragment extends ToolBarFragment implements CountDownRTimerUtil.CountDownTimerListener {
    @BindView(R.id.tv_bumen)
    TextView tvBumen;
    @BindView(R.id.et_bumeng)
    TextView etBumeng;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.et_province)
    TextView etProvince;
    @BindView(R.id.tv_ads)
    TextView tvAds;
    @BindView(R.id.et_ads)
    EditText etAds;
    @BindView(R.id.tv_idnum)
    TextView tvIdnum;
    @BindView(R.id.et_idnum)
    EditText etIdnum;
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
    @BindView(R.id.ll_baoming)
    LinearLayout llBaoming;
    @BindView(R.id.sc_programs)
    NestedScrollView scPrograms;
    @BindView(R.id.tv_gongzhong)
    TextView tvGongzhong;
    @BindView(R.id.et_gongzhong)
    TextView etGongzhong;

    protected List<String> mDatas = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static final int REG_EMS = 0x0004;
    private Thread thread;
    private CountDownRTimerUtil util;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    break;
                case MSG_LOAD_FAILED:
                    break;
                case REG_EMS:
                    util.restart();
                    break;
            }
        }
    };
    private List<String> proLists = new ArrayList<>();//项目列表数据

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_enter;
    }

    @Override
    protected void initView() {
        setToolBarTitle("考试报名");
        init();
        initBaoming();
    }

    private void init() {
        util = new CountDownRTimerUtil(getActivity(), this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        AppUtil.addForeColorSpan(tvBumen, "选择部门*");
        AppUtil.addForeColorSpan(tvGongzhong, "选择工种*");
        AppUtil.addForeColorSpan(tvName, "姓名*");
        AppUtil.addForeColorSpan(tvProvince, "选择省份*");
        AppUtil.addForeColorSpan(tvAds, "详细地址*");
        AppUtil.addForeColorSpan(tvIdnum, "身份证*");
        AppUtil.addForeColorSpan(tvPhone, "电话*");
        AppUtil.addForeColorSpan(tvSms, "验证码*");

    }

    private void initBaoming() {

        mDatas.add("项目部");
        mDatas.add("安监部");
        mDatas.add("侦查部");
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.et_bumeng, R.id.et_province, R.id.rl_get_messs, R.id.btn_commit,R.id.et_gongzhong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_bumeng: //部门
                selectbumen();
                break;
            case R.id.et_gongzhong: //工种
                selectgonghzong();
                break;
            case R.id.et_province://省份
                selectprovice();
                break;
            case R.id.rl_get_messs: //验证码
                if (validates1()) {
                    tvGetmss.setEnabled(false);
                    sendSMSClient();
                }
                break;
            case R.id.btn_commit: //提交
                if (AppUtil.isFastDoubleClick(1000)) {
                    return;
                }
                if (validate()) {
                    CommitClient();
                }
                break;
        }
    }

    /**
     * 选择工种
     * */
    private void selectgonghzong() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mDatas.get(options1);
                etGongzhong.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setSubCalSize(16)//取消确定按钮文字大小
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择工种")//标题文字
                .build();
        pvOptions.setPicker(mDatas);
        pvOptions.show();
    }

    private void sendSMSClient() {
        tvGetmss.setTextColor(Color.rgb(81, 87, 104));
        rlGetMesss.setBackgroundResource(R.drawable.corners5_solidblack);
        Message msgs = mHandler.obtainMessage();
        msgs.what = REG_EMS;
        msgs.sendToTarget();
    }

    /**
     * 验证码校验
     */
    private boolean validates1() {
        if (TextUtils.isEmpty(etBumeng.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请选择部门!");
            return false;
        } else if (etGongzhong.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请选择工种!");
            return false;
        } else if (etName.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入您的姓名!");
            return false;
        } else if (TextUtils.isEmpty(etProvince.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请选择省市区!");
            return false;
        } else if (etAds.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入详细地址!");
            return false;
        } else if (etIdnum.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入身份证!");
            return false;
        } else if (etIdnum.getText().length() == 18) {
            ToastUtils.showToast(getActivity(), "请输入18位身份证!");
            return false;
        } else if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请填写联系方式");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(getActivity(), "请填写11位手机号");
            return false;
        } else {
            return true;
        }
    }


    private void CommitClient() {
        MainActivity.instance.setBg(1);
        MainActivity.instance.setToNewsFragment();
    }

    /**
     * 选择省份
     */
    private void selectprovice() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2)
                        + options3Items.get(options1).get(options2).get(options3);
                etProvince.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setSubCalSize(16)//取消确定按钮文字大小
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择城市")//标题文字
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();

    }

    /**
     * 选择部门
     */
    private void selectbumen() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mDatas.get(options1);
                etBumeng.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setSubCalSize(16)//取消确定按钮文字大小
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择部门")//标题文字
                .build();
        pvOptions.setPicker(mDatas);
        pvOptions.show();
    }

    public Boolean validate() {
        if (TextUtils.isEmpty(etBumeng.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请选择部门!");
            return false;
        } else if (etName.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入您的姓名!");
            return false;
        } else if (etGongzhong.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请选择工种!");
            return false;
        } else if (TextUtils.isEmpty(etProvince.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请选择省市区!");
            return false;
        } else if (etAds.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入详细地址!");
            return false;
        } else if (etIdnum.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入身份证!");
            return false;
        } else if (etIdnum.getText().length() == 18) {
            ToastUtils.showToast(getActivity(), "请输入18位身份证!");
            return false;
        } else if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请填写联系方式");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(getActivity(), "请填写11位手机号");
            return false;
        } else if (registerMss.getText().toString().length() == 0) {
            ToastUtils.showToast(getActivity(), "请输入验证码");
            return false;
        } else if (registerMss.getText().length() != 6) {
            ToastUtils.showToast(getActivity(), "请输入6位验证码");
            return false;
        } else {
            return true;
        }
    }


    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(getActivity(), "province.json");//获取assets目录下的json文件数据
        Log.e("jsonbeanqqqq", "initJsonData: " + JsonData);
        ArrayList<JsonBean> jsonBean = AppUtil.parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         */
        options1Items = jsonBean;
        Log.e("jsonbean", "initJsonData: " + jsonBean);
        for (int i = 0; i < jsonBean.size(); i++) { //遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {  //遍历该省份的所有城市
                String cityname = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(cityname);//添加城市
                Log.e("jsonbeanddd", "initJsonData: " + cityname);
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空数据，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                }
                for (int d = 0; d < jsonBean.get(i).getCity().get(c).getArea().size(); d++) {
                    String area = jsonBean.get(i).getCity().get(c).getArea().get(d);
                    City_AreaList.add(area);  //添加该城市所有的地区数据
                }
                Province_AreaList.add(City_AreaList);
            }
            /**
             * 添加城市地区
             * */
            options2Items.add(CityList);

            /**
             *添加地区数据
             * */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
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
