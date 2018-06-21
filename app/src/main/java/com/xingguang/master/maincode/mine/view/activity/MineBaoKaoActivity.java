package com.xingguang.master.maincode.mine.view.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.ProgramsBean;
import com.xingguang.master.maincode.mine.model.BaoKaoGuanLiBean;
import com.xingguang.master.maincode.mine.view.adapter.MineBaoKaoAdapter;
import com.xingguang.master.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


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
    private List<BaoKaoGuanLiBean.DataBean> mList = new ArrayList<>();
    private View headview;

    int type = 0; //2是报考记录，1是培训记录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_bao_kao;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            setToolBarTitle("培训记录");
        } else if (type == 2) {
            setToolBarTitle("报考记录");
        }

        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
        load();
        initListener();
    }

    private void init() {
        adapter = new MineBaoKaoAdapter(MineBaoKaoActivity.this, mList, type);
        LinearLayoutManager manager = new LinearLayoutManager(MineBaoKaoActivity.this);
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(adapter);

        //头布局的xml
        headview = LayoutInflater.from(this).inflate(R.layout.item_mine_baokaoheader, null);
        TextView tv1 = headview.findViewById(R.id.tv1);
        TextView tv2 = headview.findViewById(R.id.tv2);
        TextView tv3 = headview.findViewById(R.id.tv3);
        adapter.addHeaderView(headview);//添加头布局到列表中；
        if (type == 1) {
            tv1.setText("培训工种");
            tv2.setText("选择部门");
            tv3.setText("培训时间");
        } else if (type == 2) {
            tv1.setText("报考部门");
            tv2.setText("报考职务");
            tv3.setText("报考时间");
        }

    }

    private void load() {
        OkGo.<String>post(HttpManager.Login_record)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .params("UserName", AppUtil.getUserId(this))
                .params("ListType", type)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BaoKaoGuanLiBean bean = gson.fromJson(response.body().toString(), BaoKaoGuanLiBean.class);
                        if (bean.getData() != null) {
                            mList.addAll(bean.getData());
                            adapter.setList(mList);
                        }

                    }
                });
    }


    private void initListener() {
        adapter.setOnItemClickListener(new MineBaoKaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MineBaoKaoActivity.this, BaoKaoDetailsActivity.class)
                        .putExtra("type", type)
                        .putExtra("id", mList.get(position-1).getID())
                );
            }
        });
    }


}
