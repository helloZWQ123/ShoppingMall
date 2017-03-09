package com.atguigu.shoppingmall.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by LiXin on 2017/2/22.
 */

public class HomeFragment extends BaseFragment {
    @InjectView(R.id.tv_scan_home)
    TextView tv_scan_home;
    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    private HomeAdapter adapter;
    private HomeBean homeBean;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页面被初始化了");
        getDataFromNet();

    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constant.HOME_URL).build().execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", ""+response);
//                switch (id) {
//                    //get请求
//                    case 100:
                        if (response != null) {
                            processData(response);
                        }
//                        break;
//                    case 101:
//
//                        break;
//                }
            }
        });
    }

    private void processData(String response) {
        homeBean = JSON.parseObject(response, HomeBean.class);
        Log.e("TAG", "解析数据成功"+ homeBean.getResult().getHot_info().get(0).getName());
        //设置recyclerview的adapter
        adapter = new HomeAdapter(context, homeBean.getResult());
        rvHome.setAdapter(adapter);
//        rvHome.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position <= 3) {
                    ibTop.setVisibility(View.GONE);
                } else {
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
        rvHome.setLayoutManager(manager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_scan_home,R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_scan_home:
                Toast.makeText(context, "扫一扫", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent1, 5);
                break;
            case R.id.tv_search_home:
//                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();

                break;
            case R.id.tv_message_home:
//                Toast.makeText(context, "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                rvHome.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(context, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    List<HomeBean.ResultBean.RecommendInfoBean> recommend_info = homeBean.getResult().getRecommend_info();
                    for (int i = 0; i < recommend_info.size(); i++) {
                        GoodsBean goodsBean = new GoodsBean();
                        if(result.equals(recommend_info.get(i).getFigure())) {
                            goodsBean.setCover_price(recommend_info.get(i).getCover_price());
                            goodsBean.setName(recommend_info.get(i).getName());
                            goodsBean.setProduct_id(recommend_info.get(i).getProduct_id());
                            goodsBean.setFigure(recommend_info.get(i).getFigure());
                            Intent intent = new Intent(context, GoodsInfoActivity.class);
                            intent.putExtra("goodsbean",goodsBean);
                            startActivity(intent);
                        }

                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
