package com.xingguang.master.maincode.home.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.TextQuestionsBean;
import com.xingguang.master.maincode.home.view.activity.ExamResultActivity;
import com.xingguang.master.maincode.home.view.activity.FiBaodianActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;
import com.xingguang.master.view.NoScrollViewpager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * 创建日期：2018/5/26
 * 描述:考试答题
 * 作者:LiuYu
 */
@SuppressLint("ValidFragment")
public class ExamBanFragment extends BaseFragment implements CountDownTimerUtil.CountDownTimerListener {

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
    private final String kaoshifenshu; //考试分数
    CountdownView tvTitle;

    public ExamBanFragment(int type, String sum, String exam,
                           NoScrollViewpager vp_exters,
                           String exampaperID, String kaoshifenshu,CountdownView tvTitle) {
        this.type = type;
        this.sum = sum;
        this.exam = exam;
        viewPager = vp_exters;
        this.exampaperID = exampaperID;
        this.kaoshifenshu = kaoshifenshu;
        this.tvTitle = tvTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_examban;
    }

    int a;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        if ("1".equals(exam)) { //练习
            llLianxi.setVisibility(View.VISIBLE);
            llExam.setVisibility(View.GONE);
            allCount.setText(type + "/" + sum);
        } else { //考试
            llLianxi.setVisibility(View.GONE);
            llExam.setVisibility(View.VISIBLE);
            all2Count.setText(type + "/" + sum);
        }
        util = new CountDownTimerUtil(getActivity(), this);



//        Log.e("tvTitle_fragment", "initView: "+tvTitle.getMinute()+",,,"+tvTitle.getSecond() );

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

