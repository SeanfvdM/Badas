package com.badas.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;

import com.badas.firebasemanager.FirebaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private static Class<?> from;
    MaterialButton btn_login, btn_addChild;
    TextInputEditText tiet_email, tiet_password, tiet_rePassword;
    FirebaseManager.Authentication authentication;

    public static void setFrom(Class<?> from) {
        RegisterActivity.from = from;
    }

    @Override
    protected void onStart() {
        super.onStart();
        tiet_email.requestFocus();
        tiet_email.setError(null);
        tiet_password.setError(null);
        tiet_rePassword.setError(null);
        AutofillManager afm = this.getSystemService(AutofillManager.class);
        if (afm != null) {
            afm.requestAutofill(tiet_email);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = findViewById(R.id.btn_login);
        btn_addChild = findViewById(R.id.btn_addChild);
        tiet_email = findViewById(R.id.tiet_email);
        tiet_password = findViewById(R.id.tiet_password);
        tiet_rePassword = findViewById(R.id.tiet_rePassword);

        authentication = new FirebaseManager.Authentication();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo do validation checks
                if(TextUtils.isEmpty(tiet_email.getText().toString())) {
                    tiet_email.setError("Please enter an email address");
                    return;
                }
                if(TextUtils.isEmpty(tiet_password.getText().toString())) {
                    tiet_password.setError("Please enter an password");
                    return;
                }
                if(tiet_password != tiet_rePassword) {
                    tiet_rePassword.setError("Please make sure the passwords match");
                    return;
                }
                //tiet_email.setError("error"); //use this method to display input errors
                authentication.EmailPassRegister(tiet_email.getText().toString(), tiet_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, from);
                                    ActivityOptionsCompat activityOptionsCompat =
                                            ActivityOptionsCompat.makeSceneTransitionAnimation(RegisterActivity.this, findViewById(R.id.appLogo), "appLogo");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent, activityOptionsCompat.toBundle());
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.btn_navigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });

        findViewById(R.id.btn_addChild).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Not implemented", Toast.LENGTH_SHORT).show();
            }
        });

        final boolean[] valid = new boolean[]{false, false, false};

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
                    //does an ui update for the email field - no animation
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_check_24, getTheme()));
                    valid[0] = true;
                } else {
                    //does an ui update for the email field - no animation
                    ((TextInputLayout) findViewById(R.id.textInputLayout)).setStartIconDrawable(
                            ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_alternate_email_24, getTheme()));
                    valid[0] = false;
                }

                //todo optionally reset error messages for passwords
                tiet_password.setError(null);
                tiet_rePassword.setError(null);
                //checks if the passwords are at the desired length
                if (Objects.requireNonNull(tiet_password.getText()).length() >= 6
                        && Objects.requireNonNull(tiet_rePassword.getText()).length() >= 6) {
                    //checks if the passwords match
                    if (TextUtils.equals(
                            Objects.requireNonNull(tiet_password.getText()).toString(),
                            Objects.requireNonNull(tiet_rePassword.getText()).toString())) {
                        //makes a ui update to show that the passwords are valid - no animation

                        ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_check_24, getTheme()));
                        ((TextInputLayout) findViewById(R.id.textInputLayout3)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_check_24, getTheme()));
                        valid[1] = true;
                        valid[2] = true;
                    } else {
                        //todo display error message of mismatching passwords
                        //makes a ui update to show that the passwords are invalid - no animation
                        tiet_password.setError("Password is invalid.");
                        ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_close, getTheme()));
                        ((TextInputLayout) findViewById(R.id.textInputLayout3)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_close, getTheme()));
                        valid[1] = false;
                        valid[2] = false;
                    }
                } else {
                    //checks if the password fields length is at the desired length
                    if (Objects.requireNonNull(tiet_password.getText()).length() >= 6) {
                        //resets the ui of the other password field if it's length is less
                        // than the desired length
                        ((TextInputLayout) findViewById(R.id.textInputLayout3)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_lock_open_24, getTheme()));
                        tiet_password.setError("Password must be longer than 6 characters");
                        ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_lock_24_white, getTheme()));
                        valid[1] = true;
                        valid[2] = false;
                    }

                    //checks if the password fields length is at the desired length
                    if (Objects.requireNonNull(tiet_rePassword.getText()).length() >= 6) {
                        //resets the ui of the other password field if it's length is less
                        tiet_rePassword.setError("Password must be longer than 6 characters");
                        // than the desired length
                        ((TextInputLayout) findViewById(R.id.textInputLayout2)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_lock_open_24, getTheme()));
                        ((TextInputLayout) findViewById(R.id.textInputLayout3)).setStartIconDrawable(
                                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_outline_lock_24_white, getTheme()));
                        valid[1] = false;
                        valid[2] = true;
                    }
                }

                btn_login.setEnabled(valid[0] && valid[1] && valid[2]);
            }
        };

        tiet_email.addTextChangedListener(textWatcher);
        tiet_password.addTextChangedListener(textWatcher);
        tiet_rePassword.addTextChangedListener(textWatcher);


        ((MaterialButtonToggleGroup) findViewById(R.id.tb_userType)).addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                //add event that happens when user type is selected
                if (checkedId == R.id.btn_parent && isChecked) {
                    btn_addChild.setEnabled(true);
                } else //generic user - can be child
                    if (checkedId == R.id.btn_gaurdian && isChecked) {
                        btn_addChild.setEnabled(true);
                    } else btn_addChild.setEnabled(checkedId == R.id.btn_teacher && isChecked);
            }
        });
    }

}
