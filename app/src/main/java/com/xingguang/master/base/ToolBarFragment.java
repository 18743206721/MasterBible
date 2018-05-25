package com.xingguang.master.base;

import android.support.annotation.ColorRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xingguang.master.R;
import butterknife.BindView;

/**
 * Fragment with ToolBar 基类
 *
 * @author BiHaidong
 * @date 2017-4-25
 */
public abstract class ToolBarFragment extends BaseFragment {

    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubTitle;
    @BindView(R.id.toolbar_subimg)
    ImageView mToolbarSubImg;
    @BindView(R.id.toolbar_subimg_two)
    ImageView mToolbarSubImgTwo;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // 是否显示返回按钮
    private Boolean backFlag = true;

    // 是否显示ToolBar();
    private Boolean toolBarFlag = true;

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部副标题的TextView
     *
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 获取头部副标题的ImageView
     *
     * @return
     */
    public ImageView getSubImgTwo() {
        return mToolbarSubImgTwo;
    }

    /**
     * 设置头部副标题的ImageView
     *
     * @return
     */
    public void setSubImgTwo(int res) {
        mToolbarSubImgTwo.setVisibility(View.VISIBLE);
        mToolbarSubImgTwo.setImageResource(res);
    }

    /**
     * 获取头部副标题的ImageView
     *
     * @return
     */
    public ImageView getSubImg() {
        return mToolbarSubImg;
    }

    /**
     * 设置头部副标题的ImageView
     *
     * @return
     */
    public void setSubImg(int res) {
        mToolbarSubImg.setVisibility(View.VISIBLE);
        mToolbarSubImg.setImageResource(res);
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
        }
    }

    /**
     * 设置头部副标题
     *
     * @param title
     */
    public void setSubTitle(CharSequence title) {
        if (mToolbarSubTitle != null) {
            mToolbarSubTitle.setVisibility(View.VISIBLE);
            mToolbarSubTitle.setText(title);
        }
    }

    /**
     * 设置头部副标题和颜色
     *
     * @param title
     */
    public void setSubTitle(CharSequence title, @ColorRes int id) {
        if (mToolbarSubTitle != null) {
            mToolbarSubTitle.setVisibility(View.VISIBLE);
            mToolbarSubTitle.setText(title);
            mToolbarSubTitle.setTextColor(getResources().getColor(id));
        }
    }

    /**
     * 设置头部副标题颜色
     */
    public void setSubTitleColor(@ColorRes int id) {
        if (mToolbarSubTitle != null && View.VISIBLE == mToolbarSubTitle.getVisibility()) {
            mToolbarSubTitle.setTextColor(getResources().getColor(id));
        }
    }

    /**
     * 设置副标题点击事件
     *
     * @param listener
     */
    public void setSubClickListener(View.OnClickListener listener) {
        if (View.VISIBLE == mToolbarSubTitle.getVisibility()) {
            mToolbarSubTitle.setOnClickListener(listener);
        } else if (View.VISIBLE == mToolbarSubImg.getVisibility()) {
            mToolbarSubImg.setOnClickListener(listener);
        }
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }
}