                        if (bean.getData() != null) {
                            mDatas.addAll(bean.getData().getAata());
                            //判断 IsPic是否是0，是文字，否则是图片 取pic；
                            if (bean.getData().getIsPic() == 0) {
                                iv_content.setVisibility(View.GONE);
                                tvContent.setVisibility(View.VISIBLE);

                                SpannableStringBuilder spannableString = new SpannableStringBuilder();
                                spannableString.append("单选题 " + bean.getData().getTitel());
                                //获取图片
                                Drawable drawable = getResources().getDrawable(R.mipmap.danxuan);
                                //设置图片的间距
                                drawable.setBounds(0, -20, drawable.getIntrinsicWidth() + 20, drawable.getIntrinsicHeight());
                                ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                                spannableString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                tvContent.setText(spannableString);
                                tvContent.setLetterSpacing(0.3f);//设置TextView的字间距，在低版本的手机上无效果

                            } else { //是图片
                                iv_content.setVisibility(View.VISIBLE);
                                tvContent.setVisibility(View.GONE);
                                ImageLoader.getInstance().initGlide(getActivity()).loadImage(
                                        HttpManager.BASE_URL + bean.getData().getPIC(), iv_content);
                            }

                            //正确答案
                            answer = bean.getData().getAnswer();

                            if (!AppUtil.getYesCount(getActivity()).equals("")){
                                if (exam.equals("1")){
                                    tvYesCount.setText(AppUtil.getYesCount(getActivity()));
                                }else {
                                    tv2YesCount.setText(AppUtil.getYesCount(getActivity()));
                                }
                            }
                            if (!AppUtil.getNoCount(getActivity()).equals("")) {
                                if (exam.equals("1")) {
                                    tvNoCount.setText(AppUtil.getNoCount(getActivity()));
                                }else{
                                    tv2NoCount.setText(AppUtil.getNoCount(getActivity()));
                                }
                            }

                            for (int i = 0; i < mDatas.size(); i++) {
                                if (i == 0) {
                                    llA.setVisibility(View.VISIBLE);
                                    llB.setVisibility(View.GONE);
                                    llC.setVisibility(View.GONE);
                                    llD.setVisibility(View.GONE);
                                    tvA.setText(mDatas.get(i).getTitle());
                                } else if (i == 1) {
                                    llA.setVisibility(View.VISIBLE);
                                    llB.setVisibility(View.VISIBLE);
                                    llC.setVisibility(View.GONE);
                                    llD.setVisibility(View.GONE);
                                    tvB.setText(mDatas.get(i).getTitle());
                                } else if (i == 2) {
                                    llA.setVisibility(View.VISIBLE);
                                    llB.setVisibility(View.VISIBLE);
                                    llC.setVisibility(View.VISIBLE);
                                    llD.setVisibility(View.GONE);
                                    tvC.setText(mDatas.get(i).getTitle());
                                } else if (i == 3) {
                                    llA.setVisibility(View.VISIBLE);
                                    llB.setVisibility(View.VISIBLE);
                                    llC.setVisibility(View.VISIBLE);
                                    llD.setVisibility(View.VISIBLE);
                                    tvD.setText(mDatas.get(i).getTitle());
                                } else {
                                    ToastUtils.showToast(getActivity(), "暂无题目选项,请联系后台管理员!");
                                    llA.setVisibility(View.GONE);
                                    llB.setVisibility(View.GONE);
                                    llC.setVisibility(View.GONE);
                                    llD.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            ToastUtils.showToast(getActivity(), bean.getMsg());
                        }

                    }
                });

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
        viewPager.setCurrentItem(a + 1);

        if (type == Integer.parseInt(sum)) {

            if ("1".equals(exam)) { //跳转到练习完成页面

                startActivity(new Intent(getActivity(), FiBaodianActivity.class));
                getActivity().finish();

            } else { //跳转到考试完成页面
                startActivity(new Intent(getActivity(), ExamResultActivity.class)
                .putExtra("kaoshifenshu",kaoshifenshu)
                );
                getActivity().finish();

            }

        }

    }

    @OnClick({R.id.ll_a, R.id.ll_b, R.id.ll_c, R.id.ll_d, R.id.ll_jiaojuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_a:
                setbg(1);
                break;
            case R.id.ll_b:
                setbg(2);
                break;
            case R.id.ll_c:
                setbg(3);
                break;
            case R.id.ll_d:
                setbg(4);
                break;
            case R.id.ll_jiaojuan://交卷
                if ("1".equals(exam)) { //跳转到练习完成页面
                    startActivity(new Intent(getActivity(), FiBaodianActivity.class));
                    getActivity().finish();
                } else { //跳转到考试完成页面
                    startActivity(new Intent(getActivity(), ExamResultActivity.class)
                    .putExtra("kaoshifenshu",kaoshifenshu)
                    );
                    getActivity().finish();
                }
                break;
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
            default:
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
        a = viewPager.getCurrentItem();
        Message msgs = mHandler.obtainMessage();
        msgs.what = 1;
        msgs.sendToTarget();


    }

    private void YesCount() {
        if (AppUtil.getYesCount(getActivity()).equals("")){
            yescount = yescount+1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESCOUNT,yescount+"" );
        }else{
            yescount = Integer.parseInt(AppUtil.getYesCount(getActivity()))+1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.YESCOUNT,yescount+"" );
        }

        if (exam.equals("1")){
            tvYesCount.setText(AppUtil.getYesCount(getActivity()));
        }else {
            tv2YesCount.setText(AppUtil.getYesCount(getActivity()));
        }

    }

    private void NOcount() {
        if (AppUtil.getNoCount(getActivity()).equals("")){
            nocount = nocount+1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOCOUNT,nocount+"" );
        }else{
            nocount = Integer.parseInt(AppUtil.getNoCount(getActivity()))+1;
            SharedPreferencesUtils.put(getActivity(), SharedPreferencesUtils.NOCOUNT,nocount+"" );
        }

        if (exam.equals("1")){
            tvNoCount.setText(AppUtil.getNoCount(getActivity()));
        }else {
            tv2NoCount.setText(AppUtil.getNoCount(getActivity()));
        }
    }


}
