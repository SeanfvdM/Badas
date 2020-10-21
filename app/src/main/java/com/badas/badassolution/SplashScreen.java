package com.badas.badassolution;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import com.badas.firebasemanager.FirebaseManager;

public class SplashScreen extends AppCompatActivity {
    boolean override_firebase = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToStart();
        if (override_firebase) {
            Start(MainActivity.class);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //todo do logic here
        FirebaseManager.getInstance(this);
        FirebaseManager.Authentication authentication = new FirebaseManager.Authentication();
        if (authentication.CheckForUser() == null)
            Start(MainActivity.class); //todo goto login
        else
            Start(MainActivity.class);
        return;
    }

    private void Start(final Class<?> cls) {
        ((MotionLayout) findViewById(R.id.motionLayout)).addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int value) {
                Intent intent = new Intent(SplashScreen.this, cls);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {
            }
        });
        final long waitDur = 100;
        CountDownTimer countDownTimer = new CountDownTimer(waitDur, 1) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
            }
        };
        countDownTimer.start();
    }
}