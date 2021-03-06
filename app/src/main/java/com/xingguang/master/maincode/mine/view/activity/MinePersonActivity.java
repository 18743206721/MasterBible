package com.xingguang.master.maincode.mine.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.maincode.home.model.HomeBean;
import com.xingguang.master.maincode.home.model.JsonBean;
import com.xingguang.master.maincode.mine.model.MineBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.BitmapUtil;
import com.xingguang.master.util.GetJsonDataUtil;
import com.xingguang.master.util.RoundImageView;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;


/**
 * 创建日期：2018/5/31
 * 描述:个人信息修改页面
 * 作者:LiuYu
 */
public class MinePersonActivity extends ToolBarActivity {

    @BindView(R.id.mine_iv)
    RoundImageView mineIv;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.mine_tvname)
    TextView mineTvname;
    @BindView(R.id.mine_etname)
    EditText mineEtname;
    @BindView(R.id.mine_tvsex)
    TextView mineTvsex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.mine_tvarea)
    TextView mineTvarea;
    @BindView(R.id.mine_rlarea)
    RelativeLayout mineRlarea;
    private int a = 0; //判断是否能点击选择,0是编辑，1完成
    private boolean isshow;
    private List<String> mDatas = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private String img = ""; //图片url
    List<MineBean.DataBean> mineDatas = new ArrayList<>();

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
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_person;
    }

    @Override
    protected void initView() {
        setToolBarTitle("个人信息");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSubTitle("编辑");
        setSubTitleColor(R.color.home_bule);

        getSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isshow = !isshow;
                if (isshow) {
                    a = 1;
                    setSubTitle("完成");
                    setSubTitleColor(R.color.home_bule);
                    mineEtname.setVisibility(View.VISIBLE);
                    mineTvname.setVisibility(View.GONE);

                    mineEtname.setText(AppUtil.getUserNickname(MinePersonActivity.this));
                    mineTvname.setText(AppUtil.getUserNickname(MinePersonActivity.this));

                } else { //完成的时候，点击上传数据，finish
                    if (mineEtname.getText().length() == 0) {
                        ToastUtils.showToast(MinePersonActivity.this, "请输入您的昵称");
                    } else {
                        a = 0;
                        setSubTitle("编辑");
                        setSubTitleColor(R.color.home_bule);
                        mineEtname.setVisibility(View.GONE);
                        mineTvname.setVisibility(View.VISIBLE);

                        mineEtname.setText(AppUtil.getUserNickname(MinePersonActivity.this));
                        mineTvname.setText(AppUtil.getUserNickname(MinePersonActivity.this));
                        load();
                    }
                }
            }
        });

        init();
        Edit();
    }

    /**
     * 回显数据
     */
    private void Edit() {
        String huixianurl = AppUtil.getUserImage(this);
        ImageLoader.loadCircleImage(MinePersonActivity.this, huixianurl, mineIv);
        mineTvname.setText(AppUtil.getUserNickname(this));
        mineTvsex.setText(AppUtil.getUsersex(this));
        mineTvarea.setText(AppUtil.getUserads(this));
    }


    /**
     * 完成接口
     */
    private void load() {
        if (img.equals("")) { //没修改过头像
            loadhead2();
        } else {
            loadhead();
        }
    }

    private void loadhead2() {
        OkGo.<String>post(HttpManager.Login_hy)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", AppUtil.getUserId(this))
                .params("MethodCode", "modify")
                .params("NickName", mineEtname.getText().toString())
                .params("Sex", mineTvsex.getText().toString())
                .params("Address", mineTvarea.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        MineBean bean = gson.fromJson(response.body().toString(), MineBean.class);
                        if (bean.getData() != null) {
                            mineDatas.addAll(bean.getData());
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERNAME, mineDatas.get(0).getYEPrice());
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERIMAGE, HttpManager.BASE_URL+ mineDatas.get(0).getHeadPic());
                            //性别
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERSEX, mineDatas.get(0).getEmail());
                            //地区
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERADS, mineDatas.get(0).getTeam());
                            ToastUtils.showToast(MinePersonActivity.this, bean.getResult());
                            finish();
                        } else {
                            ToastUtils.showToast(MinePersonActivity.this, bean.getResult());
                        }
                    }
                });
    }

    private void loadhead() {
        OkGo.<String>post(HttpManager.Login_hy)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", AppUtil.getUserId(this))
                .params("MethodCode", "modify")
                .params("HeadSculpture",new File(img))
                .params("NickName", mineEtname.getText().toString())
                .params("Sex", mineTvsex.getText().toString())
                .params("Address", mineTvarea.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        MineBean bean = gson.fromJson(response.body().toString(), MineBean.class);
                        if (bean.getData() != null) {
                            mineDatas.addAll(bean.getData());
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERNAME, mineDatas.get(0).getYEPrice());
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERIMAGE, HttpManager.BASE_URL + mineDatas.get(0).getHeadPic());
                            //性别
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERSEX, mineDatas.get(0).getEmail());
                            //地区
                            SharedPreferencesUtils.put(MinePersonActivity.this, SharedPreferencesUtils.USERADS, mineDatas.get(0).getTeam());
                            ToastUtils.showToast(MinePersonActivity.this, bean.getResult());
                            finish();
                        } else {
                            ToastUtils.showToast(MinePersonActivity.this, bean.getResult());
                        }
                    }
                });
    }


    private void init() {
        if (a == 0) {
            mineEtname.setVisibility(View.GONE);
            mineTvname.setVisibility(View.VISIBLE);
        } else {
            mineEtname.setVisibility(View.VISIBLE);
            mineTvname.setVisibility(View.GONE);
        }
        mDatas.add("男");
        mDatas.add("女");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @OnClick({R.id.rl_header, R.id.rl_sex, R.id.mine_rlarea})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_header: //头像
                if (a == 1) {
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(this, PhotoPicker.REQUEST_CODE);
                }
                break;
            case R.id.rl_sex: //性别
                if (a == 1) {
                    selectsex();
                }
                break;
            case R.id.mine_rlarea: //地区
                if (a == 1) {
                    selectarea();
                }
                break;
        }
    }


    /**
     * 选择地区
     */
    private void selectarea() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(MinePersonActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2)
                        + options3Items.get(options1).get(options2).get(options3);
                mineTvarea.setText(tx);
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
     * 选择性别
     */
    private void selectsex() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(MinePersonActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mDatas.get(options1);
                mineTvsex.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setSubCalSize(16)//取消确定按钮文字大小
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择性别")//标题文字
                .build();
        pvOptions.setPicker(mDatas);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                selectedPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (selectedPhotos.size() != 0) {
                    img = BitmapUtil.compressImage(selectedPhotos.get(0));
                }

                ImageLoader.loadCircleImage(MinePersonActivity.this,img,mineIv);

//                Bitmap bmp = BitmapFactory.decodeFile(new File(selectedPhotos.get(0)).toString());//filePath
//                Bitmap bmp= BitmapFactory.decodeResource(res, R.mipmap.flower);
//                AppUtil.qualityCompress(bmp,);


            }

        }
    }


    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        String JsonData = new GetJsonDataUtil().getJson(MinePersonActivity.this, "province.json");//获取assets目录下的json文件数据
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


}
