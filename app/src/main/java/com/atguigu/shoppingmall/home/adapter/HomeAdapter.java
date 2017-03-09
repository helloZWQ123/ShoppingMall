package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.app.WebViewActivity;
import com.atguigu.shoppingmall.home.activity.GoodsListActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.home.bean.WebViewBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by LiXin on 2017/2/22.
 */
public class HomeAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final HomeBean.ResultBean result;

    public static final int BANNER = 0;
    public static final int CHANNEL = 1;
    public static final int ACT = 2;
    public static final int SECKILL = 3;
    public static final int RECOMMEND = 4;
    public static final int HOT = 5;

    private int currentType = BANNER;
//    private final LayoutInflater inflater;

    public HomeAdapter(Context context, HomeBean.ResultBean result) {
        this.context = context;
        this.result = result;
//        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化布局
        if (viewType == BANNER) {
            return new BannerViewHolder(context, View.inflate(context, R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(context, View.inflate(context, R.layout.channel_viewpager, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(context, View.inflate(context, R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(context, View.inflate(context, R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(context, View.inflate(context, R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(context, View.inflate(context, R.layout.hot_item, null));
        }
        return null;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.tv_more_hot)
        TextView tvMoreHot;
        @InjectView(R.id.gv_hot)
        com.atguigu.shoppingmall.home.view.MyGridView gvHot;
        private HotAdapter adapter;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {
            adapter = new HotAdapter(context, hot_info);
            gvHot.setAdapter(adapter);
            gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(context, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    Intent intent = new Intent(context,GoodsInfoActivity.class);
                    intent.putExtra("goodsbean",goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.tv_more_recommend)
        TextView tvMoreRecommend;
        @InjectView(R.id.gv_recommend)
        GridView gvRecommend;
        private RecommendAdapter adapter;

        public RecommendViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
            adapter = new RecommendAdapter(context, recommend_info);
            gvRecommend.setAdapter(adapter);
            gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(context, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsbean", goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.countdownview)
        CountdownView countdownview;
        @InjectView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @InjectView(R.id.rv_seckill)
        RecyclerView rvSeckill;
        private SeckillAdapter adapter;
        private long dt;

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this, itemView);
        }

        public void setData(final HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            countdownview.start(dt);
            rvSeckill.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            adapter = new SeckillAdapter(context, seckill_info);
            rvSeckill.setAdapter(adapter);
            adapter.setOnSeckillItemClickListener(new SeckillAdapter.OnSeckillItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
//                    Toast.makeText(context, "position == " + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(listBean.getName());
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    Intent intent = new Intent(context,GoodsInfoActivity.class);
                    intent.putExtra("goodsbean",goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        @InjectView(R.id.act_viewpager)
        ViewPager actViewpager;
        private ActAdapter adapter;

        public ActViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this, itemView);
            actViewpager.setPageMargin(20);
            actViewpager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {
            adapter = new ActAdapter(context, act_info);
            actViewpager.setAdapter(adapter);
            adapter.setOnItemClickListener(new ActAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
//                    Toast.makeText(context, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setName(actInfoBean.getName());
                    webViewBean.setIcon_url(actInfoBean.getIcon_url());
                    webViewBean.setUrl(actInfoBean.getUrl());
                    Intent intent = new Intent(context,WebViewActivity.class);
                    intent.putExtra("webviewbean",webViewBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private GridView gv_channel;
        private ChannelAdapter channelAdapter;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            channelAdapter = new ChannelAdapter(context, channel_info);
            gv_channel.setAdapter(channelAdapter);
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(context, "" + channel_info.get(position).getChannel_name(), Toast.LENGTH_SHORT).show();
                    if(position < 9){
                        Intent intent = new Intent(context, GoodsListActivity.class);
                        intent.putExtra("position",position);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private Banner banner;

        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }


        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            //得到数据
            final List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(Constant.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }

            //将数据设置到banner上
            banner.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Log.e("TAG", "path==" + path);
                    Glide.with(context).load(path).into(imageView);
                }
            });
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setDelayTime(2000);
            banner.setBannerAnimation(BackgroundToForegroundTransformer.class);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
//                    Toast.makeText(context, "position == " + position, Toast.LENGTH_SHORT).show();
                    if (position < banner_info.size()) {
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        String image = "";
                        if (position == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (position == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                        } else if (position == 2) {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》 剑网3同人本";
                        }
                        image = banner_info.get(position).getImage();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(image);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);
                        Intent intent = new Intent(context, GoodsInfoActivity.class);
                        intent.putExtra("goodsbean", goodsBean);
                        context.startActivity(intent);
                    }
                }
            });
            banner.start();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder viewHolder = (BannerViewHolder) holder;
            viewHolder.setData(result.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder viewHolder = (ChannelViewHolder) holder;
            viewHolder.setData(result.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder viewHolder = (ActViewHolder) holder;
            viewHolder.setData(result.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder viewHolder = (SeckillViewHolder) holder;
            viewHolder.setData(result.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder viewHolder = (RecommendViewHolder) holder;
            viewHolder.setData(result.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder viewHolder = (HotViewHolder) holder;
            viewHolder.setData(result.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }
        return currentType;
    }


}
