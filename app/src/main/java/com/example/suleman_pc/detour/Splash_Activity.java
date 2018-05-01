package com.example.suleman_pc.detour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;


import java.util.Random;

public class Splash_Activity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;
    Thread splashTread;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(Splash_Activity.this,MainActivity.class);
//                startActivity(intent);
//
//            }
//        }, SPLASH_TIME_OUT); KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);

        imageView = (ImageView)findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int[] ids = new int[]{R.drawable.logo,R.drawable.logo, R.drawable.logo};
        Random randomGenerator = new Random();
        int r= randomGenerator.nextInt(ids.length);
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splash_Activity.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash_Activity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splash_Activity.this.finish();
                }

            }
        };
        splashTread.start();
    }
}


