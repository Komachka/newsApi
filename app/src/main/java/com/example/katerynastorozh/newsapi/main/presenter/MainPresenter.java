package com.example.katerynastorozh.newsapi.main.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public class MainPresenter implements IMainPresenter {

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    public MainPresenter() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

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
