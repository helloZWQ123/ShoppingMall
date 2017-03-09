package com.xuchengpu.fragmentdemo.testeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuchengpu.fragmentdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestEventBusActivity extends AppCompatActivity {

    @InjectView(R.id.btn_testeventbus)
    Button btnTesteventbus;
    @InjectView(R.id.fl_testeventbus)
    FrameLayout flTesteventbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event_bus);
        ButterKnife.inject(this);
        MyFragment myfragment=new MyFragment();

        getFragmentManager().beginTransaction().replace(R.id.fl_testeventbus,myfragment).commit();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setData(TextView textview) {
        if(textview==null) {
            Toast.makeText(TestEventBusActivity.this, "textview为空", Toast.LENGTH_SHORT).show();
            return;
        }
        textview.setText("我是主界面通过EventBus设置的数据");
        btnTesteventbus.setText("11111111111111111");
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTesteventbus.setText("我被Fragment通过EventBus改变了数据");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
