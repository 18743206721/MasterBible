package com.xingguang.master.maincode.home.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.model.JsonBean;
import com.xingguang.master.maincode.home.model.ProgramsBean;
import com.xingguang.master.maincode.home.view.adapter.ProjectAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
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
 * 创建日期：2018/6/15
 * 描述:培训项目，培训报名
 * 作者:LiuYu
 */
public class ListProgramsFragment extends BaseFragment implements CountDownRTimerUtil.CountDownTimerListener {

    @BindView(R.id.rv_programs)
    RecyclerView rvPrograms;
    @BindView(R.id.ll_project)
    LinearLayout llProject;
    @BindView(R.id.tv_bumen)
    TextView tvBumen;
    @BindView(R.id.et_bumeng)
    TextView etBumeng;
    @BindView(R.id.tv_gongzhong)
    TextView tvGongzhong;
    @BindView(R.id.et_gongzhong)
    TextView etGongzhong;
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
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.rl_diangong)
    RelativeLayout rl_diangong;

    protected List<String> listbumen = new ArrayList<>(); //选择部门数据
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
    private List<ProgramsBean.DataBeanX.DataBean> proLists = new ArrayList<>();//项目列表数据
    int type;
    private int len;
    private ProjectAdapter adapter;
    private List<String> listgongzhong = new ArrayList<>();

    String province = "";//省份
    String city = "";//城市
    String area = "";//区域
    int bumenId = 0;     //部门id
    int gongzhongId = 0; //工种id

    public static ListProgramsFragment newInstance(int type, int len) {
        ListProgramsFragment fragment = new ListProgramsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("len", len);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_llistprograms;
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
            len = arguments.getInt("len");
        }

        init();
        initProject();
        loadbaoming();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), WebViewActivity.class)
                        .putExtra("id", 2)
                        .putExtra("classid", proLists.get(position).getID())
                );
            }
        });
    }


    private void init() {
        util = new CountDownRTimerUtil(getActivity(), this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        if (type != len) {
            llProject.setVisibility(View.VISIBLE);
            llBaoming.setVisibility(View.GONE);
        } else {
            llProject.setVisibility(View.GONE);
            llBaoming.setVisibility(View.VISIBLE);
        }
    }


    private void load() {
        OkGo.<String>post(HttpManager.ProjectTraining)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        ProgramsBean bean = gson.fromJson(response.body().toString(), ProgramsBean.class);
                        if (bean.getData() != null) {
                            proLists.clear();
                            for (int i = 0; i < bean.getData().size() - 1; i++) {
                                if (type - 1 == i) {
                                    proLists.addAll(bean.getData().get(i).getData());
                                }
                            }
                            adapter.setList(proLists);
                        }

                    }
                });
    }

    /**
     * 部门数据
     */
    private void loadbaoming() {
        OkGo.<String>post(HttpManager.ExamRegistration)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BuMengBean bean = gson.fromJson(response.body().toString(), BuMengBean.class);
                        if (bean.getData() != null) {
                            listbumen.clear();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                listbumen.add(bean.getData().get(i).getName());
                            }
                        }
                    }
                });
    }

    /**
     * 工种数据
     *
     * @param tx
     */
    private void loadgongzhong(final String tx) {
        OkGo.<String>post(HttpManager.ExamRegistration)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BuMengBean bean = gson.fromJson(response.body().toString(), BuMengBean.class);
                        if (bean.getData() != null) {
                            for (int i = 0; i < bean.getData().size(); i++) {
                                if (tx.equals(bean.getData().get(i).getName())) {
                                    bumenId = bean.getData().get(i).getID(); //获取部门id数据
                                    for (int j = 0; j < bean.getData().get(i).getData().size(); j++) {
                                        listgongzhong.add(bean.getData().get(i).getData().get(j).getName());
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 获取工种id数据
     *
     * @param tx
     */
    private void loadgongzhong2(final String tx) {
        OkGo.<String>post(HttpManager.ExamRegistration)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BuMengBean bean = gson.fromJson(response.body().toString(), BuMengBean.class);
                        if (bean.getData() != null) {
                            for (int i = 0; i < bean.getData().size(); i++) {
                                for (int j = 0; j < bean.getData().get(i).getData().size(); j++) {
                                    if (tx.equals(bean.getData().get(i).getData().get(j).getName())) {
                                        gongzhongId = bean.getData().get(i).getData().get(j).getID();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.et_bumeng, R.id.et_province, R.id.rl_get_messs, R.id.btn_commit, R.id.et_gongzhong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_bumeng: //部门
                selectbumen();
                break;
            case R.id.et_gongzhong://工种
                if (etBumeng.getText().length() == 0) {
                    ToastUtils.showToast(getActivity(), "请选择部门!");
                } else {
                    selectedgongzhong();
                }
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
     */
    private void selectedgongzhong() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = listgongzhong.get(options1);
                etGongzhong.setText(tx);
                loadgongzhong2(tx);
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
        pvOptions.setPicker(listgongzhong);
        pvOptions.show();

    }

    /**
     * 发送验证码
     */
    private void sendSMSClient() {
        OkGo.<String>post(HttpManager.ProjectTraining)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "yzm")
                .params("Phone", etPhone.getText().toString())
                .params("UserName",AppUtil.getUserId(getActivity()))
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean bean = gson.fromJson(response.body().toString(), CommonBean.class);
                        ToastUtils.showToast(getActivity(), bean.getResult());
                        rlGetMesss.setEnabled(false);
                        tvGetmss.setTextColor(Color.rgb(81, 87, 104));
                        rlGetMesss.setBackgroundResource(R.drawable.corners5_solidblack);
                        Message msgs = mHandler.obtainMessage();
                        msgs.what = REG_EMS;
                        msgs.sendToTarget();
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        if (tvBumen.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvBumen, "选择部门*");
        }
        if (tvName.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvName, "姓名*");
        }
        if (tvGongzhong.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvGongzhong, "工种*");
        }
        if (tvProvince.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvProvince, "选择省份*");
        }
        if (tvAds.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvAds, "详细地址*");
        }
        if (tvIdnum.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvIdnum, "身份证*");
        }
        if (tvPhone.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvPhone, "电话*");
        }
        if (tvSms.getText().length() == 0) {
            AppUtil.addForeColorSpan(tvSms, "验证码*");
        }
        load();
    }

    /**
     * 提交接口
     */
    private void CommitClient() {
        OkGo.<String>post(HttpManager.ProjectTraining)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "submit")
                .params("UserName", AppUtil.getUserId(getActivity()))
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId)//工种ID
                .params("Name", etName.getText().toString()) //姓名
                .params("Province", province) //省
                .params("City", city) //市
                .params("Area", area) //区
                .params("Address", etAds.getText().toString()) //地址
                .params("IDcard", etIdnum.getText().toString()) //身份证
                .params("Phone", etPhone.getText().toString())  //手机
                .params("IdenCode", registerMss.getText().toString()) //验证码
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean bean = gson.fromJson(response.body().toString(), CommonBean.class);
                        ToastUtils.showToast(getActivity(), bean.getResult());
                        getActivity().finish();
                    }
                });
    }

    private void initProject() {
        adapter = new ProjectAdapter(getActivity(), proLists);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvPrograms.setLayoutManager(manager);
        rvPrograms.setAdapter(adapter);
        rvPrograms.setNestedScrollingEnabled(false);
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
                String tx = listbumen.get(options1);
                etBumeng.setText(tx);
                loadgongzhong(tx);
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
        pvOptions.setPicker(listbumen);
        pvOptions.show();
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
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                area = options3Items.get(options1).get(options2).get(options3);

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
     * 验证码校验
     */
    private boolean validates1() {
        if (etPhone.getText().length() == 0) {
            ToastUtils.showToast(getActivity(), "请填写联系方式");
            return false;
        } else if (etPhone.getText().length() != 11) {
            ToastUtils.showToast(getActivity(), "请填写11位手机号");
            return false;
        } else {
            return true;
        }
    }

    public Boolean validate() {
        if (TextUtils.isEmpty(etBumeng.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请选择部门!");
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
        } else if (etIdnum.getText().length() != 18) {
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
        } else if (registerMss.getText().length() != 5) {
            ToastUtils.showToast(getActivity(), "请输入5位验证码");
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
