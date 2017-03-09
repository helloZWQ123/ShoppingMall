package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/3/3.
 */
public class ListLeftAdapter extends BaseAdapter {
    private final Context context;
    private String[] titles ;
    private int position;

    public ListLeftAdapter(Context context, String[] titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_left, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(titles[position]);
        if(position == this.position) {
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        } else {
            convertView.setBackgroundResource(R.drawable.bg2);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#323437"));
        }
        Log.e("TAG", ""+titles[position]);
        return convertView;
    }

    public void setChangeSelected(int position) {
        this.position = position;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
