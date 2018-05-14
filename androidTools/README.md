
# CommonEditText

项目地址：https://github.com/huo2016/android/tree/master/androidTools

CommonEditText目前包含自动删除内容和查看隐藏输入内容功能


# ExpandableTextView

项目地址：https://github.com/huo2016/android/tree/master/androidTools

ExpandableTextView 是一个可折叠的控件，支持折叠箭头自定义以及箭头位置

使用方式如下：

布局中引用

   <com.jay.test2.view.ExpandableTextView
        android:id="@+id/tv_expand"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:ellipsize="end"
        android:textColor="#666"
        app:animDuration="200"
        app:collapseDrawable="@mipmap/icon_orange_arrow_down"
        app:collapseExpandGrarity="right"
        app:collapseExpandTextColor="@color/main_color"
        app:contentTextSize="12sp"
        app:expandDrawable="@mipmap/icon_orange_arrow_up"
        app:maxCollapsedLines="4"
        app:textCollapse="@string/collapse"
        app:textExpand="@string/expand"/>
        
        代码中使用一行搞定
        
       
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