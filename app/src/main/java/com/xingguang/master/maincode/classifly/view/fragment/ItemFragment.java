package com.xingguang.master.maincode.classifly.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.classifly.view.adapter.ItemAdapter;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.view.activity.ExamBaoDianActivity;
import com.xingguang.master.maincode.home.view.activity.SearchActivity;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/7/21
 * 描述:
 * 作者:LiuYu
 */
@SuppressLint("ValidFragment")
public class ItemFragment extends BaseFragment {

    @BindView(R.id.rv_looksp)
    RecyclerView rvLooksp;
    @BindView(R.id.my_deco_fg_apply)
    RelativeLayout myDecoFgApply;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.tv_play_serch)
    EditText tvPlaySerch;
    @BindView(R.id.tv_sousuo)
    TextView tvSousuo;
    @BindView(R.id.ll_sousuo_serch)
    LinearLayout llSousuoSerch;
    private ItemAdapter adapter;
    private List<BuMengBean.DataBeanX.DataBean> listgongzhong = new ArrayList<>();
    int type;
    private int bumenID;

    public ItemFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initView() {
        initAdapter();
        loadbumen();
        initListener();
    }

    private void initAdapter() {
        adapter = new ItemAdapter(getActivity(), listgongzhong, type);
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 2);
//       RecyclerView.ItemDecoration itemDecoration = rvLooksp.getItemDecorationAt(0);
//        if (itemDecoration == null) {
//            rvLooksp.addItemDecoration(new SpaceItemDecoration(30));
//        }
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
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
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


    @OnClick(R.id.tv_sousuo)
    public void onViewClicked() {
        if (TextUtils.isEmpty(tvPlaySerch.getText().toString())) {
            ToastUtils.showToast(getActivity(), "请输入搜索内容！");
        } else {
            loadlist();
        }
    }

    //搜索的数据
    private void loadlist() {

    }


}
