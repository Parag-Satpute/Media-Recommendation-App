package com.example.mediaRecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void movieRecommendations(View view) {
        Intent intent = new Intent(this, movieRecommendationActivity.class);
        startActivity(intent);
    }

    public void bookRecommendations(View view) {
        Intent intent = new Intent(MainActivity.this, bookRecommendationActivity.class);
        startActivity(intent);
    }

    public void musicRecommendations(View view) {
        Intent intent = new Intent(MainActivity.this, musicRecommendationsActivity.class);
        startActivity(intent);
    }
}