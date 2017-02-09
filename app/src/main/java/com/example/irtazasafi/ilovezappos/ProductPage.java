package com.example.irtazasafi.ilovezappos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import static com.example.irtazasafi.ilovezappos.R.id.imageView;

public class ProductPage extends Activity {


    FloatingActionButton cartAdd;
    TextView discountAmount;
    ImageView animobj;
    ViewGroup touchTarget;
    Context context;
    Result product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        ActivityProductPageBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_product_page);
        product = ((SharedData)getApplicationContext()).getFirstResult();
        binding.setProduct(product);

        Picasso.with(this).load(product.getThumbnailImageUrl()).into((ImageView)findViewById(R.id.productImage));
        Picasso.with(this).load(product.getThumbnailImageUrl()).into((ImageView)findViewById(R.id.animObj));

        cartAdd = (FloatingActionButton)findViewById(R.id.cartadd);
        discountAmount= (TextView)findViewById(R.id.discountAmount);
        touchTarget = (ViewGroup)findViewById(R.id.touchTargets);
        context = this;
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
    }

    public void shareProduct(View view){
        String term = ((SharedData)getApplicationContext()).searchTerm;
        String link = "http://www.linktoproduct.com/ppage?term=" + term;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(link);

        Toast.makeText(this, "Sharable link copied to your clipboard",Toast.LENGTH_SHORT).show();


    }

    public void addToCart(View view){

        animobj.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0, -300, 0, 20);
        animation.setDuration(800);
        animation.setFillAfter(false);
        animobj.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animobj.setVisibility(View.INVISIBLE);

                Toast.makeText(context,
                        ((SharedData)getApplicationContext()).getFirstResult().getProductName()
                                + " added to cart!",Toast.LENGTH_SHORT).show();

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
    }


    public void onBackPressed(){
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle state){
        // nothing needs to be saved here. Product data is in SharedData, TextViews are saved automatically.//
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        // nothing else to restore.
    }

}
