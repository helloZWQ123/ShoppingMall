package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.type.bean.StyleBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.atguigu.shoppingmall.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by LiXin on 2017/3/3.
 */
public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context context;
    private static final int HOT = 0;
    private static final int COMMON = 1;
    private final List<StyleBean.ResultBean.ChildBean> child;
    private final List<StyleBean.ResultBean.HotProductListBean> hot_product_list;
    private int currentType;

    public TypeRightAdapter(Context context, List<StyleBean.ResultBean> result) {
        this.context = context;
        //常用分类
        child = result.get(0).getChild();
        //热卖数据
        hot_product_list = result.get(0).getHot_product_list();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(context, View.inflate(context, R.layout.item_hot_right, null));
        } else if (viewType == COMMON) {
            return new CommonViewHolder(context, View.inflate(context, R.layout.item_common_right, null));
        }
        return null;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<StyleBean.ResultBean.HotProductListBean> hot_product_list) {
            for (int i = 0; i < hot_product_list.size(); i++) {
                final StyleBean.ResultBean.HotProductListBean hotProductListBean = hot_product_list.get(i);
                LinearLayout linearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 20));

                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setTag(i);
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 80), DensityUtil.dip2px(context, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(context, 10));
                //图片
                Glide.with(context).load(Constant.BASE_URL_IMAGE + hotProductListBean.getFigure()).into(imageView);
                linearLayout.addView(imageView, ivParams);
                //文字
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + hotProductListBean.getCover_price());
                linearLayout.addView(textView, tvParams);
                llHotRight.addView(linearLayout, params);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        String cover_price = hot_product_list.get(position).getCover_price();
                        String name = hot_product_list.get(position).getName();
                        String figure = hot_product_list.get(position).getFigure();
                        String product_id = hot_product_list.get(position).getProduct_id();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setFigure(figure);
                        goodsBean.setName(name);
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setCover_price(cover_price);
                        Intent intent = new Intent(context, GoodsInfoActivity.class);
                        intent.putExtra("goodsbean",goodsBean);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @InjectView(R.id.ll_root)
        LinearLayout llroot;
        @InjectView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        public CommonViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this,itemView);
        }

        public void setData(StyleBean.ResultBean.ChildBean childBean, int realPosition) {
            Glide.with(context).load(Constant.BASE_URL_IMAGE + childBean.getPic()).placeholder(R.drawable.new_img_loading_2).error(R.drawable.new_img_loading_2).into(ivOrdinaryRight);
            tvOrdinaryRight.setText(childBean.getName());
            llroot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(hot_product_list);
        } else if (getItemViewType(position) == COMMON) {
            CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
            int realPosition = position - 1;
            commonViewHolder.setData(child.get(realPosition), realPosition);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else if (position == COMMON) {
            currentType = COMMON;
        }
        return currentType;
    }
}
