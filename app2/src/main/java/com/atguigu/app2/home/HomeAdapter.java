package com.atguigu.app2.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.atguigu.app2.R;
import com.atguigu.app2.community.ChannelAdapter;
import com.atguigu.app2.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiXin on 2017/2/24.
 */
public class HomeAdapter extends RecyclerView.Adapter{
    //分类型的recyclerview
    private final HomeBean.ResultBean result;
    private Context context;


    public static final int BANNER = 0;
    public static final int CHANNEL = 1;
    private int currentType;
    private ChannelAdapter adapter;

    public HomeAdapter(Context context, HomeBean.ResultBean result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //初始化布局
        if(viewType == BANNER) {
            return new BannerViewHolder(context, View.inflate(context, R.layout.banner_viewpager,null));
        } else if(viewType == CHANNEL) {
            return new ChannelViewHolder(context,View.inflate(context,R.layout.channel_gridview,null));


        }
        return null;
    }
    class ChannelViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private GridView gridview;
        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            gridview = (GridView) itemView.findViewById(R.id.gridview);
        }

        public void setData(List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            adapter = new ChannelAdapter(context,channel_info);
            gridview.setAdapter(adapter);
        }
    }
    class BannerViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private Banner banner;
        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }


        public void setData(List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            ArrayList images = new ArrayList();
            for (int i = 0; i < banner_info.size();i++) {
                images.add(Constants.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImages(images).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            }).start();

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //绑定数据
        if(getItemViewType(position) == BANNER) {
            BannerViewHolder viewholder = (BannerViewHolder) holder;
            viewholder.setData(result.getBanner_info());
        } else if(getItemViewType(position) == CHANNEL) {
            ChannelViewHolder viewholder = (ChannelViewHolder) holder;
            viewholder.setData(result.getChannel_info());
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == BANNER) {
            currentType = BANNER;
        } else if(position == CHANNEL) {
            currentType = CHANNEL;
        }
        return currentType;
    }
}
