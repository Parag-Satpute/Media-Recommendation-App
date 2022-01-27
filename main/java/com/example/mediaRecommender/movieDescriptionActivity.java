package com.example.mediaRecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class movieDescriptionActivity extends AppCompatActivity {

    TextView title, genres, description, cast, director;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        Intent i = getIntent();
        movie m = i.getParcelableExtra("movie");
        poster = findViewById(R.id.moviePosterIV);
        title = findViewById(R.id.movieTitleTV);
        cast = findViewById(R.id.movieCastTV);
        director = findViewById(R.id.movieDirectorTV);
        description = findViewById(R.id.movieOverviewTV);
        description.setMovementMethod(new ScrollingMovementMethod());
        genres = findViewById(R.id.movieGenresTV);

        String lr = m.cast.substring(1, m.cast.length()-1);
        String[] lra = lr.split(",");
        String lr0 = lra[0].substring(1, lra[0].length()-1);
        String lr1 = lra[1].substring(2, lra[1].length()-1);
        String lr2 = lra[2].substring(2, lra[2].length()-1);

        String g = m.genres.substring(1, m.genres.length()-1);
        Log.i("lra[0]", g);
        String[] ga = g.split(",");

        ga[0] = ga[0].substring(1, ga[0].length()-1);
        String gens = ga[0];
        for(int j=1; j<ga.length; j++){
            Log.i("lra[0]", ga[j]);
            ga[j] = ga[j].substring(2, ga[j].length()-1);
            gens += ", " + ga[j];
        }
        if(!m.poster.equals("")) Glide.with(this).load(m.poster).into(poster);
        title.setText(m.title);
        cast.setText("Lead Roles: " + lr0 + ", " + lr1 + ", " + lr2);
        genres.setText("Genres: " + gens);
        description.setText(m.description);
        String c = m.crew.substring(2, m.crew.length()-2);
        director.setText("Directed By: " + c);
    }
}