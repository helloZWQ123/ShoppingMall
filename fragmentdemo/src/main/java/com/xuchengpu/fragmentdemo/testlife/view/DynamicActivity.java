package com.xuchengpu.fragmentdemo.testlife.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xuchengpu.fragmentdemo.R;
import com.xuchengpu.fragmentdemo.testlife.fragment.TestFragment;

public class DynamicActivity extends AppCompatActivity {
    Button btn_dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("tag", "DynamicActivity ************************* onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        btn_dynamic= (Button) findViewById(R.id.btn_dynamic);

        TestFragment testFragment=new TestFragment();

/*

        Bundle bundle=new Bundle();
        bundle.putString("content","我是一个动态加载过来的fragment");
        testFragment.setArguments(bundle);
*/

        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_dynamic,testFragment).commit();
        //动态加载
        getFragmentManager().beginTransaction().replace(R.id.fl_dynamic,testFragment).commit();

    }

    @Override
    protected void onStart() {
        Log.e("tag", "DynamicActivity ************************* onStart...");
        super.onStart();


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        Log.e("tag", "DynamicActivity ************************* onResume...");
        super.onResume();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        Log.e("tag", "DynamicActivity ************************* onStop...");
        super.onStop();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        Log.e("tag", "DynamicActivity ************************* onPause...");
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.e("tag", "DynamicActivity ************************* onDestroy...");
        super.onDestroy();

    }

    public interface SetFragmentTextView{
        void setData(String info);
    }

    private SetFragmentTextView setFragmentTextView;

    public  void OnSetFragmentTextView(SetFragmentTextView setFragmentTextView){
        this.setFragmentTextView=setFragmentTextView;
    };
    public void changeData(View view){
        setFragmentTextView.setData("我是activity中来的info数据");
    }


}
