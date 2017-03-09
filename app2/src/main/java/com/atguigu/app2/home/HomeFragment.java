package com.atguigu.app2.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.atguigu.app2.R;
import com.atguigu.app2.base.BaseFragment;
import com.atguigu.app2.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by LiXin on 2017/2/24.
 */
public class HomeFragment extends BaseFragment {
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
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
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.HOME_URL).id(100).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "联网成功");
                if(response != null) {
                    //fastjson解析数据
                    processData(response);
                }
            }
        });
    }

    private void processData(String response) {
        HomeBean homeBean = JSON.parseObject(response,HomeBean.class);
        Log.e("TAG", "解析数据成功"+homeBean.getResult().getBanner_info().get(0).getImage());
        adapter = new HomeAdapter(context,homeBean.getResult());
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
