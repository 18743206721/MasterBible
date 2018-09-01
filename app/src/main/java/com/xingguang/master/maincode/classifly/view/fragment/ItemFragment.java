package com.xingguang.master.maincode.classifly.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.classifly.model.KaoShiSearchBean;
import com.xingguang.master.maincode.classifly.model.KaoshiJIgouBean;
import com.xingguang.master.maincode.classifly.view.ClassifExamActivity;
import com.xingguang.master.maincode.classifly.view.adapter.ItemAdapter;
import com.xingguang.master.maincode.classifly.view.adapter.ItemSearchAdapter;
import com.xingguang.master.maincode.classifly.view.adapter.KaoShiAdapter;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.view.activity.ExamBaoDianActivity;
import com.xingguang.master.maincode.home.view.activity.SearchActivity;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.refresh.RefreshUtil;
import com.xingguang.master.util.AppUtil;
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
public class ItemFragment extends BaseFragment implements RefreshUtil.OnRefreshListener {

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
    @BindView(R.id.tw_refresh)
    TwinklingRefreshLayout tw_refresh;


    private ItemAdapter adapter;
    private List<BuMengBean.DataBeanX.DataBean> listgongzhong = new ArrayList<>();
    private List<KaoShiSearchBean.DataBean> listsearch = new ArrayList<>();
    int type;
    private int bumenID;
    ItemSearchAdapter searchAdapter;
    public int size;

    //考试机构列表
//    private List<KaoshiJIgouBean.DataBean.ListBean> kaoshilist = new ArrayList<>();
//    private KaoShiAdapter kaoshiadapter;

    private boolean isRefresh = false;
    private int page = 1;

    public ItemFragment(int type, int size) {
        this.type = type;
        this.size = size;
    }

    public ItemFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void initView() {
        tw_refresh.setHeaderView(new SinaRefreshView(getActivity()));
        tw_refresh.setBottomView(new LoadingView(getActivity()));
        tw_refresh.setOnRefreshListener(new RefreshUtil(this).refreshListenerAdapter());


//        if (size - 1 >= type) {
        initAdapter();
        loadbumen();
        initListener();
//        } else { //考试机构

//            LinearLayoutManager mgr = new LinearLayoutManager(getActivity());
//            rvLooksp.setLayoutManager(mgr);
//            kaoshiadapter = new KaoShiAdapter(getActivity(), kaoshilist, type);
//            rvLooksp.setAdapter(kaoshiadapter);
//            loadkaoshi(1);
//            kaoshiadapter.setOnItemClickListener(new KaoShiAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    startActivity(new Intent(getActivity(), WebViewActivity.class)
//                            .putExtra("id", 3)
//                            .putExtra("classid", kaoshilist.get(position).getID())
//                    );
//                }
//            });

//        }
    }

    //考试机构
//    private void loadkaoshi(final int page) {
//        OkGo.<String>post(HttpManager.ExaminingBody)
//                .tag(this)
//                .cacheKey("cachePostKey")
//                .cacheMode(CacheMode.DEFAULT)
//                .params("MethodCode", "list")
//                .params("PageNum", page)
//                .execute(new DialogCallback<String>(getActivity()) {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Gson gson = new Gson();
//                        KaoshiJIgouBean bean = gson.fromJson(response.body().toString(), KaoshiJIgouBean.class);
//                        if (bean.getData() != null) {
//                            if (page == 1) {
//                                kaoshilist.clear();
//                            }
//                            kaoshilist.addAll(bean.getData().getList());
//
//                            if (bean.getData().getList().size() == 0) {
////                                ToastUtils.showToast(getActivity(), "暂无更多!");
//                            } else {
//                                kaoshiadapter.setList(kaoshilist);
//                            }
//
//                            if (isRefresh) {
//                                tw_refresh.finishRefreshing();
//                            } else {
//                                tw_refresh.finishLoadmore();
//                            }
//
//                        }
//
//
//                    }
//                });
//    }

