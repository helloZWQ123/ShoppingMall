package com.atguigu.app2.community;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.app2.R;
import com.atguigu.app2.home.HomeBean;
import com.atguigu.app2.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/2/24.
 */
public class ChannelAdapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.ResultBean.ChannelInfoBean> channelInfos;

    public ChannelAdapter(Context context, List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
        this.context = context;
        channelInfos = channel_info;
    }

    @Override
    public int getCount() {

        return channelInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return channelInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.channel_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvChannel.setText(channelInfos.get(position).getChannel_name());
        Glide.with(context).load(Constants.BASE_URL_IMAGE+channelInfos.get(position).getImage()).crossFade().into(viewHolder.ivChannel);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_channel)
        ImageView ivChannel;
        @InjectView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
