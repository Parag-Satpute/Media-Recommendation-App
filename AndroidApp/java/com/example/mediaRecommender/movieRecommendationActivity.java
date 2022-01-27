package com.example.mediaRecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class movieRecommendationActivity extends AppCompatActivity implements movieItemClicked {

    ArrayList<movie> recommendations;
    EditText name;
    RecyclerView movieRV;
    movieAdapter adapter;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_recommendation);
        name = findViewById(R.id.movieET);
        movieRV = findViewById(R.id.recyclerView_movie);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        movieRV.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(view -> {
            hideKeyboard(view);
            movieRV.setVisibility(View.GONE);
            movieRecommendationActivity.this.getRecommendations();
        });
    }
    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void getRecommendations() {
        recommendations = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://media-recommendations.herokuapp.com/predictMovie";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("recommendations");
                        if(jsonArray.length()<1){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Sorry! Movie Not Available In Database..", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);

                                movie b = new movie(
                                        jo.getString("title"),
                                        jo.getString("poster"),
                                        jo.getString("cast"),
                                        jo.getString("crew"),
                                        jo.getString("genres"),
                                        jo.getString("overview")
                                );
                                recommendations.add(b);
                            }
                            adapter = new movieAdapter(recommendations, this);
                            movieRV.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            movieRV.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(movieRecommendationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("movie",name.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(movieRecommendationActivity.this);
        queue.add(stringRequest);

    }

    @Override
    public void onItemClicked(movie titleClicked) {
        Toast.makeText(this, titleClicked.title, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(movieRecommendationActivity.this, movieDescriptionActivity.class);
        intent.putExtra("movie", titleClicked);
        startActivity(intent);
    }
}