package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.bean.PieData;
import com.jay.test2.view.TestView;

import java.util.ArrayList;

public class AidlActivity extends AppCompatActivity {

    private TestView testView;
    private ArrayList<PieData> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        testView = findViewById(R.id.view);
        mData = new ArrayList<>();
        PieData data = new PieData("销售", 12);
        PieData data2 = new PieData("运维", 32);
        PieData data3 = new PieData("策划", 56);
        mData.add(data);
        mData.add(data2);
        mData.add(data3);

        testView.setStartAngle(12);
        testView.setData(mData);
    }
}
