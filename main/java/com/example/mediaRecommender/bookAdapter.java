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

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.holder> {

    ArrayList<book> recommendations;
    bookItemClicked bic;
    public bookAdapter(ArrayList<book> recommendations, bookItemClicked bic) {
        this.recommendations = recommendations;
        this.bic = bic;
    }

    @NonNull
    @Override
    public bookAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.books_recommedations_list_item,parent,false);
        holder h = new holder(view);
        view.setOnClickListener(view1 -> bic.onItemClicked(recommendations.get(h.getAdapterPosition())));
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull bookAdapter.holder holder, int position) {
        book b = recommendations.get(position);
        String a = b.author.substring(1, b.author.length()-1);
        String[] aa = a.split(",");
        String a1 = aa[0].substring(1, aa[0].length()-1);
        String a2="";
        if(aa.length > 1) a2 = aa[1].substring(1, aa[1].length()-1);
        if(a2.equals("")) holder.bookAuthor.setText("Book By: " + a1);
        else holder.bookAuthor.setText("Book By: " + a1 + ", " + a2);
        holder.bookTitle.setText(b.title);
        Log.d("Poster", recommendations.get(position).poster);
        if(!recommendations.get(position).poster.equals("")) {
            Glide.with(holder.itemView.getContext())
                    .load(recommendations.get(position).poster)
                    .into(holder.bookPoster);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class holder extends RecyclerView.ViewHolder
    {
        TextView bookAuthor;
        TextView bookTitle;
        ImageView bookPoster;
        public holder(@NonNull View itemView) {
            super(itemView);
            bookAuthor = itemView.findViewById(R.id.bookRecommendationListAuthor);
            bookTitle = itemView.findViewById(R.id.bookRecommendationListTitle);
            bookPoster = itemView.findViewById(R.id.bookRecommendationListPoster);
        }
    }
}

interface bookItemClicked{
    void onItemClicked(book titleClicked);
}
