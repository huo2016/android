package com.jay.test2.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jay.test2.R;
import com.jay.test2.view.CheckView;

public class CheckViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_view);
        final CheckView check = findViewById(R.id.check);
        check.setBackgroundColor(0xffFF5335);
        check.setAnimDuration(3000);
        check.check();
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                check.unCheck();
//            }
//        });



    }
}
