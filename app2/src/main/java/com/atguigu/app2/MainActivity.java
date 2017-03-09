package com.atguigu.app2;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.app2.base.BaseFragment;
import com.atguigu.app2.community.CommunityFragment;
import com.atguigu.app2.home.HomeFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.fl_main)
    FrameLayout flMain;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    private List<BaseFragment> fragments;
    private int position;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();
        initListener();
    }

    private void switchFragment(BaseFragment currentFragment) {
        if(tempFragment != currentFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(!currentFragment.isAdded()) {
                if(tempFragment!= null) {
                    ft.hide(tempFragment);
                    ft.add(R.id.fl_main,currentFragment);
                }
            } else {
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                    ft.show(currentFragment);
                }
            }
            ft.commit();
            tempFragment = currentFragment;
        }
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home :
                        position = 0;
                        break;
                    case R.id.rb_community :
                        position = 1;
                        break;

                }
                BaseFragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });
        rgMain.check(R.id.rb_home);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CommunityFragment());

    }
}
