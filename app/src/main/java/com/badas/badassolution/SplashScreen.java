package com.badas.badassolution;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;

import com.badas.firebasemanager.FirebaseManager;
import com.badas.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    static int overrideFirebase = 1; //will clear active firebase user - set to 0 to not override and 1 to override
    boolean skip_login = false; //use to skip the login screen
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //todo do logic here
        FirebaseManager.getInstance(this);
        FirebaseManager.Authentication authentication = new FirebaseManager.Authentication();
        if (overrideFirebase == 1) {
            authentication.SignOut();
            overrideFirebase++;
        }


        if (authentication.CheckForUser() == null && !skip_login)
            Start(LoginActivity.class);
        else
            Start(MainActivity.class);
    }

    @Override
    protected void onStop() {
        findViewById(R.id.motionLayout).clearAnimation();
        countDownTimer.cancel();
        super.onStop();
    }

    @Override
    protected void onPause() {
        findViewById(R.id.motionLayout).clearAnimation();
        countDownTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        findViewById(R.id.motionLayout).clearAnimation();
        countDownTimer.cancel();
        super.onDestroy();
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
                if (cls == LoginActivity.class)
                    LoginActivity.setFrom(SplashScreen.class);

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(SplashScreen.this, findViewById(R.id.appLogo), "appLogo");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent, activityOptionsCompat.toBundle());
                finish();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {
            }
        });
        countDownTimer = new CountDownTimer(getResources().getInteger(android.R.integer.config_longAnimTime) * 2, 1) {
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
