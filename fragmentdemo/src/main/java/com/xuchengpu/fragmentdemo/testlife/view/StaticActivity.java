package com.xuchengpu.fragmentdemo.testlife.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xuchengpu.fragmentdemo.R;

public class StaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("tag", "StaticActivity ************************* onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       

    }

    @Override
    protected void onStart() {
        Log.e("tag", "StaticActivity ************************* onStart...");
        super.onStart();


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        Log.e("tag", "StaticActivity ************************* onResume...");
        super.onResume();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        Log.e("tag", "StaticActivity ************************* onStop...");
        super.onStop();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        Log.e("tag", "StaticActivity ************************* onPause...");
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.e("tag", "StaticActivity ************************* onDestroy...");
        super.onDestroy();

    }

}

