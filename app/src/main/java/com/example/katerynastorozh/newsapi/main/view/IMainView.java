package com.example.katerynastorozh.newsapi.main.view;

import com.example.katerynastorozh.newsapi.main.model.News;

import java.util.List;

import retrofit2.Response;

public interface IMainView {


    void goToLogin();

    void updateApdater(List<News> response);
}
