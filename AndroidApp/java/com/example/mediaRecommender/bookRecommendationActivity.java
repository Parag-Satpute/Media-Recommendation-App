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

public class bookRecommendationActivity extends AppCompatActivity implements bookItemClicked {

    ArrayList<book> recommendations;
    EditText name;
    RecyclerView bookRV;
    bookAdapter adapter;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_recommendation);
        name = findViewById(R.id.bookET);
        bookRV = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        bookRV.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(view -> {
            hideKeyboard(view);
            bookRV.setVisibility(View.GONE);
            bookRecommendationActivity.this.getRecommendations();
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void getRecommendations() {
        recommendations = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://media-recommendations.herokuapp.com/predictBook";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("recommendations");
                        if (jsonArray.length() < 1) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(this, "Sorry! Book Not Available In Database..", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);

                                book b = new book(
                                        jo.getString("title"),
                                        jo.getString("poster"),
                                        jo.getString("authors"),
                                        jo.getString("description"),
                                        jo.getString("publisher"),
                                        jo.getString("published_date")
                                );
                                recommendations.add(b);
                            }
                            adapter = new bookAdapter(recommendations, this);
                            bookRV.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            bookRV.setVisibility(View.VISIBLE);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(bookRecommendationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("book",name.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(bookRecommendationActivity.this);
        queue.add(stringRequest);

    }

    @Override
    public void onItemClicked(book titleClicked) {
        Toast.makeText(this, titleClicked.title, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(bookRecommendationActivity.this, bookDescriptionActivity.class);
        intent.putExtra("book", titleClicked);
        startActivity(intent);
    }
}