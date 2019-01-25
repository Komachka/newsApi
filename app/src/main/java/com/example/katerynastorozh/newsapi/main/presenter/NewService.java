package com.example.katerynastorozh.newsapi.main.presenter;

import com.example.katerynastorozh.newsapi.main.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewService {
    @GET()
    Call<List<News>> groupList();
}
