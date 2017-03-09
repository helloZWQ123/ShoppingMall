package com.atguigu.shoppingmall.type.fragment;

import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.adapter.TagGridViewAdapter;
import com.atguigu.shoppingmall.type.bean.TagBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by LiXin on 2017/3/3.
 */

public class TagFragment extends BaseFragment {
    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultEntity> result;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constant.TAG_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if(response != null) {
                    processData(response);
                    adapter = new TagGridViewAdapter(context,result);
                    gvTag.setAdapter(adapter);
                }
            }
        });
    }

    private void processData(String response) {
        TagBean tagBean = JSON.parseObject(response, TagBean.class);
        result = tagBean.getResult();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
