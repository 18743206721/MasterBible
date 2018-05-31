package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.view.activity.OneDetailsActivity;
import com.xingguang.master.maincode.home.view.adapter.OneMoreAdapter;
import com.xingguang.master.maincode.home.view.adapter.TwoMoreAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 创建日期：2018/5/31
 * 描述:行业资讯列表页面
 * 作者:LiuYu
 */
public class TwoFragment extends ToolBarFragment {

    @BindView(R.id.rv_baokao)
    RecyclerView rvBaokao;
    @BindView(R.id.rl_baokao)
    RelativeLayout rlBaokao;
    @BindView(R.id.empty)
    ImageView empty;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
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
        setToolBarTitle("行业资讯");
        init();
    }

    private void init() {
        TwoMoreAdapter twoAdapter = new TwoMoreAdapter(getActivity(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(twoAdapter);

        twoAdapter.setOnItemClickListener(new TwoMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //行业资讯
                startActivity(new Intent(getActivity(),WebViewActivity.class)
                        .putExtra("id",1)
                        .putExtra("classid",position)
                ); //传1显示上下翻页按钮
            }
        });

    }

    @Override
    protected void lazyLoad() {

    }



}
