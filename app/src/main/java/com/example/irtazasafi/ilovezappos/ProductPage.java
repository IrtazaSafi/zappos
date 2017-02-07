package com.example.irtazasafi.ilovezappos;

import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.irtazasafi.ilovezappos.databinding.ActivityProductPageBinding;
import com.google.gson.Gson;
import com.readystatesoftware.viewbadger.BadgeView;
import com.squareup.picasso.Picasso;
import android.widget.LinearLayout;
import static com.example.irtazasafi.ilovezappos.R.id.imageView;

public class ProductPage extends Activity {


    FloatingActionButton cartAdd;
    TextView discountAmount;
    ImageView animobj;
    ViewGroup touchTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        Result product = ((SharedData)getApplicationContext()).getFirstResult();
        ActivityProductPageBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_product_page);
        binding.setProduct(product);
        Picasso.with(this).load(product.getThumbnailImageUrl()).into((ImageView)findViewById(R.id.productImage));
        Picasso.with(this).load(product.getThumbnailImageUrl()).into((ImageView)findViewById(R.id.animObj));


        // product.setPercentOff("10%");
//        System.out.println(product.getOriginalPrice());
//        System.out.println(product.getPercentOff());
//        System.out.println(product.getPrice());


        cartAdd = (FloatingActionButton)findViewById(R.id.cartadd);
        discountAmount= (TextView)findViewById(R.id.discountAmount);
        touchTarget = (ViewGroup)findViewById(R.id.touchTargets);
        CoordinatorLayout disc = (CoordinatorLayout)findViewById(R.id.test);
        animobj = (ImageView)findViewById(R.id.animObj);
        animobj.setVisibility(View.INVISIBLE);
        if(product.getPercentOff().equals("0%")){
            product.setPercentOff("");
            product.setPrice("");
        } else {
            product.setPercentOff(product.getPercentOff() + " off!");

            TextView tv = (TextView) findViewById(R.id.origPrice);
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

//        touchTarget.setOnTouchListener(
//                new LinearLayout.OnTouchListener(){
//
//
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        move();
//                        return true;
//                    }
//                });



    }

    public void move(){
        LinearLayout.LayoutParams positionRules = new LinearLayout.
                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
               LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    public void addToCart(View view){

        animobj.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0, -300, 0, 20);
        animation.setDuration(1200);
        animation.setFillAfter(false);
        animobj.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animobj.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




        YoYo.with(Techniques.ZoomOut)
                .duration(200)
                .playOn(findViewById(R.id.cartadd));

        YoYo.with(Techniques.ZoomIn)
                .duration(500)
                .playOn(findViewById(R.id.cartadd));


//        YoYo.with(Techniques.BounceInUp)
//                .duration(700)
//                .playOn(findViewById(R.id.cartadd));
    }

}
