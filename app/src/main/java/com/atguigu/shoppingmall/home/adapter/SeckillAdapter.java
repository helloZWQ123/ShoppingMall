package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/2/25.
 */
public class SeckillAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final HomeBean.ResultBean.SeckillInfoBean datas;

    public SeckillAdapter(Context context, HomeBean.ResultBean.SeckillInfoBean seckill_info) {
        this.context = context;
        datas = seckill_info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(context, View.inflate(context, R.layout.item_seckill, null));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.iv_figure)
        ImageView ivFigure;
        @InjectView(R.id.tv_cover_price)
        TextView tvCoverPrice;
        @InjectView(R.id.tv_origin_price)
        TextView tvOriginPrice;
        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onSeckillItemClickListener != null) {
                        onSeckillItemClickListener.onItemClick(getLayoutPosition(),v);
                    }
                }
            });
        }

        public void setData(HomeBean.ResultBean.SeckillInfoBean.ListBean listBean) {
            Glide.with(context).load(Constant.BASE_URL_IMAGE + listBean.getFigure()).into(ivFigure);
            tvCoverPrice.setText("￥"+listBean.getCover_price());
            tvOriginPrice.setText("￥"+listBean.getOrigin_price());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.setData(datas.getList().get(position));
    }

    @Override
    public int getItemCount() {
        return datas.getList().size();
    }
    public interface OnSeckillItemClickListener {
        void onItemClick(int position, View v);
    }
    private OnSeckillItemClickListener onSeckillItemClickListener;

    public void setOnSeckillItemClickListener(OnSeckillItemClickListener onSeckillItemClickListener) {
        this.onSeckillItemClickListener = onSeckillItemClickListener;
    }
}
