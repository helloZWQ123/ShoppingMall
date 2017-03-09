package com.atguigu.shoppingmall.community;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by LiXin on 2017/2/22.
 */

public class CommunityFragment extends BaseFragment {
    @InjectView(R.id.ib_community_icon)
    ImageButton ibCommunityIcon;
    @InjectView(R.id.ib_community_message)
    ImageButton ibCommunityMessage;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    private CommunityViewPagerAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_community, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ib_community_icon, R.id.ib_community_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_community_icon:

                break;
            case R.id.ib_community_message:
                break;
        }
    }
}
