package com.atguigu.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.lv_main)
    ListView lvMain;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.tv_delete)
    TextView tvDelete;
    @InjectView(R.id.cb_checkall)
    CheckBox cbAll;
    @InjectView(R.id.tv_buy)
    Button tvBuy;
    @InjectView(R.id.tv_total)
    TextView tvTotal;
    private ShopAdapter adapter;
    private List<GoodsBean.ResultBean.HotInfoBean> hot_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getDataFromNet();
        initListener();
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.HOME_URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                GoodsBean goodsBean = JSON.parseObject(response, GoodsBean.class);
                hot_info = goodsBean.getResult().getHot_info();
                initData();

            }
        });
    }

    private void initListener() {
//        adapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(int position) {
//                boolean checked = hot_info.get(position).isChecked();
//                hot_info.get(position).setChecked(!checked);
//                checkAll();
//            }
//        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean checked = hot_info.get(position).isChecked();
                hot_info.get(position).setChecked(!checked);
//                adapter.refresh(hot_info,position);
                adapter.notifyDataSetChanged();
                Log.e("TAG", "111111111111");
//                checkAll();
            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("TAG", "22222222222222222");
            }
        });
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = cbAll.isChecked();
                for (GoodsBean.ResultBean.HotInfoBean hotInfoBean : hot_info) {
                    hotInfoBean.setChecked(checked);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void checkAll() {
//        int number = 0;
//        for (GoodsBean.ResultBean.HotInfoBean hotInfoBean : hot_info) {
//            if(!hotInfoBean.isChecked()) {
//                cbAll.setChecked(false);
//                return;
//            } else {
//                number++;
//            }
//
//        }
//        if(number == hot_info.size()) {
//            cbAll.setChecked(true);
//        }


    }

    private void initData() {
        adapter = new ShopAdapter(this, hot_info,tvTotal);
        lvMain.setAdapter(adapter);
    }


}
