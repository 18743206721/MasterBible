package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.view.activity.OneDetailsActivity;
import com.xingguang.master.maincode.home.view.adapter.OneAdapter;
import com.xingguang.master.maincode.home.view.adapter.OneMoreAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/31
 * 描述:招工申请 更多信息
 * 作者:LiuYu
 */
public class OneFragment extends ToolBarFragment {

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
        setToolBarTitle("招工信息");
        init();
    }

    private void init() {
        OneMoreAdapter oneAdapter = new OneMoreAdapter(getActivity(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(oneAdapter);

        oneAdapter.setOnItemClickListener(new OneMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //招工详情
                startActivity(new Intent(getActivity(),OneDetailsActivity.class)
                .putExtra("id",position));
            }
        });

    }

    @Override
    protected void lazyLoad() {

    }




}
