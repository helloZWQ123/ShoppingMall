package com.atguigu.shoppingmall_1020.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall_1020.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/3/8.
 */
public class ListViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> name;

    public ListViewAdapter(Context context, List<String> name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.size();
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
            convertView = View.inflate(context, R.layout.item_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(name.get(position)+"");
        return convertView;
    }

    public void removeAll() {
        name.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
