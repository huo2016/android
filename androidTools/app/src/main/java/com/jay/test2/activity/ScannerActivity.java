package com.jay.test2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.jay.scanner.QRCodeView;
import com.jay.test2.R;
import google.zxing.Result;


public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {

    private QRCodeView qrCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        qrCodeView =findViewById(R.id.qr_code_view);
        qrCodeView.setOnQRCodeListener(new QRCodeView.OnQRCodeRecognitionListener() {
            @Override
            public void onQRCodeRecognition(Result result) {
                Intent intent = new Intent(ScannerActivity.this, ShowQRCodeContentActivity.class);
                intent.putExtra("qr_content", result.getText());
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                qrCodeView.startPreview();
                break;
            case R.id.btn_stop:
                qrCodeView.stopPreview();
                break;
            default:
                break;
        }
    }
}
