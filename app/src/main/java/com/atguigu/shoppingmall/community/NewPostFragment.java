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
public class NewPostFragment extends BaseFragment {
    @InjectView(R.id.lv_new_post)
    ListView lvNewPost;
    private NewPostListViewAdapter adapter;

    @Override
    public View initView() {
        View rootView = View.inflate(context, R.layout.fragment_news_post, null);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constant.NEW_POST_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","联网失败=="+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","新帖联网成功==");
                        processData(response);

                    }
                });
    }

    private void processData(String json) {

        NewPostBean bean = JSON.parseObject(json,NewPostBean.class);
        List<NewPostBean.ResultEntity> result = bean.getResult();
        if(result != null && result.size() >0){

            adapter = new NewPostListViewAdapter(context,result);
            lvNewPost.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
