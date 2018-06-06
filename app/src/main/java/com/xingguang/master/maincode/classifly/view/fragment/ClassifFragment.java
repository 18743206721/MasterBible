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
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.classifly.view.ClassifExamActivity;
import com.xingguang.master.maincode.classifly.view.adapter.LeftListAdapter;
import com.xingguang.master.maincode.classifly.view.adapter.RightAdapter;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.GridItemDecoration;

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
    private String[] rightStr = new String[]{"番茄鸡蛋", "红烧排骨", "农家小炒肉","番茄鸡蛋", "红烧排骨"};
    private LeftListAdapter leftListAdapter;
    private RightAdapter rightListAdapter;

    private boolean isScroll = true;
    private boolean shouldSet;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classif;
    }

    @Override
    protected void initView() {
        setToolBarTitle("分类");

        leftListAdapter = new LeftListAdapter(getActivity(), leftStr, flagArray);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        leftListview.setLayoutManager(manager);
        leftListview.setAdapter(leftListAdapter);


        rightListAdapter = new RightAdapter(getActivity(), rightStr);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
//        rightListView.addItemDecoration(//为recycleview设置分割线
//                new GridSpaceItemDecoration.Builder(rightListView)
//                        .setColNum(2) //列数
//                        .setSpaceSize(10) //设置间距
//                        .build()

//        );

        rightListView.addItemDecoration(new GridItemDecoration(AppUtil.dip2px(getActivity(),10),
                AppUtil.dip2px(getActivity(),10)));
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
                leftListAdapter.notifyDataSetChanged();
//                int rightSection = 0;
//                rightListView.setSelection(rightSection);
            }
        });


        rightListAdapter.setOnItemClickListener(new RightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到考试宝典
              startActivity(new Intent(getActivity(), ClassifExamActivity.class)
              .putExtra("classifid",position+""));
            }
        });


    }

    private void load() {

    }

    @Override
    protected void lazyLoad() {
    }


}
