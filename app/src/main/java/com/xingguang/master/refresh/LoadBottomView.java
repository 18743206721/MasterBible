package com.xingguang.master.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.xingguang.master.R;

/**
 * @auathor LiuYu
 * @date 2018/06/08
 */

public class LoadBottomView implements IBottomView {
    private Context context;
    private View bottomView;
    private ImageView iv;


    public LoadBottomView(Context context) {
        this.context = context;
        bottomView = LayoutInflater.from(context).inflate(R.layout.load_more, null);
        iv = (ImageView) bottomView.findViewById(R.id.load_iv);
        iv.setImageResource(R.drawable.load_animation);
    }

    @Override
    public View getView() {
        return bottomView;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onFinish() {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.stop();
    }

    @Override
    public void reset() {

    }
}
