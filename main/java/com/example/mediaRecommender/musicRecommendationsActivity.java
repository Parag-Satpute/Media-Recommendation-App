package com.example.mediaRecommender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class musicRecommendationsActivity extends AppCompatActivity {

    ArrayList<music> recommendations;
    EditText name;
    RecyclerView musicRV;
    musicAdapter adapter;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_recommendations);
        name = findViewById(R.id.musicET);
        musicRV = findViewById(R.id.recyclerView_music);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        musicRV.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(view -> {
            hideKeyboard(view);
            musicRV.setVisibility(View.GONE);
            musicRecommendationsActivity.this.getRecommendations();
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void getRecommendations() {
        recommendations = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://media-recommendations.herokuapp.com/predictSong";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("recommendations");
                        if (jsonArray.length() < 1) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Sorry! Song Not Available In Database..", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);

                                music b = new music(
                                        jo.getString("title"),
                                        jo.getString("artist")
                                );
                                recommendations.add(b);
                            }
                            adapter = new musicAdapter(recommendations);
                            musicRV.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            musicRV.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(musicRecommendationsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("song",name.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(musicRecommendationsActivity.this);
        queue.add(stringRequest);

    }
}