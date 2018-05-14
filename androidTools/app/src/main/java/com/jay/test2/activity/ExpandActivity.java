package com.jay.test2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jay.test2.R;
import com.jay.test2.view.ExpandableTextView;

public class ExpandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        ExpandableTextView tv_expand = findViewById(R.id.tv_expand);
        tv_expand.setText("橡树的绿叶啊 白色的竹篱笆\n" +
                "好想告诉我的她 这里像幅画\n" +
                "去年的圣诞卡 镜子里的胡渣\n" +
                "画面开始没有她 我还在装傻\n" +
                "说好为我泡花茶 学习摆刀叉\n" +
                "学生宿舍空荡荡的家\n" +
                "守着电话 却等不到她\n" +
                "心里的雨倾盆的下 也沾不湿她的发\n" +
                "泪晕开明信片上的牵挂 那伤心原来没有时差\n" +
                "心里的雨倾盆的下 却始终淋不到她\n" +
                "寒风经过院子里的枝芽 也冷却了我手中的鲜花\n" +
                "橡树的绿叶啊 白色的竹篱笆\n" +
                "好想告诉我的她 这里像幅画\n" +
                "去年的圣诞卡 镜子里的胡渣\n" +
                "画面开始没有她 我还在装傻\n" +
                "说好为我泡花茶 学习摆刀叉\n" +
                "学生宿舍空荡荡的家\n" +
                "守着电话 却等不到她\n" +
                "心里的雨倾盆的下 也沾不湿她的发\n" +
                "泪晕开明信片上的牵挂 那伤心原来没有时差\n" +
                "心里的雨倾盆的下 却始终淋不到她\n" +
                "寒风经过院子里的枝芽 也冷却了我手中的鲜花\n" +
                "心里的雨倾盆的下 也沾不湿她的发\n" +
                "泪晕开明信片上的牵挂 那伤心原来没有时差\n" +
                "心里的雨倾盆的下 却始终淋不到她\n" +
                "寒风经过院子里的枝芽 也冷却了我手中的鲜花");
    }
}
