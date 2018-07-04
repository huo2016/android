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
        findViewById(R.id.btn_animation).setOnClickListener(this);
        findViewById(R.id.btn_money).setOnClickListener(this);
        findViewById(R.id.btn_scanner).setOnClickListener(this);
        findViewById(R.id.btn_note).setOnClickListener(this);
        findViewById(R.id.btn_view).setOnClickListener(this);
        findViewById(R.id.btn_bitmap).setOnClickListener(this);
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
            case R.id.btn_animation:
                startActivity(new Intent(this,FastAnimationActivity.class));
                break;
            case R.id.btn_money:
                startActivity(new Intent(this,NumRunActivity.class));
                break;
            case R.id.btn_scanner:
                startActivity(new Intent(this,ScannerActivity.class));
                break;
            case R.id.btn_note:
                startActivity(new Intent(this,NoteActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(this,AidlActivity.class));
                break;
            case R.id.btn_bitmap:
                startActivity(new Intent(this,CheckViewActivity.class));
                break;
            default:
                break;
        }
    }


}
