package com.example.soccernews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccernews.R;
import com.example.soccernews.databinding.NewsItemBinding;
import com.example.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> news;
    private final FavoriteListener favoriteListener;

    public NewsAdapter(List<News> news, FavoriteListener favoriteListener) {
        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvDescription.setText(news.description);
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbnail);

        holder.binding.btOpenLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.link));
            holder.itemView.getContext().startActivity(intent);
        });

        holder.binding.ivShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, news.title);
            intent.putExtra(Intent.EXTRA_TEXT, news.link);
            holder.itemView.getContext().startActivity(Intent.createChooser(intent, "Share via"));
        });

        // implementar ação de favoritar
        holder.binding.ivFavorite.setOnClickListener(v -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });
            int iconFavorite = news.favorite ? R.drawable.ic_favorite : R.drawable.ic_unfavorite;
            holder.binding.ivFavorite.setImageResource(iconFavorite);
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface FavoriteListener {
        void onFavorite(News news);
    }
}
