package com.example.mediaRecommender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class musicAdapter extends RecyclerView.Adapter<musicAdapter.holder>{
    ArrayList<music> recommendations;
    public musicAdapter(ArrayList<music> recommendations) {
        this.recommendations = recommendations;
    }

    @NonNull
    @Override
    public musicAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.music_recommendation_list_item,parent,false);
        musicAdapter.holder h = new musicAdapter.holder(view);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull musicAdapter.holder holder, int position) {
        music b = recommendations.get(position);
        holder.title.setText(b.song);
        holder.artist.setText("Song By " + b.artist);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class holder extends RecyclerView.ViewHolder
    {
        TextView title, artist;
        //ImageView poster;
        public holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.musicRecommendationListTitle);
            artist = itemView.findViewById(R.id.musicRecommendationListArtist);
        }
    }
}
