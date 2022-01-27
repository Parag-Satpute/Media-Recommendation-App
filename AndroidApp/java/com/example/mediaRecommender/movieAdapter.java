package com.example.mediaRecommender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.holder> {

    ArrayList<movie> recommendations;
    movieItemClicked bic;
    public movieAdapter(ArrayList<movie> recommendations, movieItemClicked bic) {
        this.recommendations = recommendations;
        this.bic = bic;
    }

    @NonNull
    @Override
    public movieAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.mm_recommendations_list_item,parent,false);
        holder h = new holder(view);
        view.setOnClickListener(view1 -> bic.onItemClicked(recommendations.get(h.getAdapterPosition())));
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull movieAdapter.holder holder, int position) {
        movie m = recommendations.get(position);
        holder.movieTitle.setText(m.title);
        if(!recommendations.get(position).poster.equals("")) {
            Glide.with(holder.itemView.getContext())
                    .load(recommendations.get(position).poster)
                    .into(holder.moviePoster);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class holder extends RecyclerView.ViewHolder
    {
        TextView movieTitle;
        ImageView moviePoster;
        public holder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.recommendationListTitle);
            moviePoster = itemView.findViewById(R.id.recommendationListPoster);
        }
    }
}

interface movieItemClicked{
    void onItemClicked(movie titleClicked);
}