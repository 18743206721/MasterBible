package com.xingguang.master.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xingguang.master.app.MyApplication;

import java.util.List;


/**
 * Created by wy on 16/10/31.
 * 通用的ViewHolder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> views;
    private View itemView;//行布局
    private static Context context;

    public CommonViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
    }
    /**
     * 通过View的id来获取指定的View
     * 如果该View没有赋值, 就限制性findViewById
     * 然后把它放到View的集合里面
     * 使用泛型来取消强转
     * @param id
     * @return 指定View
     */
    public <T extends View>T getView(int id){
        View view = views.get(id);
        if (view == null){
            //证明SparseArray里面没有这个View
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }
    //ListView使用方法
    public static CommonViewHolder getViewHolder(View itemView, ViewGroup parent, int itemId){
        CommonViewHolder commonViewHolder;
        if (itemView == null){
            Context context = parent.getContext();
            itemView = LayoutInflater.from(context).inflate(itemId, parent, false);
            commonViewHolder = new CommonViewHolder(itemView);
            itemView.setTag(commonViewHolder);
        }else {
            commonViewHolder = (CommonViewHolder) itemView.getTag();
        }
        return commonViewHolder;
    }
    //RecyclerView使用方法
    public static CommonViewHolder getViewHolder(ViewGroup parent, int itemId){
        context = parent.getContext();
        return getViewHolder(null, parent, itemId);
    }
    public View getItemView() {
        return itemView;
    }
    /************ViewHolder 设置数据的方法************/
    //设置文字
    public CommonViewHolder setText(int id, String text){
        TextView textView = getView(id);
        textView.setText(text);
        return this;
    }
    public CommonViewHolder setItemClick(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }
    public CommonViewHolder setUrlImage(int id, String url){
        ImageView imagesView = getView(id);
        Glide.with(MyApplication.app).load(url).into(imagesView);
        return this;
    }
    public CommonViewHolder setTextColor(int id, int color){
        TextView textView = getView(id);
        textView.setTextColor(color);
        return this;
    }
    public CommonViewHolder setTextBackColor(int id, int drawable){
        TextView textView = getView(id);
        textView.setBackgroundColor(drawable);
        return this;
    }
    public CommonViewHolder setImage(int id, int imgId){
        ImageView imageView = getView(id);
        imageView.setImageResource(imgId);
        return this;
    }

    public CommonViewHolder setUrlImage1(int id, String url){
        ImageView imagesView = getView(id);
//        ImageLoader.loadRoundImage(MyApplication.app,url,imagesView,5);
        return this;
    }
    //设置圆形
    public CommonViewHolder setCircleImg(int id, String url){
        ImageView imagesView = getView(id);
//        ImageLoader.loadCircleImage(MyApplication.app,url,imagesView);
        return this;
    }
    //设置默认圆形头像
    public CommonViewHolder setAvatarCircleImg(int id, int resId){
        ImageView imagesView = getView(id);
//        ImageLoader.loadCircleImage(MyApplication.app,resId,imagesView);
        return this;
    }
    public CommonViewHolder setViewClick(int id, View.OnClickListener listener){
        getView(id).setOnClickListener(listener);
        return this;
    }
    //给行布局设置点击事件

    public CommonViewHolder setLongItemClick(View.OnLongClickListener listener){
        itemView.setOnLongClickListener(listener);
        return this;
    }
    public CommonViewHolder setInAdapter(Context mcontext, List<String> imglist){
        return this;
    }
}
