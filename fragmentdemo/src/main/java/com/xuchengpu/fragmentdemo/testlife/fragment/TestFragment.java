package com.xuchengpu.fragmentdemo.testlife.fragment;

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

import com.xuchengpu.fragmentdemo.testlife.view.DynamicActivity;

/**
 * Created by 许成谱 on 2017/2/26 9:50.
 * qq:1550540124
 * for:
 */

public class TestFragment extends Fragment  {
    private Context mContext;
    public TextView textView;

    @Override
    public void onAttach(Context context) {
        Log.e("tag", "TestFragment **** onAttach...");
        super.onAttach(context);
    }


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
        return textView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("tag", "TestFragment **** onActivityCreated...");
        super.onActivityCreated(savedInstanceState);
        textView.setText("我是一个静态加载过来的Fragment");
        Bundle bundle = getArguments();
        if(bundle!=null) {
            String content=bundle.getString("content");
            textView.setText(content);
        }
        DynamicActivity activity = (DynamicActivity) getActivity();
        activity.OnSetFragmentTextView(new DynamicActivity.SetFragmentTextView() {
            @Override
            public void setData(String info) {
                textView.setText(info);
            }
        });
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        Log.e("tag", "TestFragment **** onStart...");
        super.onStart();

    }

    @Override
    public void onResume() {
        Log.e("tag", "TestFragment **** onResume...");
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("tag", "TestFragment **** onPause...");
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("tag", "TestFragment **** onStop...");
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("tag", "TestFragment **** onDestroyView...");
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.e("tag", "TestFragment **** onDestroy...");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("tag", "TestFragment **** onDetach...");
        // TODO Auto-generated method stub
        super.onDetach();
    }


}
