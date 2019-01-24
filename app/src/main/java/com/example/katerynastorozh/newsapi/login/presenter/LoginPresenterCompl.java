package com.example.katerynastorozh.newsapi.login.presenter;

import android.content.Context;
import android.os.Looper;

import com.example.katerynastorozh.newsapi.login.model.IUser;
import com.example.katerynastorozh.newsapi.login.model.UserModel;
import com.example.katerynastorozh.newsapi.login.view.ILoginView;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

public class LoginPresenterCompl implements ILoginPresenter {
    private static final String LOG_TAG = LoginPresenterCompl.class.getSimpleName();
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser != null)
            {
                Log.d(LOG_TAG, "onAuthStateChanged, user != null");
                iLoginView.startProfileActivity();
            }
            else
            {
                Log.d(LOG_TAG, "onAuthStateChanged, user == null");
            }
        }
    };



    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        handler = new Handler(Looper.getMainLooper());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("users");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();

    }

    @Override
    public void singUP(String name, String passwd) {
        user = new UserModel.UserBuilder(name, passwd).build();
        auth.createUserWithEmailAndPassword(name, passwd)
                .addOnCompleteListener(iLoginView.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setProgressBarVisiblity(false);
                        final Boolean isLoginSuccess = task.isSuccessful();
                        if (isLoginSuccess) {
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else
                        {
                            Toast.makeText((Context) iLoginView.getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.d(LOG_TAG, task.getException().getMessage()+ "sing up is not success");

                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iLoginView.onLoginResult(isLoginSuccess, 0);
                            }
                        }, 5000);

                    }
                })
        .addOnCanceledListener(iLoginView.getActivity(), new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Log.d(LOG_TAG,  "login is canceled");

            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        writeNewUser(user.getUid());

    }

    private void writeNewUser(String userId) {

        databaseReference.child(userId).setValue(user);
    }

    @Override
    public void singIN(String name, String passwd) {
        user = new UserModel.UserBuilder(name, passwd).build();
        auth.signInWithEmailAndPassword(name, passwd)
                .addOnCompleteListener(iLoginView.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setProgressBarVisiblity(false);
                        final Boolean isLoginSuccess = task.isSuccessful();
                        if (isLoginSuccess) {
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else
                        {
                            //вынисти в вью клас
                            Toast.makeText((Context) iLoginView.getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iLoginView.onLoginResult(isLoginSuccess, 0);
                            }
                        }, 5000);


                    }
                })
                .addOnCanceledListener(iLoginView.getActivity(), new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText((Context) iLoginView.getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                    }
                });

    }


    @Override
    public void setProgressBarVisiblity(Boolean isVisible) {
        iLoginView.onSetProgressBarVisibility(isVisible);

    }

    @Override
    public Boolean isLoginIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }


    public void addAuthListener()
    {
        auth.addAuthStateListener(authStateListener);
    }


    public void removeAuthListener()
    {
        auth.removeAuthStateListener(authStateListener);
    }




}
