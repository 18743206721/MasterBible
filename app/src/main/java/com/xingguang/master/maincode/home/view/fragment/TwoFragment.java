package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.OneBean;
import com.xingguang.master.maincode.home.model.TwoBean;
import com.xingguang.master.maincode.home.view.activity.OneDetailsActivity;
import com.xingguang.master.maincode.home.view.adapter.OneMoreAdapter;
import com.xingguang.master.maincode.home.view.adapter.TwoMoreAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.refresh.RefreshUtil;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 创建日期：2018/5/31
 * 描述:行业资讯列表页面
 * 作者:LiuYu
 */
public class TwoFragment extends ToolBarFragment implements RefreshUtil.OnRefreshListener{

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


    private List<TwoBean.DataBean.ListBean> list = new ArrayList<>();
    private TwoMoreAdapter twoAdapter;


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
        setToolBarTitle("行业资讯");
        init();
        load(1);
        initListener();
    }

    private void init() {
        twoAdapter = new TwoMoreAdapter(getActivity(),list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvBaokao.setLayoutManager(manager);
        rvBaokao.setAdapter(twoAdapter);
    }

    private void load(final int page) {
        OkGo.<String>post(HttpManager.Information)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode","list")
                .params("PageNum",page)
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TwoBean bean = gson.fromJson(response.body().toString(), TwoBean.class);
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

                            if (list.size() == 0){
                                rlBaokao.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                            }else {
                                rlBaokao.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);
                            }

                            twoAdapter.setList(list);
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

    private void initListener() {
        twoAdapter.setOnItemClickListener(new TwoMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //行业资讯
                startActivity(new Intent(getActivity(),WebViewActivity.class)
                        .putExtra("id",1)
                        .putExtra("classid",list.get(position).getID())
                ); //传1显示上下翻页按钮
            }
        });

    }


    @Override
    protected void lazyLoad() {

    }


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
