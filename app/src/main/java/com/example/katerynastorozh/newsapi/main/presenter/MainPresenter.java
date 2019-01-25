package com.example.katerynastorozh.newsapi.main.presenter;

import android.support.annotation.NonNull;

import com.example.katerynastorozh.newsapi.main.model.News;
import com.example.katerynastorozh.newsapi.main.view.IMainView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements IMainPresenter {

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    IMainView view;
    Retrofit retrofit;
    public static final String API_KEY = "7cf06a7c9b2a4a6891e1f2edca2bfa87";
    public static final String BASE_URL = "https://newsapi.org/v2/everything?q=bitcoin&from=2018-12-25&sortBy=publishedAt&apiKey=";


    public MainPresenter(IMainView mainActivity) {
        view = mainActivity;
        auth = FirebaseAuth.getInstance();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL + API_KEY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewService service = retrofit.create(NewService.class);
        Call<List<News>> newsCall = service.groupList();
        newsCall.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                List<News> news = response.body();
                view.updateApdater(news);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    public void addAuthListener()
    {
        auth.addAuthStateListener(authStateListener);
    }

    public void removeAuthListener()
    {
        auth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void singOut() {
        auth.signOut();
        view.goToLogin();
    }
}
