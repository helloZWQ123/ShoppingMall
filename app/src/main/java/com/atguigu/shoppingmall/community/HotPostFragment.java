package com.atguigu.shoppingmall.community;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by LiXin on 2017/3/5.
 */
public class HotPostFragment extends BaseFragment {
    @InjectView(R.id.lv_hot_post)
    ListView lvHotPost;
    private HotPostListViewAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_hot_post, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();

    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constant.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        processData(response);

                    }
                });
    }

    private void processData(String json) {

        HotPostBean bean = JSON.parseObject(json, HotPostBean.class);
        List<HotPostBean.ResultEntity> result = bean.getResult();
        if (result != null && result.size() > 0) {

            adapter = new HotPostListViewAdapter(context, result);
            lvHotPost.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
