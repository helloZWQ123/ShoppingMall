package com.atguigu.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingmall.shoppingcart.view.AddSubView;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/2/28.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {
    private final Context context;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox cbAll;

    public ShoppingCartAdapter(Context context, List<GoodsBean> list, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.context = context;
        this.datas = list;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    public double getTotalPrice() {
        double total = 0;
        if(datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()) {
                    total = total + Double.parseDouble(goodsBean.getCover_price()) * Double.parseDouble(goodsBean.getNumber()+"");
                }
            }
        }
        return total;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(context, R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodsBean goodsBean = datas.get(position);
        holder.cbGov.setChecked(goodsBean.isChecked());
        Glide.with(context).load(Constant.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥"+goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinvalue(1);
        holder.addSubView.setMaxvalue(100);
        holder.addSubView.setListener(new AddSubView.OnNumberChangedListener() {
            @Override
            public void onNumberChanged(int value) {
                //设置数据
                goodsBean.setNumber(value);
                //在本地更新数据
                CartStorage.getInstance(context).upDateData(goodsBean);
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll() {
        if(datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(!goodsBean.isChecked()) {
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    number++;
                }
            }
            if(datas.size() == number) {
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void checkAll_none(boolean checked) {
        if(datas != null && datas.size() > 0) {
            for (int i = 0; i<datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setChecked(checked);
                checkboxAll.setChecked(checked);
                cbAll.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }
    }

    public void deleteData() {
        if(datas != null && datas.size()>0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isChecked()) {
                    datas.remove(goodsBean);
                    CartStorage.getInstance(context).deleteData(goodsBean);
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.cb_gov)
        CheckBox cbGov;
        @InjectView(R.id.iv_gov)
        ImageView ivGov;
        @InjectView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @InjectView(R.id.tv_price_gov)
        TextView tvPriceGov;
        @InjectView(R.id.addSubView)
        com.atguigu.shoppingmall.shoppingcart.view.AddSubView addSubView;

       public MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(v,getLayoutPosition());
                    }
                }
            });
        }
    }
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
