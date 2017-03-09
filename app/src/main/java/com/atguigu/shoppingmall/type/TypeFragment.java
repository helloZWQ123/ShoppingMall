package com.atguigu.shoppingmall.type;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.fragment.ListFragment;
import com.atguigu.shoppingmall.type.fragment.TagFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiXin on 2017/2/22.
 */

public class TypeFragment extends BaseFragment {
    String[] titles = {"分类", "标签"};
    @InjectView(R.id.tl_1)
    SegmentTabLayout tl1;
    @InjectView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @InjectView(R.id.fl_type)
    FrameLayout flType;
    private ArrayList<BaseFragment> fragments;
    private BaseFragment tempFragment;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_type, null);
        ButterKnife.inject(this, view);
        initFragment();
        return view;
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new TagFragment());
        //默认显示fragment
        switchFragment(fragments.get(0));
    }

    private void switchFragment(BaseFragment fragment) {
        if(tempFragment != fragment) {
            if(fragment != null) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if(!fragment.isAdded()) {
                    if(tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.add(R.id.fl_type,fragment);
                } else {
                    if(tempFragment!=null) {
                        ft.hide(tempFragment);
                        ft.show(fragment);
                    }
                }
                tempFragment = fragment;
                ft.commit();
            }

        }
    }

    @Override
    public void initData() {
        super.initData();
        tl1.setTabData(titles);
        //设置监听
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



    @OnClick(R.id.iv_type_search)
    public void onClick() {
        Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
    }

}
