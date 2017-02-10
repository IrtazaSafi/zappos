package com.example.irtazasafi.ilovezappos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements Callback<QueryResult> {

    public static String baseUrl = "https://api.zappos.com";
    public static Gson gson;
    public static Retrofit retrofit;
    zappoApi zappo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        zappo = retrofit.create(zappoApi.class);
    }

    public void search(View v){
       EditText query = (EditText)findViewById(R.id.searchQuery);
        if(query.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Please enter a search query",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        makeRequest(query.getText().toString());
    }

    public void makeRequest(String searchTerm){
        zappo.loadResponse(searchTerm).enqueue(this);
    }

    @Override
    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
        if(response.isSuccessful()){
            QueryResult resp = response.body();
            if(resp.getResults().size() == 0){
              Toast.makeText(getApplicationContext(), "No results for your search", Toast.LENGTH_SHORT).show();
                return;
            }
            Result first = resp.getResults().get(0);
            SharedData sharedData = (SharedData)getApplicationContext();
            sharedData.setFirstResult(first);
            sharedData.searchTerm = resp.getTerm();
            startActivity(new Intent(this,ProductPage.class));
        } else {
            System.out.println("Response Error");
        }
    }
    @Override
    public void onFailure(Call<QueryResult> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Oops! Something went wrong with this search." +
                " Make sure you have an active internet connection and try again.", Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }

    protected void onSaveInstanceState(Bundle state){
        //TextViews are saved automatically.//
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        // nothing else to restore.
    }

}
