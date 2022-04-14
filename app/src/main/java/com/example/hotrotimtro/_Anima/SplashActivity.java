package com.example.hotrotimtro._Anima;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotrotimtro.R;
import com.example.hotrotimtro._Login.Login;

public class SplashActivity extends AppCompatActivity {

    ImageView imgvTop, imgvHeart, imgvBeat, imgvBottom;
    TextView txtView;

    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        anhXa();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animationTop = AnimationUtils.loadAnimation(this, R.anim.top_wave);
        imgvTop.setAnimation(animationTop);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imgvHeart, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();

        //Glide.with(this).load("https://i.giphy.com/lo9OHBZzaM7W8Lg5L0.gif").asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imgvBeat);
        Glide.with(this).load("https://i.giphy.com/lo9OHBZzaM7W8Lg5L0.gif").into(imgvBeat);

        Animation animationBot = AnimationUtils.loadAnimation(this, R.anim.bot_wave);
        imgvBottom.setAnimation(animationBot);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 2000);

    }

    private void anhXa() {
        imgvTop = findViewById(R.id.imgv_Top);
        imgvHeart = findViewById(R.id.imgv_Heart);
        imgvBeat = findViewById(R.id.imgv_Beat);
        imgvBottom = findViewById(R.id.imgv_Bottom);
        txtView = findViewById(R.id.txt_View);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            txtView.setText(charSequence.subSequence(0, index++));
            if (index <= charSequence.length()) {
                handler.postDelayed(runnable, delay);
            }
        }
    };

    public void animaText(CharSequence cs) {
        charSequence = cs;
        index = 0;
        txtView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }
}