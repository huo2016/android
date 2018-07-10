package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.view.CwView;
import com.jay.test2.view.ProgressView;

public class LineToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_to);

        ProgressView view = findViewById(R.id.line);
        CwView cw = findViewById(R.id.cw);
    }
}
