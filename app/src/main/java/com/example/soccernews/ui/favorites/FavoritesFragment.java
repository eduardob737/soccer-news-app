package com.example.soccernews.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.soccernews.MainActivity;
import com.example.soccernews.databinding.FragmentFavoritesBinding;
import com.example.soccernews.domain.News;
import com.example.soccernews.ui.adapter.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoriteNews();

        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                List<News> favoriteNews = activity.getDb().newsDAO().loadFavoriteNews();
                binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvNews.setHasFixedSize(true);
                    binding.rvNews.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
                        activity.getDb().newsDAO().save(updatedNews);
                        loadFavoriteNews();
                    }));
                Log.i("test", String.valueOf(favoriteNews.size()));
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}