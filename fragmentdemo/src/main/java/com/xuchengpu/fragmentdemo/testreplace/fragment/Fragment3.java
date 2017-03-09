package com.xuchengpu.fragmentdemo.testreplace.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by 许成谱 on 2017/2/26 14:18.
 * qq:1550540124
 * for:
 */

public class Fragment3 extends Fragment {
    private Context mContext;
    private EditText editText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("tag", "Fragment3 **** onCreate...");
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("tag", "Fragment3 **** onCreateView...");
        editText=new EditText(mContext);
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.CENTER);
        editText.setTextSize(20);
        editText.setBackgroundColor(Color.LTGRAY);
        return editText;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("tag", "Fragment3 **** onActivityCreated...");
        super.onActivityCreated(savedInstanceState);
        editText.setText("我是Fragment----3");
    }
}
