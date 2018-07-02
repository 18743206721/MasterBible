package com.xingguang.master.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.xingguang.master.R;


/**
 * @auathor LiuYu
 * @date 2018/06/08
 */

public class RefreshHeadView implements IHeaderView {
    private View view;
    private Context context;
    private ImageView iv;

    public RefreshHeadView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.refresh_layout, null);
        iv = (ImageView) view.findViewById(R.id.refresh_iv);
        iv.setMinimumHeight(75);
        iv.setMinimumHeight(75);
        iv.setImageResource(R.drawable.refresh_animation);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.stop();
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

    }
}
