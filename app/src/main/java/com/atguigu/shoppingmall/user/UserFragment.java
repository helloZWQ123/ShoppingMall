package com.atguigu.shoppingmall.user;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.LoginActivity;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.hankkin.gradationscroll.GradationScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：尚硅谷-杨光福 on 2017/2/22 11:36
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：用户Fragment
 */
public class UserFragment extends BaseFragment implements GradationScrollView.ScrollViewListener {
    @InjectView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.rl_header)
    RelativeLayout rlHeader;
    @InjectView(R.id.tv_all_order)
    TextView tvAllOrder;
    @InjectView(R.id.tv_user_pay)
    TextView tvUserPay;
    @InjectView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @InjectView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @InjectView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @InjectView(R.id.tv_user_location)
    TextView tvUserLocation;
    @InjectView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @InjectView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @InjectView(R.id.tv_user_score)
    TextView tvUserScore;
    @InjectView(R.id.tv_user_prize)
    TextView tvUserPrize;
    @InjectView(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @InjectView(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @InjectView(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @InjectView(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @InjectView(R.id.ll_root)
    LinearLayout llRoot;
    @InjectView(R.id.scrollview)
    GradationScrollView scrollview;
    @InjectView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @InjectView(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @InjectView(R.id.ib_user_message)
    ImageButton ibUserMessage;

    private int height;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "用户的数据被初始化了...");

        ViewTreeObserver vto = rlHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //移除视图树的监听
                rlHeader.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);

                height = rlHeader.getHeight();

                scrollview.setScrollViewListener(UserFragment.this);
            }
        });

        tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255, 0, 0));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255, 0, 0));

        } else if (y > 0 && y <= height) {
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvUsercenter.setTextColor(Color.argb((int) alpha, 255, 255, 255));
            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
        } else {
            //滑动到banner下面设置普通颜色
            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 255, 0, 0));
        }
    }


    @OnClick(R.id.tv_username)
    public void onClick() {

        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}
