package com.jay.test2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;

/**
 * 测试广播的基本使用
 * <p>
 * 注册广播(代码中注册为动态注册，Maifest中为静态注册)
 */

public class BroadCastActivity extends AppCompatActivity {


    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);

//        //动态注册
//        MyReceiver receiver = new MyReceiver();
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.test.broad");
//        registerReceiver(receiver, filter);
    }


}
