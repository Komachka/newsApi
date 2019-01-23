package com.example.katerynastorozh.newsapi.login.login.view;

import android.app.Activity;
import android.content.Context;

public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);

    public Activity getContext();
}
