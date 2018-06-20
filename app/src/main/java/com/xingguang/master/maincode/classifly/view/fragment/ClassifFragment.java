package com.xingguang.master.maincode.classifly.view.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.qing.itemdecoration.GridSpaceItemDecoration;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.classifly.view.ClassifExamActivity;
import com.xingguang.master.maincode.classifly.view.adapter.LeftListAdapter;
import com.xingguang.master.maincode.classifly.view.adapter.RightAdapter;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/25
 * 描述:分类
 * 作者:LiuYu
 */
public class ClassifFragment extends ToolBarFragment {

    @BindView(R.id.left_listview)
    RecyclerView leftListview;
    @BindView(R.id.right_listView)
    RecyclerView rightListView;
    private String[] leftStr = new String[]{"安监局", "安监局", "安监局", "安监局", "安监局"};
    private boolean[] flagArray = {true, false, false, false, false};
    private String[] rightStr = new String[]{"番茄鸡蛋", "红烧排骨", "农家小炒肉", "番茄鸡蛋", "红烧排骨"};
    private LeftListAdapter leftListAdapter;
    private RightAdapter rightListAdapter;

    private boolean isScroll = true;
    private boolean shouldSet;

    private List<String> listbumen = new ArrayList<>();
    private int bumenId; //部门id
    private List<BuMengBean.DataBeanX.DataBean> listgongzhong = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classif;
    }

    @Override
    protected void initView() {
        setToolBarTitle("分类");

        leftListAdapter = new LeftListAdapter(getActivity(), listbumen, flagArray);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        leftListview.setLayoutManager(manager);
        leftListview.setAdapter(leftListAdapter);


        rightListAdapter = new RightAdapter(getActivity(), listgongzhong);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
//        rightListView.addItemDecoration(//为recycleview设置分割线
//                new GridSpaceItemDecoration.Builder(rightListView)
//                        .setColNum(2) //列数
//                        .setSpaceSize(10) //设置间距
//                        .build()
//        );

        rightListView.addItemDecoration(new GridItemDecoration(AppUtil.dip2px(getActivity(), 10),
                AppUtil.dip2px(getActivity(), 10)));
        rightListView.setLayoutManager(layoutManager);
        rightListView.setAdapter(rightListAdapter);


        initListener();
        load();

    }

    private void initListener() {
//        leftListview.setItemChecked(0, true);
        leftListAdapter.setOnItemClickListener(new LeftListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TextView bg_tv, int position) {
                isScroll = false;
                for (int i = 0; i < leftStr.length; i++) {
                    if (i == position) {
                        flagArray[i] = true;
                    } else {
                        flagArray[i] = false;
                    }
                }
                loadgongzhong(listbumen.get(position));
                leftListAdapter.notifyDataSetChanged();
            }
        });


        rightListAdapter.setOnItemClickListener(new RightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到考试宝典
                startActivity(new Intent(getActivity(), ClassifExamActivity.class)
                        .putExtra("classifid", position + ""));
            }
        });


    }

    /**
     * 部门数据
     */
    private void load() {
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
                                listbumen.add(bean.getData().get(i).getName());
                            }
                            leftListAdapter.setList(listbumen);
                        }
                    }
                });
    }

    /**
     * 工种数据
     *
     * @param tx
     */
    private void loadgongzhong(final String tx) {
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
                                if (tx.equals(bean.getData().get(i).getName())) {
                                    bumenId = bean.getData().get(i).getID(); //获取部门id数据
                                    for (int j = 0; j < bean.getData().get(i).getData().size(); j++) {
//                                        listgongzhong.add(bean.getData().get(i).getData().get(j).getName());
                                        listgongzhong.addAll(bean.getData().get(i).getData());
                                    }

                                }
                            }

                            rightListAdapter.setList(listgongzhong);

                        }
                    }
                });
    }


    @Override
    protected void lazyLoad() {}


}
