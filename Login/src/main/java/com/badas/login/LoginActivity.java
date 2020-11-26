package com.badas.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Pair;

import com.badas.firebasemanager.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    static Class<?> from;
    MaterialButton btn_login;
    TextInputEditText tiet_email, tiet_password;
    FirebaseManager.Authentication authentication;
    Snackbar snackbar;

    public static void setFrom(Class<?> from) {
        LoginActivity.from = from;
    }

    @Override
    protected void onStart() {
        super.onStart();
        tiet_email.requestFocus();
        tiet_email.setError(null);
        tiet_password.setError(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        tiet_email = findViewById(R.id.tiet_email);
        tiet_password = findViewById(R.id.tiet_password);

        FirebaseManager.getInstance(this);
        authentication = new FirebaseManager.Authentication();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo do validation checks
                //tiet_email.setError("error"); //use this method to display input errors
                if(TextUtils.isEmpty(tiet_email.getText().toString())) {
                    tiet_email.setError("Please enter an email address");
                    return;
                }
                if(TextUtils.isEmpty(tiet_password.getText().toString())) {
                    tiet_email.setError("Please enter an password");
                    return;
                }

                authentication.EmailPassLogin(tiet_email.getText().toString(), tiet_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, from);
                                    ActivityOptionsCompat activityOptionsCompat =
                                            ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this, findViewById(R.id.appLogo), "appLogo");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent, activityOptionsCompat.toBundle());
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                @SuppressWarnings("unchecked") ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this,
                                Pair.create(findViewById(R.id.appLogo), "appLogo"),
                                Pair.create(findViewById(R.id.textInputLayout), "emailContainer"),
                                Pair.create(findViewById(R.id.textInputLayout2), "passwordContainer"),
                                Pair.create(findViewById(R.id.btn_login), "confirmButton"),
                                Pair.create(findViewById(R.id.btn_register), "navigateButton"),
                                Pair.create(findViewById(R.id.title), "title"),
                                Pair.create(findViewById(R.id.btn_resetPass), "extraButton"));
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        findViewById(R.id.btn_resetPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo check that email field is filled
//                tiet_email.setError("error"); //use this method to display input errors
                authentication.ResetPasswordEmail(tiet_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            snackbar = Snackbar.make(findViewById(R.id.btn_resetPass), "Email was sent", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Open Email", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (snackbar.isShown()) {
                                        snackbar.dismiss();
                                    }
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                    startActivity(intent);
                                    startActivity(Intent.createChooser(intent, "Pick you preferred mailing app"));
                                }
                            });
                            snackbar.show();
                            CountDownTimer countDownTimer = new CountDownTimer(5000, 1) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    snackbar.dismiss();
                                }
                            };
                            countDownTimer.start();
                        } else {
                            //todo display error
                        }
                    }
                });

            }
        });

        final boolean[] valid = new boolean[]{false, false};

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Patterns.EMAIL_ADDRESS.matcher(
                        Objects.requireNonNull(tiet_email.getText())).matches()) {
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_check_24, getTheme()));
                    valid[0] = true;
                } else {
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_alternate_email_24, getTheme()));
                    valid[0] = false;
                }

                if (tiet_password.getText().length() >= 6) {
                    ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_check_24, getTheme()));
                    valid[1] = true;
                } else {
                    ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_lock_open_24, getTheme()));
                    valid[1] = false;
                }

                btn_login.setEnabled(valid[0] && valid[1]);
                findViewById(R.id.btn_resetPass).setEnabled(valid[0]);
            }
        };

        tiet_email.addTextChangedListener(textWatcher);
        tiet_password.addTextChangedListener(textWatcher);
    }

}
