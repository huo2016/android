package com.jay.test2.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.jay.test2.R;

/**
 * 利用lottie-android使用json动画快速实现复杂的动画
 */
public class FastAnimationActivity extends AppCompatActivity {

    private LottieAnimationView lav_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_animation);

        lav_show = findViewById(R.id.lav_show);

        LottieComposition.Factory.fromAssetFileName(this, "LottieLogo1.json",
                new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(@Nullable LottieComposition composition) {
                        lav_show.setComposition(composition);
                    }
                });
    }
}
