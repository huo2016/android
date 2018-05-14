package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Toast;

import com.jay.test2.R;
import com.jay.test2.view.CommonEditText;

/**
 * 自定义EditText实现内容删除，查看密码等功能
 */
public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
                if (!TextUtils.isEmpty(s)) {
                    Toast.makeText(EditActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
