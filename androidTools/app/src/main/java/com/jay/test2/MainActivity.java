package com.jay.test2;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    Button button= (Button) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item,lv,false);
                    button.setText(""+position);
                    return button;
                }
                ((Button)convertView).setText(""+position);
                return convertView;
            }
        });

        File file=new File(Environment.getExternalStorageDirectory(),"aaa");
        file.mkdirs();
        if (file!=null&&file.exists()){
            for (File f:file.listFiles()){
                f.delete();
            }
        }
        lv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                lv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                start();
            }
        });

    }

    private void start(){
        final View view=findViewById(R.id.view);
        final ScrollableViewRECUtil scrollableViewRECUtil=new ScrollableViewRECUtil(view,ScrollableViewRECUtil.VERTICAL);
        scrollableViewRECUtil.start(new ScrollableViewRECUtil.OnRecFinishedListener() {
            @Override
            public void onRecFinish(Bitmap bitmap) {
                File f= Environment.getExternalStorageDirectory();
                System.out.print(f.getAbsoluteFile().toString());
                Toast.makeText(getApplicationContext(),f.getAbsolutePath(),Toast.LENGTH_LONG).show();
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG,60,new FileOutputStream(new File(f,"rec"+System.currentTimeMillis()+".jpg")));
                    Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        // scrollableViewRECUtil
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollableViewRECUtil.stop();
            }
        },90*1000);
    }
}
