package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by LiXin on 2017/2/25.
 */

public class ActAdapter extends PagerAdapter {
    private final Context context;
    private final List<HomeBean.ResultBean.ActInfoBean> datas;

    public ActAdapter(Context context, List<HomeBean.ResultBean.ActInfoBean> act_info) {
        this.context = context;
        this.datas = act_info;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(Constant.BASE_URL_IMAGE + datas.get(position).getIcon_url()).crossFade().into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(position,imageView);
                }
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    public interface OnItemClickListener {
        void onItemClick(int position,  View v);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
