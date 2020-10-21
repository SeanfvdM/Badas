package com.badas.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnSignInWithGoogle;
    EditText edtPassword, edtEmail;
    TextView btnRegister;
    ProgressBar progressbarLogin;
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //  mAuth = FirebaseAuth.getInstance();

        btnRegister = findViewById(R.id.btnRegister);
        btnSignInWithGoogle = findViewById(R.id.btnSignInWithGoogle);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        progressbarLogin = findViewById(R.id.progressBarLogin);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(/*token*/))
//                .requestEmail()
//                .build();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    edtPassword.setError("Password must be longer than 6 characters.");
                    return;
                }

                progressbarLogin.setVisibility(View.VISIBLE);

                //authenticate user

//                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                            finish();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressbarLogin.setVisibility(View.GONE);
//                        }
//                    }
//                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    startActivity(new Intent(getApplicationContext(), Register.class));
                //todo to go to the registration page
                finish();
            }
        });


        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                private void firebaseAuthWithGoogle(String idToken) {
//                    AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//                    mAuth.signInWithCredential(credential)
//                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "signInWithCredential:success");
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        updateUI(user);
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                        Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                                        updateUI(null);
//                                    }
//
//                                    // ...
//                                }
//                            });
//                }
            }
        });


    }

}
