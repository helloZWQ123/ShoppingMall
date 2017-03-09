package com.xuchengpu.fragmentdemo.testeventbus;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 许成谱 on 2017/2/27 19:15.
 * qq:1550540124
 * for:
 */

public class MyFragment extends Fragment {
    private Context mContext;
    public TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.e("tag", "TestFragment **** onCreate...");
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.e("tag", "TestFragment **** onCreateView...");
        textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.LTGRAY);
        textView.setClickable(true);
        return textView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("tag", "TestFragment **** onActivityCreated...");
        super.onActivityCreated(savedInstanceState);
        textView.setText("我是Fragment中原本的内容");
        sendTextViewToActivity();
    }

    private void sendTextViewToActivity() {
        EventBus.getDefault().post(textView);
    }

}
