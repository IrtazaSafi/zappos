package com.example.irtazasafi.ilovezappos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class user_link extends AppCompatActivity implements Callback<QueryResult> {

    public static String baseUrl = "https://api.zappos.com";
    public static Gson gson;
    public static Retrofit retrofit;
    zappoApi zappo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_link);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        zappo = retrofit.create(zappoApi.class);
        Uri data = this.getIntent().getData();
        if (data != null) {
            String uri = this.getIntent().getDataString();
            Log.i("MyApp", "Deep link clicked " + uri);
            String queryParam = data.getQueryParameter("term");
            System.out.println(queryParam);
            makeRequest(queryParam);
        }
    }

    public void makeRequest(String searchTerm){
        zappo.loadResponse(searchTerm).enqueue(this);
    }


    @Override
    public void onResponse(Call<QueryResult> call, Response<QueryResult> response) {

            if(response.isSuccessful()){
                QueryResult resp = response.body();
                if(resp.getResults().size() == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "No results for your search";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                Result first = resp.getResults().get(0);
                SharedData sharedData = (SharedData)getApplicationContext();
                sharedData.setFirstResult(first);
                startActivity(new Intent(this,ProductPage.class));
            } else {
                System.out.println("FAIL");
            }
    }
    @Override
    public void onFailure(Call<QueryResult> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        t.printStackTrace();

    }
}
