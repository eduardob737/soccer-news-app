package com.example.soccernews.ui.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.soccernews.R;
import com.example.soccernews.databinding.FragmentNewsBinding;
import com.example.soccernews.ui.adapter.NewsAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;
    private NewsViewModel.State state;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        observeNews();
        observeStates();

        binding.srlNews.setOnRefreshListener(newsViewModel::findNews);

        return root;
    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            this.state = state;
            switch (state){
                case DOING:
                    binding.srlNews.setRefreshing(true);
                    break;

                case DONE:
                    new Handler().postDelayed(() -> {
                        observeNews();
                    }, 4000);

                    binding.srlNews.setRefreshing(false);
                    break;

                case ERROR:
                    binding.srlNews.setRefreshing(false);
                    Snackbar.make(binding.getRoot(), R.string.error_network, BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, (updatedNews ->
                    newsViewModel.saveNews(updatedNews)), state));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}