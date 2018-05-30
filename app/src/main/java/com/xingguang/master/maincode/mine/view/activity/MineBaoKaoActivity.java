package com.xingguang.master.maincode.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xingguang.master.BaoKaoDetailsActivity;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.maincode.mine.view.MineBaoKaoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建日期：2018/5/30
 * 描述:报考记录
 * 作者:LiuYu
 */
public class MineBaoKaoActivity extends ToolBarActivity {

    @BindView(R.id.rv_baokao)
    RecyclerView rvBaokao;
    @BindView(R.id.rl_baokao)
    RelativeLayout rlBaokao;
    @BindView(R.id.empty)
    ImageView empty;
    MineBaoKaoAdapter adapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_bao_kao;
    }

    @Override
    protected void initView() {
        setToolBarTitle("报考记录");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
    }

    private void init() {
        adapter = new MineBaoKaoAdapter(MineBaoKaoActivity.this,mList);
        LinearLayoutManager manager = new LinearLayoutManager(MineBaoKaoActivity.this);
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(adapter);

        adapter.setOnItemClickListener(new MineBaoKaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MineBaoKaoActivity.this,BaoKaoDetailsActivity.class));
            }
        });

    }



}
