package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.view.Bezier;
import com.jay.test2.view.Bezier2;

public class BezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        Bezier bezier = findViewById(R.id.bezier);
        Bezier2 bezier2 = findViewById(R.id.bezier2);
    }
}
