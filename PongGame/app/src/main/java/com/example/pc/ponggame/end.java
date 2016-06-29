package com.example.pc.ponggame;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class end extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        final TextView tai_text = (TextView) findViewById(R.id.gameover);
        final TextView tai_text1 = (TextView) findViewById(R.id.emrea);
        final TextView tai_text2 = (TextView) findViewById(R.id.ozgun);
        final TextView tai_text3 = (TextView) findViewById(R.id.berkay);
        final TextView tai_text4 = (TextView) findViewById(R.id.basak);
        final TextView tai_text5 = (TextView) findViewById(R.id.emreme);

        tai_text.setVisibility(View.INVISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(tai_text, "alpha", 0f, 1f);
        anim.setDuration(3000);
        anim.start();

        TranslateAnimation animation = new TranslateAnimation(0.0f, 000.0f,
                0.0f, 1000.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(8000);  // animation duration
        /*animation.setRepeatCount(5);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )*/
        animation.setFillAfter(true);

        tai_text.startAnimation(animation);
        tai_text1.startAnimation(animation);
        tai_text2.startAnimation(animation);
        tai_text3.startAnimation(animation);
        tai_text4.startAnimation(animation);
        tai_text5.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                startActivity(new Intent(end.this, MainActivity.class));//Functionality here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }
}






