package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.github.qing.itemdecoration.GridSpaceItemDecoration;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.view.activity.ExamBaoDianActivity;
import com.xingguang.master.maincode.home.view.activity.ExamChapterActivity;
import com.xingguang.master.maincode.home.view.adapter.BaoDianItemAdapter;
import com.xingguang.master.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/26
 * 描述:练习分类下的工种
 * 作者:LiuYu
 */
public class BaodianItemFragment extends BaseFragment {

    @BindView(R.id.rv_looksp)
    RecyclerView rvLooksp;
    @BindView(R.id.my_deco_fg_apply)
    RelativeLayout myDecoFgApply;
    @BindView(R.id.empty)
    ImageView empty;
    private BaoDianItemAdapter adapter;
    private List<BuMengBean.DataBeanX.DataBean> listgongzhong = new ArrayList<>();
    int type;

    public static BaodianItemFragment newInstance(int type) {
        BaodianItemFragment fragment = new BaodianItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int bumenID;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_baodian;
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getInt("type");
        }

        initAdapter();
        loadbumen();
        initListener();
    }

    private void initAdapter() {
        adapter = new BaoDianItemAdapter(getActivity(), listgongzhong, type);
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 2);
        rvLooksp.setLayoutManager(mgr);
        rvLooksp.setAdapter(adapter);
    }

    /**
     * 工种数据
     */
    private void loadbumen() {
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
                                if (type - 1 == i) {
                                    bumenID = bean.getData().get(i).getID();
                                    listgongzhong.addAll(bean.getData().get(i).getData());
                                }
                            }
                            adapter.setList(listgongzhong);
                        }
                    }
                });
    }

    private void initListener() {
            adapter.setOnItemClickListener(new BaoDianItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(getActivity(), ExamBaoDianActivity.class)
                            .putExtra("bumenId", bumenID)
                            .putExtra("gongzhongId", listgongzhong.get(position).getID())
                    ); //跳转到练习入口
                }
            });
    }

    @Override
    protected void lazyLoad() {
    }


}
