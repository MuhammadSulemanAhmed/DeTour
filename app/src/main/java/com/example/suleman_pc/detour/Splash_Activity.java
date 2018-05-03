package com.example.suleman_pc.detour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.Random;

public class Splash_Activity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;
    Thread splashTread;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_);
        progressBar=findViewById(R.id.progressbar);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    Intent intent = new Intent(Splash_Activity.this,
                            LoginActivity.class);
//                    progressBar.setVisibility(View.GONE);
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


