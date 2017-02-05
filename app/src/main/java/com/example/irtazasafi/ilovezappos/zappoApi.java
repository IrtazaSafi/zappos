package com.example.irtazasafi.ilovezappos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by irtazasafi on 2/5/17.
 */

public interface zappoApi {
    @GET("/Search?key=b743e26728e16b81da139182bb2094357c31d331")
    Call<QueryResult> loadResponse(@Query("term") String term);

}
