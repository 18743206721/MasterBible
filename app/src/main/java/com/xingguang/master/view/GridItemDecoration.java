package com.xingguang.master.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 创建日期：2018/6/4
 * 描述:
 * 作者:LiuYu
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {


    private int leftRight;
    private int topBottom;

    public GridItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        final int childPosition = parent.getChildAdapterPosition(view);
        final int spanCount = layoutManager.getSpanCount();

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {

            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {//第一排的需要上面
                outRect.top = topBottom;
            }
            outRect.bottom = topBottom;
            if (lp.getSpanSize() == spanCount) {//占满

                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                outRect.left = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * leftRight);
                outRect.right = (int) (((float) leftRight * (spanCount + 1) / spanCount) - outRect.left);
            }
        } else {

            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {
                outRect.left = leftRight;
            }
            outRect.right = leftRight;
            if (lp.getSpanSize() == spanCount) {//占满
                outRect.top = topBottom;
                outRect.bottom = topBottom;

            } else {
                outRect.top = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * topBottom);
                outRect.bottom = (int) (((float) topBottom * (spanCount + 1) / spanCount) - outRect.top);
            }
        }
    }













}
