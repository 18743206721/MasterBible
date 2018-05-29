/**
 * @param
 */
package com.xingguang.master.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.xingguang.master.R;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.wheel.ArrayWheelAdapter;
import com.xingguang.master.wheel.OnWheelChangedListener;
import com.xingguang.master.wheel.WheelView;


/**
 * @param
 */
public class WheelPopUpWindow extends PopupWindow implements
        OnWheelChangedListener, OnClickListener {

    private Context context;
    private String datas;
    private WheelView mView;
    private LinearLayout address_ll;
    private TextView cancle, complete;
    protected String[] mDatas = null;

    /**
     * 数据
     */
    protected String mCurrentExperienceName;
    private String from;

    public WheelPopUpWindow(Context context, View parent,
                            String data, String[] mDatas, String froms) {
        View view = View.inflate(context, R.layout.work_experience_popup, null);

        this.context = context;
        this.datas = data;
        this.mDatas = mDatas;
        this.from = froms;

        mView = (WheelView) view.findViewById(R.id.id_experience);
        complete = (TextView) view.findViewById(R.id.complete);
        cancle = (TextView) view.findViewById(R.id.cancle);
        address_ll = (LinearLayout) view.findViewById(R.id.address_ll);
        address_ll.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.address_bottom_in));
        // 添加change事件
        mView.addChangingListener(this);

        complete.setOnClickListener(this);
        cancle.setOnClickListener(this);

        mView.setViewAdapter(new ArrayWheelAdapter<String>(context,
                mDatas));
        // 设置可见条目数量
        mView.setVisibleItems(7);

        if (!TextUtils.isEmpty(data)) {
            setExperienceView();
        }

        if (TextUtils.isEmpty(data)) {
            datas = mDatas[0];
        }

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x99000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.TOP, 0, 0);
        update();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        datas = mDatas[newValue];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:
                Message msg = null;
//                if (context == RegisterActivity.instance){
//                    msg = RegisterActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 9;
//                    msg.obj = datas;
//                    msg.sendToTarget();
//                }else
//                if (context == RentFindActivity.instance) {
//                    msg = RentFindActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 2;
//                } else if (context == ReadilyActivity.instance) {
//                    msg = ReadilyActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 1;
//                } else if (context == VipActivity.instance) {
//                    msg = VipActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 3;
//                } else if (context == MaintenanceActivity.instance) {
//                    msg = MaintenanceActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 10;
//                } else if (context == ComplaintsAddDetailActivity.instance) {
//                    msg = ComplaintsAddDetailActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 13;
//                } else if (context == AdsActivity.instance) {
//                    msg = AdsActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 14;
//                }
//                else if (context == PersonInfActivity.instance){
//                    msg = PersonInfActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = 15;
//                }
//                //简历相关10xx
//                else if (context == MyResumeActivity.instance) {
//                    msg = MyResumeActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = Integer.parseInt(from);
//                }
//                //企业发布招聘相关11xx
//                else if (context == SendHiringMessageActivity.instance) {
//                    msg = SendHiringMessageActivity.instance.mHandler
//                            .obtainMessage();
//                    msg.what = Integer.parseInt(from);
//                }

//                msg = activity.mHandler
//                            .obtainMessage();
//                    msg.what = 1;

                msg.obj = datas;
                msg.sendToTarget();
                dismiss();
                break;
            case R.id.cancle:
                dismiss();
                break;

            default:
                break;
        }
    }

    /**
     * 回显
     */
    private void setExperienceView() {
        int ExperienceCount = 0;
        for (int i = 0, j = mDatas.length; i < j; i++) {
            ExperienceCount++;
            if (datas.equals(mDatas[i])) {
                break;
            }
        }
        mView.setCurrentItem(ExperienceCount - 1);
        datas = mDatas[ExperienceCount - 1];
    }
}
