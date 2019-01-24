package com.example.katerynastorozh.newsapi.login.view;

import android.app.Activity;

import com.example.katerynastorozh.newsapi.login.model.IUser;
import com.google.firebase.auth.FirebaseUser;

public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(Boolean isVisible);

    public Activity getActivity();

    public boolean isPasswordValid(String password);
    public boolean isEmailValid(String email);

    public void updateUi(FirebaseUser user);
    public void startProfileActivity();

}
