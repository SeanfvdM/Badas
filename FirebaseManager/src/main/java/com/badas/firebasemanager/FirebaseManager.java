package com.badas.firebasemanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 21,October,2020
 */
public class FirebaseManager {
    private static FirebaseManager instance = null;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firebaseFirestore;
    FirebaseAnalytics firebaseAnalytics;

    private FirebaseManager(Context context) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseManager getInstance(Context context) {
        return instance == null ? instance = new FirebaseManager(context) : instance;
    }

    public interface PhoneVerificationListener {
        void VerificationComplete(PhoneAuthCredential phoneAuthCredential);

        void VerificationFailed(FirebaseException e);

        void CompleteRegister(Task<AuthResult> task);

        void CompleteLink(Task<AuthResult> task);
    }

    static void checkInstance() {
        if (instance == null)
            throw new NullPointerException("FirebaseManager needs to be initialised");
    }

    public static class Authentication {
        public Authentication() {
            checkInstance();
        }

        public FirebaseUser CheckForUser() {
            return instance.firebaseAuth.getCurrentUser();
        }

        public Task<AuthResult> EmailPassLogin(String email, String pass) {
            checkInstance();
            return instance.firebaseAuth.signInWithEmailAndPassword(email, pass);
        }

        public Task<AuthResult> EmailPassRegister(String email, String pass) {
            checkInstance();
            return instance.firebaseAuth.createUserWithEmailAndPassword(email, pass);
        }

        public Task<AuthResult> CredentialLogin(AuthCredential credential) {
            checkInstance();
            return instance.firebaseAuth.signInWithCredential(credential);
        }

        public Task<AuthResult> CredentialRegister(AuthCredential credential) {
            checkInstance();
            return instance.firebaseAuth.signInWithCredential(credential);
        }

        public Task<AuthResult> LinkCredentials(AuthCredential credential) {
            checkInstance();
            return instance.firebaseAuth.getCurrentUser().linkWithCredential(credential);
        }

        public class PhoneAuthentication {
            String phoneNumber;
            PhoneVerificationListener verificationListener;

            /***
             * Will create a
             * @param listener
             * @param activity
             * @param phoneNumber
             * @param register
             */
            public PhoneAuthentication(PhoneVerificationListener listener, Activity activity, String phoneNumber, final boolean register) {
                this.phoneNumber = phoneNumber;
                this.verificationListener = listener;

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        3,
                        TimeUnit.MINUTES,
                        activity,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                verificationListener.VerificationComplete(phoneAuthCredential);
                                if (register)
                                    CredentialRegister(phoneAuthCredential).addOnCompleteListener(
                                            new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    verificationListener.CompleteRegister(task);
                                                }
                                            });
                                else {
                                    LinkCredentials(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            verificationListener.CompleteLink(task);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                e.printStackTrace();
                                verificationListener.VerificationFailed(e);
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Log.d("Phone Verification", "onCodeSent:" + verificationId);

                                // Save verification ID and resending token so we can use them later
//                                mVerificationId = verificationId;
//                                mResendToken = forceResendingToken;
                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String verificationId) {
                                super.onCodeAutoRetrievalTimeOut(verificationId);
                                Log.d("Phone Verification", "onCodeAutoRetrievalTimeOut:" + verificationId);

//                                mVerificationId = verificationId;
                            }
                        });
            }
        }
    }

}
