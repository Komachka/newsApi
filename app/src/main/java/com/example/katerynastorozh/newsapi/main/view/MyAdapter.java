package com.example.katerynastorozh.newsapi.main.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.katerynastorozh.newsapi.main.model.News;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter {
    List<News> mDataSet;


    public MyAdapter(List<News> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
