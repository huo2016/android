package com.jay.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Toast;


public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        CommonEditText edit = findViewById(R.id.edit);
        edit.addTextListener(new CommonEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)){
                    Toast.makeText(TargetActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
