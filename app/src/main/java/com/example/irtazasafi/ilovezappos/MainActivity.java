package com.example.irtazasafi.ilovezappos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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



        //makeRequest("apple");
    }

    public void search(View v){

       EditText query = (EditText)findViewById(R.id.searchQuery);
        makeRequest(query.getText().toString());

    }

    public void makeRequest(String searchTerm){
        zappo.loadResponse(searchTerm).enqueue(this);
    }

    @Override
    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {
        if(response.isSuccessful()){
            System.out.println("SUCCESS");

            QueryResult resp = response.body();
            Result first = resp.getResults().get(0);
            SharedData sharedData = (SharedData)getApplicationContext();
            sharedData.setFirstResult(first);

            startActivity(new Intent(this,ProductPage.class));

            //System.out.println(first.getBrandName() + " " + first.getThumbnailImageUrl() + " " + first.getProductName());

        } else {
            System.out.println("FAIL");
        }
    }

    @Override
    public void onFailure(Call<QueryResult> call, Throwable t) {
        t.printStackTrace();
    }
}
