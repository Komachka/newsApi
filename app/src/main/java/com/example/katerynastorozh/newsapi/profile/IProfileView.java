package com.example.katerynastorozh.newsapi.profile;

import com.example.katerynastorozh.newsapi.login.model.IUser;
import com.example.katerynastorozh.newsapi.login.model.UserModel;

import java.util.HashMap;

public interface IProfileView {

    void goToMainActivity();

    void updateUI(UserModel user);
}
