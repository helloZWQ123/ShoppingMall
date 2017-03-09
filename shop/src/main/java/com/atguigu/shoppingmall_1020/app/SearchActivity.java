package com.atguigu.shoppingmall_1020.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.UserDao;
import com.atguigu.shoppingmall_1020.R;
import com.atguigu.shoppingmall_1020.User;
import com.atguigu.shoppingmall_1020.home.activity.GoodsListActivity;
import com.atguigu.shoppingmall_1020.home.adapter.ListViewAdapter;
import com.atguigu.shoppingmall_1020.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.tv_search_home)
    EditText tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.ll_titlebar)
    LinearLayout llTitlebar;
    @InjectView(R.id.rv_home)
    ListView rvHome;
    @InjectView(R.id.iv_search_voice)
    ImageView ivVoice;
    @InjectView(R.id.btn_clear)
    Button btn_clear;
    private UserDao mUserDao;
    private User mUser;
    private ListViewAdapter adapter;
    private List<String> datas;
    private String name;
    private List<User> users;

    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        datas = new ArrayList<>();
        initListener();

    }

    private void getData() {
        if(mUserDao!=null) {
            users = mUserDao.loadAll();
            for (int i = 0; i < users.size(); i++) {
                name = users.get(i).getName();
                datas.add(name);
            }
            adapter = new ListViewAdapter(this,datas);
            rvHome.setAdapter(adapter);
            if(datas!=null&&datas.size()>0) {
                btn_clear.setVisibility(View.VISIBLE);
                btn_clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserDao.deleteAll();
                        adapter.removeAll();
                        btn_clear.setVisibility(View.GONE);
                    }
                });
            } else {
                btn_clear.setVisibility(View.GONE);
            }
        }

    }

    private void initListener() {
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tvSearchHome.getText())) {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }else {
                    mUser = new User(null,tvSearchHome.getText()+"");
                    mUserDao.insert(mUser);//添加一个
                    getData();
                }
                Intent intent = new Intent(SearchActivity.this, GoodsListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ivVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceToText();
            }
        });
    }

    private void voiceToText() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//中文
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4.显示dialog，接收语音输入
        mDialog.show();
    }
    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Toast.makeText(SearchActivity.this, "初始化失败了", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * 返回的结果
         *
         * @param recognizerResult
         * @param b                是否说话结束
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            printResult(recognizerResult);
            Log.e("TAG", "result==" + result);
//           Toast.makeText(MainActivity.this, "result=="+result, Toast.LENGTH_SHORT).show();

        }

        /**
         * 出错的回调
         *
         * @param speechError
         */
        @Override
        public void onError(SpeechError speechError) {

        }
    }
    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        String reuslt = resultBuffer.toString().replace("。", "");

        tvSearchHome.setText(reuslt);
        tvSearchHome.setSelection(tvSearchHome.length());
    }

}
