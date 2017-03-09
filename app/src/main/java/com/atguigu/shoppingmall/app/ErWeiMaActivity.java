package com.atguigu.shoppingmall.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ErWeiMaActivity extends AppCompatActivity {

    @InjectView(R.id.iv_erweima)
    ImageView ivErweima;
    @InjectView(R.id.activity_er_wei_ma)
    RelativeLayout activityErWeiMa;
    private Bitmap mBitmap;
    private Bitmap bitmap;
    private FutureTarget<Bitmap> into;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_wei_ma);
        ButterKnife.inject(this);
        final GoodsBean goodsbean = (GoodsBean) getIntent().getSerializableExtra("goodsbean");
        into = Glide.with(this).load(Constant.BASE_URL_IMAGE + goodsbean.getFigure()).asBitmap().into(400, 400);
//        Log.e("TAG", "===" +Constant.BASE_URL_IMAGE + goodsbean.getFigure() );
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    try {
                        bitmap = into.get();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBitmap = CodeUtils.createImage(goodsbean.getProduct_id(), 400, 400, bitmap);
                                ivErweima.setImageBitmap(mBitmap);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
