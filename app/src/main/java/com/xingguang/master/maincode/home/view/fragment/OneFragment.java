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
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.OneBean;
import com.xingguang.master.maincode.home.view.activity.OneDetailsActivity;
import com.xingguang.master.maincode.home.view.adapter.OneAdapter;
import com.xingguang.master.maincode.home.view.adapter.OneMoreAdapter;
import com.xingguang.master.refresh.RefreshUtil;
import com.xingguang.master.util.ToastUtils;

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
public class OneFragment extends ToolBarFragment implements RefreshUtil.OnRefreshListener{

    @BindView(R.id.rv_baokao)
    RecyclerView rvBaokao;
    @BindView(R.id.rl_baokao)
    RelativeLayout rlBaokao;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.tw_Refresh)
    TwinklingRefreshLayout tw_refresh;

    int page = 1;
    private boolean isRefresh = false;

    private List<OneBean.DataBean.ListBean> list = new ArrayList<>();
    private OneMoreAdapter oneAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {
        tw_refresh.setHeaderView(new SinaRefreshView(getActivity()));
        tw_refresh.setBottomView(new LoadingView(getActivity()));
        tw_refresh.setOnRefreshListener(new RefreshUtil(this).refreshListenerAdapter());

        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
            }
        });
        setToolBarTitle("招工信息");
        init();
        load(1);
    }

    private void init() {
        oneAdapter = new OneMoreAdapter(getActivity(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(oneAdapter);

        oneAdapter.setOnItemClickListener(new OneMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //招工详情
                startActivity(new Intent(getActivity(),OneDetailsActivity.class)
                .putExtra("id",list.get(position).getID()));
            }
        });

    }

    /**
     *更多招工列表
     *
     */
    private void load(final int page) {
        OkGo.<String>post(HttpManager.Recruit)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode","list")
                .params("PageNum",page)
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        OneBean bean = gson.fromJson(response.body().toString(), OneBean.class);
                        if (bean.getData()!=null){

                            if (bean.getData().getList().size() == 0 && page != 1) {
                                Toast.makeText(getActivity(),
                                        "只有这么多了~",
                                        Toast.LENGTH_SHORT).show();
                            }

                            if (page == 1){
                                list.clear();
                            }

                            list.addAll(bean.getData().getList());
                            oneAdapter.setList(list);

                            if (list.size() == 0){
                                rlBaokao.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                            }else {
                                rlBaokao.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);
                            }


                        }else{
                            ToastUtils.showToast(getActivity(),bean.getMsg());
                        }

                        if (isRefresh) {
                            tw_refresh.finishRefreshing();
                        } else {
                            tw_refresh.finishLoadmore();
                        }



                    }
                });
    }


    @Override
    protected void lazyLoad() {}


    @Override
    public void onRefresh() {
        isRefresh = true;
        load(1);
    }

    @Override
    public void onLoad() {
        isRefresh = false;
        load(page++);
    }



}
