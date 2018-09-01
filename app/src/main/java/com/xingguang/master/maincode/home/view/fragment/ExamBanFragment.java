package com.xingguang.master.maincode.home.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.TextQuestionsBean;
import com.xingguang.master.maincode.home.view.activity.DaTiActivity;
import com.xingguang.master.maincode.home.view.activity.ExamResultActivity;
import com.xingguang.master.maincode.home.view.activity.FiBaodianActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;
import com.xingguang.master.view.NoScrollViewpager;
import com.xingguang.master.view.TimerTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/26
 * 描述:考试答题
 * 作者:LiuYu
 */
@SuppressLint("ValidFragment")
public class ExamBanFragment extends BaseFragment implements CountDownTimerUtil.CountDownTimerListener,
        FragmentBackHandler {

    @BindView(R.id.tv_yes_count)
    TextView tvYesCount;
    @BindView(R.id.tv_no_count)
    TextView tvNoCount;
    @BindView(R.id.all_count)
    TextView allCount;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_lianxi)
    LinearLayout llLianxi;
    @BindView(R.id.ll_jiaojuan)
    LinearLayout llJiaojuan;
    @BindView(R.id.tv2_yes_count)
    TextView tv2YesCount;
    @BindView(R.id.tv2_no_count)
    TextView tv2NoCount;
    @BindView(R.id.all2_count)
    TextView all2Count;
    @BindView(R.id.ll_exam)
    LinearLayout llExam;
    @BindView(R.id.iv_content)
    ImageView iv_content;
    @BindView(R.id.iv_a)
    ImageView ivA;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.ll_a)
    LinearLayout llA;
    @BindView(R.id.iv_b)
    ImageView ivB;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.ll_b)
    LinearLayout llB;
    @BindView(R.id.iv_c)
    ImageView ivC;
    @BindView(R.id.tv_c)
    TextView tvC;
    @BindView(R.id.ll_c)
    LinearLayout llC;
    @BindView(R.id.iv_d)
    ImageView ivD;
    @BindView(R.id.tv_d)
    TextView tvD;
    @BindView(R.id.ll_d)
    LinearLayout llD;
    @BindView(R.id.iv_e)
    ImageView ivE;
    @BindView(R.id.tv_e)
    TextView tvE;
    @BindView(R.id.ll_e)
    LinearLayout llE;
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.tv_header)
    TextView tv_header;
    @BindView(R.id.iv_dati)
    ImageView iv_dati;
    @BindView(R.id.ll_answer)
    LinearLayout ll_answer;
    @BindView(R.id.tv_duoxuan_answer)
    TextView tv_duoxuan_answer;

    //多选是否选择答案
    private boolean isshow = false;
    private final int curren;
    NoScrollViewpager viewPager;
    private CountDownTimerUtil util;
    int type; //上个界面传过来的页面数值

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    util.restart();
                    break;
            }
        }
    };

    private String sum = "";
    private String exam;
    public String exampaperID;
    private List<TextQuestionsBean.DataBean.AataBean> mDatas = new ArrayList<>();
    private String answer; //正确答案
    int yescount = 0; //答对题数
    int nocount = 0; //答错题数
    private String kaoshifenshu; //考试分数
    TimerTextView tvTitle;
    private int count = 0;//当前答题数量
    private int yesjilu = 0;
    private int nojilu = 0;
    private int biaoshi;
    private String questiontype; //判断类型，0单选1多选2判断

    public ExamBanFragment(int type, String sum, String exam,
                           NoScrollViewpager vp_exters,
                           String exampaperID, String kaoshifenshu, TimerTextView tvTitle,
                           int currentcount,
                           int yesjilu,
                           int nojilu,
                           int biaoshi) {
        this.type = type;
        this.sum = sum;
        this.exam = exam;
        viewPager = vp_exters;
        this.exampaperID = exampaperID;
        this.kaoshifenshu = kaoshifenshu;
        this.tvTitle = tvTitle;
        this.curren = currentcount;
        this.yesjilu = yesjilu;
        this.nojilu = nojilu;
        this.biaoshi = biaoshi;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_examban;
    }

    int aa;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {

        if (type - 1 == curren) {

            tvYesCount.setText(yesjilu + "");
            tvNoCount.setText(nojilu + "");
        }

        util = new CountDownTimerUtil(getActivity(), this);


        //如果时间到
        if (tvTitle.showTime().equals("0:0:0")) {
            if ("2".equals(exam)) { //跳转到考试完成页面
                startActivity(new Intent(getActivity(),
                        ExamResultActivity.class)
                        .putExtra("kaoshifenshu", kaoshifenshu)
                        .putExtra("tvtime", tvTitle.getText().toString()));
                getActivity().finish();
            } else { //跳转到练习完成页面
                startActivity(new Intent(getActivity(), FiBaodianActivity.class));
                getActivity().finish();
            }
        }

        initListener();


    }

    private void initListener() {
        DaTiActivity.instance.setCallBackListener(new DaTiActivity.CallBackListener() {
            @Override
            public void cancleSelect(ImageView ivback) {

                if (exam.equals("1")) {
                    ToastUtils.showToast(getActivity(), "已为您保存当前的答题数!");
                    if (!AppUtil.getCount(getActivity()).equals("")) {
                        SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.COUNT);
                    }
                    SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.COUNT, type - 2 + "");

                    if (!AppUtil.getYesJilu(getActivity()).equals("")) {
                        SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.YESJILU);
                    }

                    if (!AppUtil.getNoJilu(getActivity()).equals("")) {
                        SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.NOJILU);
                    }

                    //保存对的题和错的题
                    if (!AppUtil.getYesCount(getActivity()).equals("")) {
                        SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESJILU,
                                yesjilu + Integer.parseInt(AppUtil.getYesCount(getActivity())) + ""
                        );
                    } else {
                        SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESJILU,
                                yesjilu + "");
                    }

                    if (!AppUtil.getNoCount(getActivity()).equals("")) {
                        SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOJILU,
                                nojilu + Integer.parseInt(AppUtil.getNoCount(getActivity())) + ""
                        );
                    } else {
                        SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOJILU,
                                nojilu + "");
                    }
                    SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.CLASSTYPE, 1 + "");
                }
                getActivity().finish();

            }
        });

    }

    private void loadcomment() {
        OkGo.<String>post(HttpManager.TestQuestions)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("ExampaperID", exampaperID)
                .params("Current", type) //当前题数
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TextQuestionsBean bean = gson.fromJson(response.body().toString(), TextQuestionsBean.class);

                        if (bean.getStatus() == 1) {

                            questiontype = bean.getData().getQuestionTypes();
                            mDatas.addAll(bean.getData().getAata());

                            //判断 IsPic是否是0，是文字，否则是图片 取pic；
                            if (bean.getData().getIsPic() == 0) {
                                iv_content.setVisibility(View.GONE);
                                rl_content.setVisibility(View.VISIBLE);
                                tvContent.setText("                " + bean.getData().getTitel());
                            } else { //是图片
                                iv_content.setVisibility(View.VISIBLE);
                                rl_content.setVisibility(View.GONE);
                                ImageLoader.getInstance().initGlide(getActivity()).loadImage(
                                        HttpManager.BASE_URL + bean.getData().getPIC(), iv_content);
                            }

                            //正确答案
                            answer = bean.getData().getAnswer();

                            if (bean.getData().getAata().size() == 0) {
                                ToastUtils.showToast(getActivity(), "暂无题目选项,请联系后台管理员!");
                            }

                            if (questiontype.equals("0")) { //单选
                                tv_header.setText("单选题");
                                iv_dati.setVisibility(View.GONE);
                                danxuan();
                            } else if (questiontype.equals("1")) { //1多选
                                tv_header.setText("多选题");
                                iv_dati.setVisibility(View.VISIBLE);
                                danxuan();
                            } else if (questiontype.equals("2")) { //判断题
                                tv_header.setText("判断题");
                                tvA.setText(mDatas.get(0).getTitle());
                                tvB.setText(mDatas.get(1).getTitle());
                                llC.setVisibility(View.GONE);
                                llD.setVisibility(View.GONE);
                                llE.setVisibility(View.GONE);
                                iv_dati.setVisibility(View.GONE);
                            }


                            if (!AppUtil.getYesCount(getActivity()).equals("")) {
                                if (exam.equals("1")) {
                                    if (biaoshi == 1) {
                                        tvYesCount.setText(yesjilu +
                                                Integer.parseInt(AppUtil.getYesCount(getActivity())) + "");
                                    } else {
                                        tvYesCount.setText(AppUtil.getYesCount(getActivity()));
                                    }
                                } else {
                                    tv2YesCount.setText(AppUtil.getYesCount(getActivity()));
                                }
                            } else { //如果答对的题为空0；

                                if (exam.equals("1")) {
                                    if (biaoshi == 1) {
                                        tvYesCount.setText(yesjilu + "");
                                    }
                                }

                            }

                            if (!AppUtil.getNoCount(getActivity()).equals("")) {
                                if (exam.equals("1")) {

                                    if (biaoshi == 1) {
                                        tvNoCount.setText(nojilu +
                                                Integer.parseInt(AppUtil.getNoCount(getActivity())) + "");
                                    } else {
                                        tvNoCount.setText(AppUtil.getNoCount(getActivity()));
                                    }

                                } else {
                                    tv2NoCount.setText(AppUtil.getNoCount(getActivity()));
                                }
                            } else {

                                if (exam.equals("1")) {
                                    if (biaoshi == 1) {
                                        tvNoCount.setText(nojilu + "");
                                    }
                                }

                            }

                            if ("1".equals(exam)) { //练习
                                llLianxi.setVisibility(View.VISIBLE);
                                llExam.setVisibility(View.GONE);
                                if (AppUtil.getCount(getActivity()).equals("")) {
                                    allCount.setText(type + "/" + sum);
                                } else {
                                    int c = Integer.parseInt(AppUtil.getCount(getActivity())) + 1;
                                    allCount.setText(c + "/" + sum);
                                }

                            } else { //考试
                                llLianxi.setVisibility(View.GONE);
                                llExam.setVisibility(View.VISIBLE);
                                all2Count.setText(type + "/" + sum);
                            }


                        } else {
                            ToastUtils.showToast(getActivity(), bean.getMsg());
                        }

                    }
                });

    }

    private void danxuan() {

        for (int i = 0; i < mDatas.size(); i++) {
            if (i == 0) {
                llA.setVisibility(View.VISIBLE);
                llB.setVisibility(View.GONE);
                llC.setVisibility(View.GONE);
                llD.setVisibility(View.GONE);
                llE.setVisibility(View.GONE);
                tvA.setText(mDatas.get(i).getTitle());
            } else if (i == 1) {
                llB.setVisibility(View.VISIBLE);
                llC.setVisibility(View.GONE);
                llD.setVisibility(View.GONE);
                llE.setVisibility(View.GONE);
                tvB.setText(mDatas.get(i).getTitle());
            } else if (i == 2) {
                llC.setVisibility(View.VISIBLE);
                llD.setVisibility(View.GONE);
                llE.setVisibility(View.GONE);
                tvC.setText(mDatas.get(i).getTitle());
            } else if (i == 3) {
                llD.setVisibility(View.VISIBLE);
                llE.setVisibility(View.GONE);
                tvD.setText(mDatas.get(i).getTitle());
            } else if (i == 4) {
//                ToastUtils.showToast(getActivity(), "暂无题目选项,请联系后台管理员!");
                llE.setVisibility(View.VISIBLE);
                tvE.setText(mDatas.get(i).getTitle());
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void lazyLoad() {
        loadcomment();
    }

    @Override
    public void countDownTimerListener(String time) {
    }

    @Override
    public void countDownTimerFinish() {
        viewPager.setCurrentItem(aa + 1);

        if (type == Integer.parseInt(sum)) {

            if ("1".equals(exam)) { //跳转到练习完成页面

                startActivity(new Intent(getActivity(), FiBaodianActivity.class));
                getActivity().finish();

            } else { //跳转到考试完成页面
                tvTitle.stop();
                tvTitle.showTime();

                startActivity(new Intent(getActivity(), ExamResultActivity.class)
                        .putExtra("kaoshifenshu", kaoshifenshu)
                        .putExtra("tvtime", tvTitle.getText().toString())
                );
                getActivity().finish();
            }

        }

    }

    @OnClick({R.id.ll_a, R.id.ll_b, R.id.ll_c, R.id.ll_d, R.id.ll_jiaojuan, R.id.ll_e, R.id.iv_dati})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_a:
                if (!("1".equals(questiontype))) {
                    if (AppUtil.isFastDoubleClick(3000)) {
                        return;
                    }
                    setbg(1);
                } else {
                    setduoxuanbg(1);
                }
                break;
            case R.id.ll_b:
                if (!("1".equals(questiontype))) {
                    if (AppUtil.isFastDoubleClick(3000)) {
                        return;
                    }
                    setbg(2);
                } else {
                    setduoxuanbg(2);
                }
                break;
            case R.id.ll_c:
                if (!("1".equals(questiontype))) {
                    if (AppUtil.isFastDoubleClick(3000)) {
                        return;
                    }
                    setbg(3);
                } else {
                    setduoxuanbg(3);
                }
                break;
            case R.id.ll_d:
                if (!("1".equals(questiontype))) {
                    if (AppUtil.isFastDoubleClick(3000)) {
                        return;
                    }
                    setbg(4);
                } else {
                    setduoxuanbg(4);
                }
                break;
            case R.id.ll_e:
                if (!("1".equals(questiontype))) {
                    if (AppUtil.isFastDoubleClick(3000)) {
                        return;
                    }
                    setbg(5);
                } else {
                    setduoxuanbg(5);
                }
                break;
            case R.id.ll_jiaojuan://交卷
                if ("1".equals(exam)) { //跳转到练习完成页面
                    startActivity(new Intent(getActivity(), FiBaodianActivity.class));
                    getActivity().finish();
                } else { //跳转到考试完成页面
                    startActivity(new Intent(getActivity(), ExamResultActivity.class)
                            .putExtra("kaoshifenshu", kaoshifenshu)
                            .putExtra("tvtime", tvTitle.showTime())
                    );
                    getActivity().finish();
                }
                break;
            case R.id.iv_dati: //提交答案
                moresure = moreA + moreB + moreC + moreD + moreE;
                if (moresure.equals("")) {
                    ToastUtils.showToast(getActivity(), "请至少选择一个答案!");
                } else {
                    commitmore();
                }
                break;

        }
    }

    String moresure = "";

    //多选的提交
    private void commitmore() {

        ivA.setImageResource(R.mipmap.exam_0);
        ivB.setImageResource(R.mipmap.exam_1);
        ivC.setImageResource(R.mipmap.exam_2);
        ivD.setImageResource(R.mipmap.exam_3);
        ivE.setImageResource(R.mipmap.exam_4);
        ll_answer.setVisibility(View.VISIBLE);
        tv_duoxuan_answer.setText(answer);


        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";

        String[] alls = new String[]{"A", "B", "C", "D", "E"};
        //正确答案
        String[] stringsanswer = answer.split("");
        //我填写的答案
        String[] stringsmore = moresure.split("");

        if (moresure.equals(answer)) {  //全答对
            ToastUtils.showToast(getActivity(), "正确!");

            for (int i = 0; i < stringsanswer.length; i++) {
                if (i == 0) {
                    duoxuanduide(stringsanswer[0]);
                } else if (i == 1) {
                    duoxuanduide(stringsanswer[1]);
                } else if (i == 2) {
                    duoxuanduide(stringsanswer[2]);
                } else if (i == 3) {
                    duoxuanduide(stringsanswer[3]);
                } else if (i == 4) {
                    duoxuanduide(stringsanswer[4]);
                }
            }

            YesCount();

        } else { // 全答错，或者对一个

            for (int i = 0; i < stringsanswer.length; i++) {
                if (i == 0) {
                    for (int j = 0; j < alls.length; j++) {
                        if (alls[j].equals(stringsanswer[0])) {
                            a = alls[j];
                        }
                    }
                } else if (i == 1) {
                    for (int j = 0; j < alls.length; j++) {
                        if (alls[j].equals(stringsanswer[1])) {
                            b = alls[j];
                        }
                    }
                } else if (i == 2) {
                    for (int j = 0; j < alls.length; j++) {
                        if (alls[j].equals(stringsanswer[2])) {
                            c = alls[j];
                        }
                    }
                } else if (i == 3) {
                    for (int j = 0; j < alls.length; j++) {
                        if (alls[j].equals(stringsanswer[3])) {
                            d = alls[j];
                        }
                    }
                } else if (i == 4) {
                    for (int j = 0; j < alls.length; j++) {
                        if (alls[j].equals(stringsanswer[4])) {
                            e = alls[j];
                        }
                    }
                }

            }

            for (int i = 0; i < stringsmore.length; i++) {
                if ("A".equals(stringsmore[i])) {
                    if (stringsmore[i].equals(a)) {
                        ivA.setImageResource(R.mipmap.yes);
                    } else if (!stringsmore[i].equals(a)) {
                        if (moreA.equals("")) {
                            ivA.setImageResource(R.mipmap.exam_a);
                        } else {
                            ivA.setImageResource(R.mipmap.no);
                        }
                    }
                } else if ("B".equals(stringsmore[i])) {
                    if (stringsmore[i].equals(b)) {
                        ivB.setImageResource(R.mipmap.yes);
                    } else if (!stringsmore[i].equals(b)) {
                        if (moreB.equals("")) {
                            ivB.setImageResource(R.mipmap.exam_b);
                        } else {
                            ivB.setImageResource(R.mipmap.no);
                        }
                    }
                } else if ("C".equals(stringsmore[i])) {
                    if (stringsmore[i].equals(c)) {
                        ivC.setImageResource(R.mipmap.yes);
                    } else if (!stringsmore[i].equals(c)) {
                        if (moreC.equals("")) {
                            ivC.setImageResource(R.mipmap.exam_c);
                        } else {
                            ivC.setImageResource(R.mipmap.no);
                        }
                    }
                } else if ("D".equals(stringsmore[i])) {
                    if (stringsmore[i].equals(d)) {
                        ivD.setImageResource(R.mipmap.yes);
                    } else if (!stringsmore[i].equals(d)) {
                        if (moreD.equals("")) {
                            ivD.setImageResource(R.mipmap.exam_d);
                        } else {
                            ivD.setImageResource(R.mipmap.no);
                        }
                    }
                } else if ("E".equals(stringsmore[i])) {
                    if (stringsmore[i].equals(e)) {
                        ivE.setImageResource(R.mipmap.yes);
                    } else if (!stringsmore[i].equals(e)) {
                        if (moreE.equals("")) {
                            ivE.setImageResource(R.mipmap.exam_e);
                        } else {
                            ivE.setImageResource(R.mipmap.no);
                        }
                    }
                }
            }

            for (int i = 0; i < stringsanswer.length; i++) {
                if (moreA.equals("")) {
                    if ("A".equals(stringsanswer[i])) {
                        ivA.setImageResource(R.mipmap.exam_a);
                    }
                }
                if (moreB.equals("")) {
                    if ("B".equals(stringsanswer[i])) {
                        ivB.setImageResource(R.mipmap.exam_b);
                    }
                }

                if (moreC.equals("")) {
                    if ("C".equals(stringsanswer[i])) {
                        ivC.setImageResource(R.mipmap.exam_c);
                    }
                }

                if (moreD.equals("")) {
                    if ("D".equals(stringsanswer[i])) {
                        ivD.setImageResource(R.mipmap.exam_d);
                    }
                }
                if (moreE.equals("")) {
                    if ("E".equals(stringsanswer[i])) {
                        ivE.setImageResource(R.mipmap.exam_e);
                    }
                }
            }
        }


        NOcount();

        llA.setClickable(false);
        llB.setClickable(false);
        llC.setClickable(false);
        llD.setClickable(false);
        llE.setClickable(false);

        aa = viewPager.getCurrentItem();

        Count();

        Message msgs = mHandler.obtainMessage();
        msgs.what = 1;
        msgs.sendToTarget();

    }

    private void duoxuanduide(String anObject) {
        if (!anObject.equals("")) {
            if (moreA.equals(anObject)) {
                ivA.setImageResource(R.mipmap.yes);
            } else if (moreB.equals(anObject)) {
                ivB.setImageResource(R.mipmap.yes);
            } else if (moreC.equals(anObject)) {
                ivC.setImageResource(R.mipmap.yes);
            } else if (moreD.equals(anObject)) {
                ivD.setImageResource(R.mipmap.yes);
            } else if (moreE.equals(anObject)) {
                ivE.setImageResource(R.mipmap.yes);
            }
        }
    }

    //多选的方法
    private void setduoxuanbg(int id) {
        switch (id) {
            case 1: // a
                setlectedduoxuan(1);
                break;
            case 2: // b
                setlectedduoxuan(2);
                break;
            case 3: // c
                setlectedduoxuan(3);
                break;
            case 4: // d
                setlectedduoxuan(4);
                break;
            case 5: // e
                setlectedduoxuan(5);
                break;
        }
    }

    String moreA = "";
    String moreB = "";
    String moreC = "";
    String moreD = "";
    String moreE = "";

    //多选
    private void setlectedduoxuan(int i) {
        isshow = !isshow;
        if (i == 1) {
            if (isshow) {
                ivA.setImageResource(R.mipmap.exam_a);
                moreA = "A";
            } else {
                ivA.setImageResource(R.mipmap.exam_0);
                moreA = "";
            }
        } else if (i == 2) {
            if (isshow) {
                ivB.setImageResource(R.mipmap.exam_b);
                moreB = "B";
            } else {
                ivB.setImageResource(R.mipmap.exam_1);
                moreB = "";
            }
        } else if (i == 3) {
            if (isshow) {
                ivC.setImageResource(R.mipmap.exam_c);
                moreC = "C";
            } else {
                ivC.setImageResource(R.mipmap.exam_2);
                moreC = "";
            }
        } else if (i == 4) {
            if (isshow) {
                ivD.setImageResource(R.mipmap.exam_d);
                moreD = "D";
            } else {
                ivD.setImageResource(R.mipmap.exam_3);
                moreD = "";
            }
        } else if (i == 5) {
            if (isshow) {
                ivE.setImageResource(R.mipmap.exam_e);
                moreE = "E";
            } else {
                ivE.setImageResource(R.mipmap.exam_4);
                moreE = "";
            }
        }


    }

    public void setbg(int id) {
        switch (id) {
            case 1: // a
                setlected(1);
                break;
            case 2: // b
                setlected(2);
                break;
            case 3: // c
                setlected(3);
                break;
            case 4: // d
                setlected(4);
                break;
            case 5: // e
                setlected(5);
                break;
        }
    }


    String A = "A";
    String B = "B";
    String C = "C";
    String D = "D";

    private void setlected(int i) {

        if (i == 1) {
            if (answer.equals(mDatas.get(i - 1).getOptionHead())) {
                ivA.setImageResource(R.mipmap.yes);
                ToastUtils.showToast(getActivity(), "正确!");

                YesCount();

            } else {
                ivA.setImageResource(R.mipmap.no);

                if (A.equals(answer)) {
                    ivA.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是A!");
                } else if (B.equals(answer)) {
                    ivB.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是B!");
                } else if (C.equals(answer)) {
                    ivC.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是C!");
                } else {
                    ivD.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是D!");
                }

                NOcount();
            }

            llB.setClickable(false);
            llC.setClickable(false);
            llD.setClickable(false);
        } else if (i == 2) {
            if (answer.equals(mDatas.get(i - 1).getOptionHead())) {
                ivB.setImageResource(R.mipmap.yes);
                ToastUtils.showToast(getActivity(), "正确!");

                YesCount();

            } else {
                ivB.setImageResource(R.mipmap.no);

                if (A.equals(answer)) {
                    ivA.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是A!");
                } else if (B.equals(answer)) {
                    ivB.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是B!");
                } else if (C.equals(answer)) {
                    ivC.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是C!");
                } else {
                    ivD.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是D!");
                }

                NOcount();

            }


            llA.setClickable(false);
            llC.setClickable(false);
            llD.setClickable(false);

        } else if (i == 3) {
            if (answer.equals(mDatas.get(i - 1).getOptionHead())) {
                ivC.setImageResource(R.mipmap.yes);
                ToastUtils.showToast(getActivity(), "正确!");

                YesCount();

            } else {
                ivC.setImageResource(R.mipmap.no);

                if (A.equals(answer)) {
                    ivA.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是A!");
                } else if (B.equals(answer)) {
                    ivB.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是B!");
                } else if (C.equals(answer)) {
                    ivC.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是C!");
                } else {
                    ivD.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是D!");
                }
                NOcount();
            }
            llA.setClickable(false);
            llB.setClickable(false);
            llD.setClickable(false);
        } else {
            if (answer.equals(mDatas.get(i - 1).getOptionHead())) {
                ivD.setImageResource(R.mipmap.yes);
                ToastUtils.showToast(getActivity(), "正确!");
                YesCount();
            } else {
                ivD.setImageResource(R.mipmap.no);
                if (A.equals(answer)) {
                    ivA.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是A!");
                } else if (B.equals(answer)) {
                    ivB.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是B!");
                } else if (C.equals(answer)) {
                    ivC.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是C!");
                } else {
                    ivD.setImageResource(R.mipmap.yes);
                    ToastUtils.showToast(getActivity(), "正确答案是D!");
                }
                NOcount();
            }
            llA.setClickable(false);
            llB.setClickable(false);
            llC.setClickable(false);
        }
        aa = viewPager.getCurrentItem();


        Count();

        Message msgs = mHandler.obtainMessage();
        msgs.what = 1;
        msgs.sendToTarget();


    }

    private void Count() {
        if (AppUtil.getCount(getActivity()).equals("")) {
            count = type;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.COUNT, count + "");
        } else {
            count = Integer.parseInt(AppUtil.getCount(getActivity())) + 1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.COUNT, count + "");
        }

        if (exam.equals("1")) {
            allCount.setText(AppUtil.getCount(getActivity()) + "/" + sum);
        }


    }

    private void YesCount() {
        if (AppUtil.getYesCount(getActivity()).equals("")) {
            yescount = yescount + 1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESCOUNT, yescount + "");
        } else {
            yescount = Integer.parseInt(AppUtil.getYesCount(getActivity())) + 1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESCOUNT, yescount + "");
        }

        if (exam.equals("1")) {
            if (biaoshi == 1) {
                tvYesCount.setText(yesjilu +
                        Integer.parseInt(AppUtil.getYesCount(getActivity())) + "");
            } else {
                tvYesCount.setText(AppUtil.getYesCount(getActivity()));

            }
        } else {
            tv2YesCount.setText(AppUtil.getYesCount(getActivity()));
        }
    }

    private void NOcount() {
        if (AppUtil.getNoCount(getActivity()).equals("")) {
            nocount = nocount + 1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOCOUNT, nocount + "");
        } else {
            nocount = Integer.parseInt(AppUtil.getNoCount(getActivity())) + 1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOCOUNT, nocount + "");
        }
        if (exam.equals("1")) {

            if (biaoshi == 1) {
                tvNoCount.setText(nojilu +
                        Integer.parseInt(AppUtil.getNoCount(getActivity())) + "");
            } else {
                tvNoCount.setText(AppUtil.getNoCount(getActivity()));
            }

        } else {
            tv2NoCount.setText(AppUtil.getNoCount(getActivity()));
        }


    }


    @Override
    public boolean onBackPressed() {

        if (exam.equals("1")) {
            ToastUtils.showToast(getActivity(), "已为您保存当前的答题数!");
            if (!AppUtil.getCount(getActivity()).equals("")) {
                SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.COUNT);
            }
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.COUNT, type - 1 + "");

            if (!AppUtil.getYesJilu(getActivity()).equals("")) {
                SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.YESJILU);
            }

            if (!AppUtil.getNoJilu(getActivity()).equals("")) {
                SharedPreferencesUtils.remove(getActivity(), SharedPreferencesUtils.NOJILU);
            }

            //保存对的题和错的题
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESJILU,
                    tvYesCount.getText().toString());
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOJILU,
                    tvNoCount.getText().toString());

        }
        getActivity().finish();

        return BackHandlerHelper.handleBackPress(this);
    }


}
