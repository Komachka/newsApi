package com.example.katerynastorozh.newsapi.login.login.presenter;

public interface ILoginPresenter {
    void clear();
    void singUP(String name, String passw);
    void singIN(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
