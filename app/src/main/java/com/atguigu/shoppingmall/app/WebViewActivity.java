package com.atguigu.shoppingmall.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.H5bean;
import com.atguigu.shoppingmall.home.bean.WebViewBean;
import com.atguigu.shoppingmall.utils.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_more)
    ImageButton ibMore;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;
    @InjectView(R.id.activity_web_view)
    LinearLayout activityWebView;
    private WebViewBean webviewbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.inject(this);
        getData();
    }

    private void getData() {
        webviewbean = (WebViewBean) getIntent().getSerializableExtra("webviewbean");
        tvTitle.setText(webviewbean.getName());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }
        });
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "cyc");
        webview.loadUrl(Constant.BASE_URL_IMAGE + webviewbean.getUrl());
    }

    @OnClick({R.id.ib_back, R.id.ib_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_more:
                Toast.makeText(WebViewActivity.this, "更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        public void jumpForAndroid(String data) {
            Log.e("TAG", data);
            if(!TextUtils.isEmpty(data)) {
                H5bean h5bean = JSON.parseObject(data,H5bean.class);
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setProduct_id(h5bean.getValue().getProduct_id()+"");
                goodsBean.setCover_price("10000");
                goodsBean.setFigure("/1478770583834.png");
                goodsBean.setName("Bilibili");
                Intent intent = new Intent(WebViewActivity.this,GoodsInfoActivity.class);
                intent.putExtra("goodsbean",goodsBean);
                startActivity(intent);
            }

        }
    }
}
