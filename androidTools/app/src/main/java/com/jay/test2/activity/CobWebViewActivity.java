package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.bean.RadarData;
import com.jay.test2.view.CobWebView;
import com.jay.test2.view.RadarView;

import java.util.ArrayList;
import java.util.List;

public class CobWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cob_web_view);

        CobWebView web = findViewById(R.id.cob_view);
        List<RadarData> dataList = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            RadarData radarData = new RadarData("标题" + i, i * 12);
            dataList.add(radarData);
        }
        web.setData(dataList);
    }
}
