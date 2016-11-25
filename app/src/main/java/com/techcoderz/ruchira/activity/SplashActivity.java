package com.techcoderz.ruchira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.classtune.MainActivity2;
import com.techcoderz.ruchira.utills.UserPreferences;

/**
 * Created by Shahriar on 6/30/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private final String TAG = "SplashActivity";

    private DotProgressBar dotProgressBar;
    private RelativeLayout relativeLayout;

    String checkToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
//        initialize();
//        action();


//        dotProgressBar.setStartColor(startColor);
//        dotProgressBar.setEndColor(endColor);
//        dotProgressBar.setDotAmount(amount);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent=new Intent(SplashActivity.this,MainActivity2.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}