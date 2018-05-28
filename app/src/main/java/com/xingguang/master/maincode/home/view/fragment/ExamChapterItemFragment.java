package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.maincode.home.view.activity.ExamChapterActivity;
import com.xingguang.master.maincode.home.view.adapter.BaoDianItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/26
 * 描述:模拟考试里分类下的工种
 * 作者:LiuYu
 */
public class ExamChapterItemFragment extends BaseFragment {

    @BindView(R.id.rv_looksp)
    RecyclerView rvLooksp;
    @BindView(R.id.my_deco_fg_apply)
    RelativeLayout myDecoFgApply;
    @BindView(R.id.empty)
    ImageView empty;
    private BaoDianItemAdapter adapter;
    private List<String> list = new ArrayList<>();

    int type;

    public static ExamChapterItemFragment newInstance(int type) {
        ExamChapterItemFragment fragment = new ExamChapterItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


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
        initListener();
    }

    private void initAdapter() {
        adapter = new BaoDianItemAdapter(getActivity(), list, type);
        GridLayoutManager mgr = new GridLayoutManager(getActivity(), 2);
//        rvLooksp.addItemDecoration(//为recycleview设置分割线
//                new GridSpaceItemDecoration.Builder(rvLooksp)
//                        .setColNum(2) //列数
//                        .setSpaceSize(13) //设置间距
//                        .build()
//        );
        rvLooksp.setLayoutManager(mgr);
        rvLooksp.setAdapter(adapter);
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaoDianItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), ExamChapterActivity.class)); //跳转到模拟考试
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }


}
