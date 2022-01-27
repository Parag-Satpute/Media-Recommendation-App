package com.example.mediaRecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class bookDescriptionActivity extends AppCompatActivity {

    TextView title, authors, description;
    ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        Intent i = getIntent();
        book b = i.getParcelableExtra("book");
        poster = findViewById(R.id.bookPosterIV);
        title = findViewById(R.id.bookTitleTV);
        authors = findViewById(R.id.bookAuthorsTV);
        description = findViewById(R.id.bookDescriptionTV);
        description.setMovementMethod(new ScrollingMovementMethod());

        String a = b.author.substring(1, b.author.length()-1);
        String[] aa = a.split(",");
        String a1 = aa[0].substring(1, aa[0].length()-1);
        String a2="";
        if(aa.length > 1) a2 = aa[1].substring(1, aa[1].length()-1);
        if(a2.equals("")) authors.setText("Book By: " + a1);
        else authors.setText("Book By: " + a1 + ", " + a2);
        title.setText(b.title);

        if(!b.poster.equals("")) {
            Glide.with(this)
                    .load(b.poster)
                    .into(poster);
        }
        description.setText(b.description);
    }
}