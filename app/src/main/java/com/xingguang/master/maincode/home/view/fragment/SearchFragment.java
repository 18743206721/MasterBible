package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.view.adapter.SearchAdapter;
import com.xingguang.master.maincode.home.view.adapter.TwoMoreAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/31
 * 描述:搜索界面
 * 作者:LiuYu
 */
public class SearchFragment extends BaseFragment {

    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.tv_play_serch)
    EditText tvPlaySerch;
    @BindView(R.id.tv_sousuo)
    TextView tvSousuo;
    @BindView(R.id.ll_sousuo_serch)
    LinearLayout llSousuoSerch;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.rl_baokao)
    RelativeLayout rlBaokao;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.rlsearch)
    RelativeLayout rlsearch;
    private List<String> list = new ArrayList<>();
    private SearchAdapter twoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        init();
    }

    private void init() {
        twoAdapter = new SearchAdapter(getActivity(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvHome.setLayoutManager(manager);
        rvHome.setAdapter(twoAdapter);

        twoAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //根据后台状态，区分跳转到招工，或者webview页面

//                //行业资讯
//                startActivity(new Intent(getActivity(),WebViewActivity.class)
//                        .putExtra("id",1)
//                        .putExtra("classid",position)
//                ); //传1显示上下翻页按钮
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.iv_finish, R.id.tv_sousuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
                break;
            case R.id.tv_sousuo:
                if (TextUtils.isEmpty(tvPlaySerch.getText().toString())){
                    ToastUtils.showToast(getActivity(),"请输入搜索内容！");
                }else{
                    serarchload();
                }
                break;
        }
    }


    private void serarchload() {

    }


}
