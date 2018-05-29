package com.xingguang.master.maincode.home.model;

import android.widget.TextView; /**
 * 创建日期：2018/5/29
 * 描述:事件类
 * 作者:LiuYu
 */
public class MessageEvent {

    private TextView etBumeng;

    public MessageEvent(TextView etBumeng) {
        this.etBumeng = etBumeng;
    }

    public TextView getEtBumeng() {
        return etBumeng;
    }

    public void setEtBumeng(TextView etBumeng) {
        this.etBumeng = etBumeng;
    }

}
