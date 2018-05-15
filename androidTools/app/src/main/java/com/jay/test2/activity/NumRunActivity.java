package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.view.RunningTextView;

public class NumRunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_run);

        RunningTextView tv_money=findViewById(R.id.tv_money);
        RunningTextView tv_num=findViewById(R.id.tv_num);
        tv_money.setContent("1234.56");
        tv_num.setContent("4567");
    }
}