    private void initAdapter() {
        adapter = new ItemAdapter(getActivity(), listgongzhong, type);
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 2);
        rvLooksp.setLayoutManager(mgr);
        rvLooksp.setAdapter(adapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        loadbumen();
        initListener();
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
                .params("UserName", AppUtil.getShenFenId(getActivity()))
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BuMengBean bean = gson.fromJson(response.body().toString(), BuMengBean.class);
                        if (bean.getData() != null) {
                            if (listgongzhong.size() != 0) {
                                listgongzhong.clear();
                            }
                            for (int i = 0; i < bean.getData().size(); i++) {
                                if (type - 1 == i) {
                                    bumenID = bean.getData().get(i).getID();
                                    listgongzhong.addAll(bean.getData().get(i).getData());
                                }
                            }
                            adapter.setList(listgongzhong);

                            if (isRefresh) {
                                tw_refresh.finishRefreshing();
                            } else {
                                tw_refresh.finishLoadmore();
                            }


                        }
                    }
                });
    }

    private void initListener() {
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (AppUtil.isShenFened(getActivity())) {
                    if (listgongzhong.get(position).getIsEnabled() == 1) {
                        startActivity(new Intent(getActivity(), ClassifExamActivity.class)
                                .putExtra("name", listgongzhong.get(position).getName())
                                .putExtra("bumenId", bumenID)
                                .putExtra("gongzhongId", listgongzhong.get(position).getID())
                        );//跳转到练习入口
                    } else {
                        ToastUtils.showToast(getActivity(), "请选择正确的工种进行答题!");
                    }
                }
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
//            if (type == size) { // 考试机构
//                loadkaoshisearch();
//            } else {
                loadlist();
//            }

        }
    }

    //考试机构搜索
    private void loadkaoshisearch() {
        OkGo.<String>post(HttpManager.ExaminingBody)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .params("PageNum", page)
                .params("Selectkeyword", tvPlaySerch.getText().toString())
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoshiJIgouBean bean = gson.fromJson(response.body().toString(), KaoshiJIgouBean.class);
                        if (bean.getData() != null) {
//                            kaoshilist.clear();
//                            kaoshilist.addAll(bean.getData().getList());
                            if (bean.getData().getList().size() == 0) {
                                ToastUtils.showToast(getActivity(), "暂无搜索数据!");
                            } else {
//                                kaoshiadapter.setList(kaoshilist);
                            }


                        }


                    }
                });


    }

    //搜索的数据
    private void loadlist() {
        OkGo.<String>post(HttpManager.ProjectSearch)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .params("ClassID", bumenID)
                .params("Selectkeyword", tvPlaySerch.getText().toString())
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiSearchBean bean = gson.fromJson(response.body().toString(), KaoShiSearchBean.class);
                        if (bean.getData() != null) {
                            listgongzhong.clear();
                            adapter.setList(listgongzhong);
                            listsearch.clear();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                if (type - 1 == i) {
                                    listsearch.addAll(bean.getData());
                                }
                            }
                            searchAdapter = new ItemSearchAdapter(getActivity(), listsearch, type);
                            GridLayoutManager mgr = new GridLayoutManager(getActivity(), 2);
                            rvLooksp.setLayoutManager(mgr);
                            rvLooksp.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();

                            searchAdapter.setOnItemClickListener(new ItemSearchAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivity(new Intent(getActivity(), ClassifExamActivity.class)
                                            .putExtra("name", listsearch.get(position).getName())
                                            .putExtra("bumenId", bumenID)
                                            .putExtra("gongzhongId", listsearch.get(position).getID())
                                    );//跳转到练习入口
                                }
                            });


                        }


                        if (bean.getData().size() == 0) {
                            ToastUtils.showToast(getActivity(), "暂无搜索数据!");
                        }


                    }
                });


    }


    @Override
    public void onLoad() {
        isRefresh = false;
//        if (type == size) {
//            page++;
//            loadkaoshi(page);
//        } else {
            loadbumen();
//        }

    }

    @Override
    public void onRefresh() {
        isRefresh = true;
//        if (type == size) {
//            loadkaoshi(page);
//        } else {
            loadbumen();
//        }

    }


}
