package com.atguigu.shoppingmall.type.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.adapter.ListLeftAdapter;
import com.atguigu.shoppingmall.type.adapter.TypeRightAdapter;
import com.atguigu.shoppingmall.type.bean.StyleBean;
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

public class ListFragment extends BaseFragment {
    @InjectView(R.id.lv_list)
    ListView lvList;
    @InjectView(R.id.rv_right)
    RecyclerView rvRight;
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品",
            "办公文具", "数码周边", "游戏专区"};
    private String[] urls = new String[]{Constant.SKIRT_URL, Constant.JACKET_URL, Constant.PANTS_URL, Constant.OVERCOAT_URL,
            Constant.ACCESSORY_URL, Constant.BAG_URL, Constant.DRESS_UP_URL, Constant.HOME_PRODUCTS_URL, Constant.STATIONERY_URL,
            Constant.DIGIT_URL, Constant.GAME_URL};
    private List<StyleBean.ResultBean> result;
    private TypeRightAdapter rightAdapter;
    private ListLeftAdapter listLeftAdapter;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_list, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        listLeftAdapter = new ListLeftAdapter(context, titles);
        lvList.setAdapter(listLeftAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listLeftAdapter.setChangeSelected(position);
                listLeftAdapter.notifyDataSetChanged();
                getDataFromNet(urls[position]);
            }
        });
        getDataFromNet(urls[0]);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                    processData(response);
            }
        });
    }

    private void processData(String response) {
        StyleBean styleBean = JSON.parseObject(response, StyleBean.class);
        result = styleBean.getResult();
        if (result != null && result.size() > 0) {
            rightAdapter = new TypeRightAdapter(context, result);
            rvRight.setAdapter(rightAdapter);
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });
            rvRight.setLayoutManager(manager);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
