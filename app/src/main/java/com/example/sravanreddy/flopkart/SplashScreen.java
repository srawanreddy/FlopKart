package com.example.sravanreddy.flopkart;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {
Animation anim_blink, anim_blink2, anim_blink3;
private int circularImg[]={R.id.circular_img1, R.id.circular_img2, R.id.circular_img3};
private int loopcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loopcount=0;
        anim_blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_anim);
        anim_blink2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_anim2);
        anim_blink3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_anim3);
        anim_blink.setAnimationListener(this);
        anim_blink2.setAnimationListener(this);
        anim_blink3.setAnimationListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 6000);

        findViewById(circularImg[0]).startAnimation(anim_blink);
        //findViewById(circularImg[2]).startAnimation(anim_blink);

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==anim_blink)
        findViewById(circularImg[1]).startAnimation(anim_blink2);
        else if(animation==anim_blink2)
            findViewById(circularImg[2]).startAnimation(anim_blink3);
        else if( animation==anim_blink3)
            findViewById(circularImg[0]).startAnimation(anim_blink);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }
}
