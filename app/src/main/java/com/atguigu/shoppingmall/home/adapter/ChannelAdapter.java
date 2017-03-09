package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/2/23.
 */
public class ChannelAdapter extends BaseAdapter {
    private final Context context;
    private final List<HomeBean.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context context, List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
        this.context = context;
        this.datas = channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_channel, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeBean.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        viewHolder.tvChannelItem.setText(channelInfoBean.getChannel_name());
        Glide.with(context).load(Constant.BASE_URL_IMAGE+channelInfoBean.getImage()).into(viewHolder.ivChannelItem);
        Log.e("TAG", "--------"+Constant.BASE_URL_IMAGE+channelInfoBean.getImage());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_channel_item)
        ImageView ivChannelItem;
        @InjectView(R.id.tv_channel_item)
        TextView tvChannelItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
