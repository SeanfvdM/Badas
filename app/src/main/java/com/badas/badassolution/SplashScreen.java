package com.badas.badassolution;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;

import com.badas.badasoptions.General;
import com.badas.badasoptions.Settings;
import com.badas.badassolution.ChildScreen.MainChildActivity;
import com.badas.firebasemanager.FirebaseManager;
import com.badas.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    CountDownTimer countDownTimer;

    public static boolean isGeneral = true, isChild = false, isGuardian = false, skipLogin = false;
    boolean overrideFirebase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new General(MainActivity2.class, MainChildActivity.class, LoginActivity.class);
        Settings.init(this, new Settings.WaitingEvent() {
            @Override
            public void loaded() {
                ((MotionLayout) findViewById(R.id.motionLayout)).transitionToStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (General.getInstance().isDemo())
            findViewById(R.id.appLogo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    load();
                }
            });
        else
            load();
    }

    private void load() {
        FirebaseManager.getInstance(this);
        final FirebaseManager.Authentication authentication = new FirebaseManager.Authentication();
        if (overrideFirebase) {
            authentication.SignOut();
            overrideFirebase = !overrideFirebase;
        }
        if (authentication.CheckForUser() == null && !skipLogin)
            Start(General.getInstance().getLoginActivity());
        else {
            //used to load certain settings
            if (isChild) {
                Start(General.getInstance().getChildActivity());
                GameState.init();
            } else if (isGeneral || isGuardian) {
                Start(General.getInstance().getUserActivity());
            } else {
                Start(General.getInstance().getChildActivity());
                GameState.init();
            }
        }
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
                else if (cls == MainChildActivity.class && (isChild && (isGeneral || isGuardian))) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                }

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
