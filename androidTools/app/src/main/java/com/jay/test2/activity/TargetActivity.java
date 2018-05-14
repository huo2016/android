package com.jay.test2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jay.test2.R;
import com.jay.test2.activity.EditActivity;


public class TargetActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_expand).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                startActivity(new Intent(this, EditActivity.class));
                break;
            case R.id.btn_expand:
                startActivity(new Intent(this,ExpandActivity.class));
                break;
            default:
                break;
        }
    }


}