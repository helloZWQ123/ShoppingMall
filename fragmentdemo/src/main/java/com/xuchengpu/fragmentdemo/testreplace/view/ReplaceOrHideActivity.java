package com.xuchengpu.fragmentdemo.testreplace.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.xuchengpu.fragmentdemo.R;
import com.xuchengpu.fragmentdemo.testreplace.fragment.Fragment1;
import com.xuchengpu.fragmentdemo.testreplace.fragment.Fragment2;
import com.xuchengpu.fragmentdemo.testreplace.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ReplaceOrHideActivity extends AppCompatActivity {

    @InjectView(R.id.fl_main)
    FrameLayout flMain;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    private int position;
    private List<Fragment> fragments;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_or_hide);
        ButterKnife.inject(this);
        initData();

        //设置监听
        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_home:
                        position = 0;
                        break;
                    case R.id.rb_main_type:
                        position = 1;
                        break;
                    case R.id.rb_main_community:
                        position = 2;
                        break;

                }
                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
//                getFragmentManager().beginTransaction().replace(R.id.fl_main,currentFragment).addToBackStack("name").commit();
            }

        });
        //注意放在设置监听之后
        rgMain.check(R.id.rb_main_home);

    }
    private void switchFragment(Fragment currentFragment) {

        //当前页面与缓存页面不同
        if (tempFragment != currentFragment) {
            //得到事务
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            //缓存页面不为空时
            if (tempFragment != null) {
                //当前页面没有被添加
                if (!currentFragment.isAdded()) {
                    transaction.add(R.id.fl_main, currentFragment);
                    transaction.hide(tempFragment);
                    //当前页面已经被添加
                } else {
                    transaction.hide(tempFragment);
                }
                // 缓存页面为空时
            } else {
                transaction.add(R.id.fl_main, currentFragment);
            }
            transaction.show(currentFragment);
            transaction .addToBackStack("name");
            transaction.commit();
            tempFragment = currentFragment;
        }
    }

    private void initData() {
        fragments = new ArrayList();
        fragments.add(0, new Fragment3());
        fragments.add(0, new Fragment2());
        fragments.add(0, new Fragment1());

    }
}
