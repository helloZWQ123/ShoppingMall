package com.atguigu.shoppingmall_1020.home.fragment;

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
import com.anye.greendao.gen.UserDao;
import com.atguigu.shoppingmall_1020.R;
import com.atguigu.shoppingmall_1020.app.GoodsInfoActivity;
import com.atguigu.shoppingmall_1020.app.MyApplication;
import com.atguigu.shoppingmall_1020.app.SearchActivity;
import com.atguigu.shoppingmall_1020.base.BaseFragment;
import com.atguigu.shoppingmall_1020.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall_1020.home.bean.GoodsBean;
import com.atguigu.shoppingmall_1020.home.bean.HomeBean;
import com.atguigu.shoppingmall_1020.utils.Constants;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


public class HomeFragment extends BaseFragment {
    @InjectView(R.id.tv_search_home)
    TextView tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    @InjectView(R.id.tv_scan)
    TextView tv_scan;
    private HomeAdapter adapter;
    private UserDao mUserDao;
    private HomeBean homeBean;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页的数据被初始化了...");
        getDataFromNet();
    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                //联网地址
                .url(Constants.HOME_URL)
                .id(100)//http,
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","联网失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","联网成功==");
                        processData(response);

                    }
                });
    }

    /**
     * 1.三种解析方式：fastjson解析数据和Gson和手动解析
     * 2.设置适配器
     * @param response
     */
    private void processData(String response) {
        //使用fastjson解析json数据
        HomeBean homeBean = JSON.parseObject(response,HomeBean.class);
        Log.e("TAG","解析数据成功=="+homeBean.getResult().getHot_info().get(0).getName());

        //设置RecyclerView的适配器
        adapter = new HomeAdapter(mContext,homeBean.getResult());
        rvHome.setAdapter(adapter);
        GridLayoutManager manager =new GridLayoutManager(mContext,1);
        //设置布局管理器
        rvHome.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position <=3){
                    ///按钮隐藏
                    ibTop.setVisibility(View.GONE);
                }else{
                    //按钮显示
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top, R.id.tv_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
//                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
               // Toast.makeText(mContext, "回到顶部", Toast.LENGTH_SHORT).show();
                rvHome.scrollToPosition(0);
                break;
            case R.id.tv_scan:
                Toast.makeText(mContext, "扫一扫", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent1, 5);
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
                    List<HomeBean.ResultEntity.RecommendInfoEntity> recommend_info = homeBean.getResult().getRecommend_info();
                    for (int i = 0; i < recommend_info.size(); i++) {
                        GoodsBean goodsBean = new GoodsBean();
                        if(result.equals(recommend_info.get(i).getFigure())) {
                            goodsBean.setCover_price(recommend_info.get(i).getCover_price());
                            goodsBean.setName(recommend_info.get(i).getName());
                            goodsBean.setProduct_id(recommend_info.get(i).getProduct_id());
                            goodsBean.setFigure(recommend_info.get(i).getFigure());
                            Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                            intent.putExtra("goodsbean",goodsBean);
                            startActivity(intent);
                        }

                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
