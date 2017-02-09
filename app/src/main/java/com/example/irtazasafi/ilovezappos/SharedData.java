package com.example.irtazasafi.ilovezappos;

import android.app.Application;

/**
 * Created by irtazasafi on 2/5/17.
 */

public class SharedData extends Application {

    private Result firstResult;

    Result getFirstResult(){
        return firstResult;
    }

    String searchTerm;

    void setFirstResult(Result _result){
        firstResult = _result;
    }
}
