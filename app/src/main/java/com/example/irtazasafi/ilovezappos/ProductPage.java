package com.example.irtazasafi.ilovezappos;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.readystatesoftware.viewbadger.BadgeView;

public class ProductPage extends Activity {


    FloatingActionButton cartAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        cartAdd = (FloatingActionButton)findViewById(R.id.cartadd);


    }

    public void addToCart(View view){
        YoYo.with(Techniques.Wobble)
                .duration(700)
                .playOn(findViewById(R.id.cartadd));
        

//        YoYo.with(Techniques.BounceInUp)
//                .duration(700)
//                .playOn(findViewById(R.id.cartadd));
    }

}
