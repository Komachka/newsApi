package com.example.katerynastorozh.newsapi.login.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.example.katerynastorozh.newsapi.login.login.model.IUser;
import com.example.katerynastorozh.newsapi.login.login.model.UserModel;
import com.example.katerynastorozh.newsapi.login.login.view.ILoginView;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;


    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        //user = new UserModel("name", "pass"); //????
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

      /*  Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }*/

      user = new UserModel(name, passwd);
        auth.createUserWithEmailAndPassword(name, passwd)
                .addOnCompleteListener(iLoginView.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        setProgressBarVisiblity(View.INVISIBLE);
                        final Boolean isLoginSuccess = task.isSuccessful();
                        if (isLoginSuccess) {
                            //вынисти в вью класс
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else
                        {
                            //вынисти в вью клас
                            Toast.makeText((Context) iLoginView.getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iLoginView.onLoginResult(isLoginSuccess, 0);
                            }
                        }, 5000);


                    }
                })
        .addOnCanceledListener(iLoginView.getContext(), new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText((Context) iLoginView.getContext(), "Cancel", Toast.LENGTH_LONG).show();
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


        user = new UserModel(name, passwd);
        auth.signInWithEmailAndPassword(name, passwd)
                .addOnCompleteListener(iLoginView.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        setProgressBarVisiblity(View.INVISIBLE);
                        final Boolean isLoginSuccess = task.isSuccessful();
                        if (isLoginSuccess) {
                            //вынисти в вью класс
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else
                        {
                            //вынисти в вью клас
                            Toast.makeText((Context) iLoginView.getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                iLoginView.onLoginResult(isLoginSuccess, 0);
                            }
                        }, 5000);


                    }
                })
                .addOnCanceledListener(iLoginView.getContext(), new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText((Context) iLoginView.getContext(), "Cancel", Toast.LENGTH_LONG).show();
                    }
                });

    }


    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);

    }
}
