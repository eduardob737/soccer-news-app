package com.example.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        //TODO Remover mock de notícias
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("Ferroviária tem desfalque importante",""));
        newsList.add(new News("Sorteio da Copa das Minas acontece nesse sábado",""));
        newsList.add(new News("Treinadora Carla Santana é apresentado no Linense",""));

        this.news.setValue(newsList);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}