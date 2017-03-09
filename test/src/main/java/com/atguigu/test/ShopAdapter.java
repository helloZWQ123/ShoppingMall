package com.atguigu.test;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/3/2.
 */
public class ShopAdapter extends BaseAdapter {
    private final Context context;
    private final TextView tv_total;
    private List<GoodsBean.ResultBean.HotInfoBean> datas;

    public ShopAdapter(Context context, List<GoodsBean.ResultBean.HotInfoBean> list, TextView tv_total) {
        this.tv_total = tv_total;
        this.context = context;
        this.datas = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shop, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cb_check.setChecked(datas.get(position).isChecked());
        viewHolder.tvName.setText(datas.get(position).getName());
        viewHolder.tvPrice.setText(datas.get(position).getCover_price());
        Glide.with(context).load(Constants.BASE_URL_IMAGE+datas.get(position).getFigure()).into(viewHolder.ivGoods);
        viewHolder.addSubView.setListener(new AddSubView.OnNumberChangedListener() {
            @Override
            public void onNumberChanged(int value) {
                //设置数据
                datas.get(position).setNumber(value);
                showTotalPrice();
            }
        });


        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.cb_check.setChecked(!datas.get(position).isChecked());
//                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void showTotalPrice() {
        tv_total.setText(getTotalPrice()+"");
    }

    private Double getTotalPrice() {
        double price = 0;
        if(datas != null && datas.size()>0) {
            for (int i = 0; i < datas.size();i++) {
                GoodsBean.ResultBean.HotInfoBean hotInfoBean = datas.get(i);
                if(hotInfoBean.isChecked()) {
                    price = price + hotInfoBean.getNumber() * Double.parseDouble(hotInfoBean.getCover_price());
                }
            }
        }
        return price;
    }

    public void refresh(List<GoodsBean.ResultBean.HotInfoBean> hot_info, int position) {
        datas = hot_info;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.cb_check)
        CheckBox cb_check;
        @InjectView(R.id.iv_goods)
        ImageView ivGoods;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price)
        TextView tvPrice;
        @InjectView(R.id.addSubView)
        AddSubView addSubView;
        @InjectView(R.id.btn_buy)
        Button btnBuy;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.OnItemClick();
//                }
//            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "aaaaaaa");
                }
            });
        }
    }
//    public interface OnItemClickListener {
//        void OnItemClick(int position);
//    }
//    private OnItemClickListener listener;
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
}
