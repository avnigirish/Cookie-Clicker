package com.example.cookieclicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ImageView cookie, Grandma, Mine;
    ConstraintLayout layout;
    TextView cookieCount;
    TextView TVFC;
    Button grandma, mine;
    TextView gcost, mcost;
    AnimationSet grandmaAnimation, mineAnimation;
    Animation one;
    int count1 = 0;
    int count2 = 0;
    int gOwn=0;
    int mOwn = 0;
    int gCost=10;
    int mCost = 50;
    int perSec=0;
    TextView cookieGen;
    static AtomicInteger ai;
    Thread thread;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cookie = findViewById(R.id.cookie);
        cookieCount = findViewById(R.id.cookieC);
        layout = findViewById(R.id.id_layout);
        grandma = findViewById(R.id.Grandma);
        mine = findViewById(R.id.Mine);
        gcost = findViewById(R.id.gCost);
        mcost = findViewById(R.id.mCost);
        cookieGen = findViewById(R.id.perSec);
        ai = new AtomicInteger(0);
        addTouchListener();
        //Log.d("TAG - num of cookies i", ""+ai.get());

        grandma.setEnabled(false);
        grandma.setBackgroundColor(Color.DKGRAY);
        mine.setEnabled(false);
        mine.setBackgroundColor(Color.DKGRAY);
        thread = new Thread("Thread") {
            @Override
            public void run() {
                while (!currentThread().isInterrupted()) {
                    runOnUiThread(() -> {
                        cookieCount.setText(ai + " cookies");
                        if(ai.get()>=gCost) {
                            //Log.d("TAG - num of cookies", "" + ai.get());
                            grandma.setEnabled(true);
                            grandma.setBackgroundColor(Color.rgb(170, 165, 165));
                        }
                        if(ai.get()<gCost){
                            //Log.d("TAG - num of cookies", "" + ai.get());
                            grandma.setEnabled(false);
                            grandma.setBackgroundColor(Color.DKGRAY);
                        }
                        if(ai.get()>=mCost) {
                            //Log.d("TAG - num of cookies", "" + ai.get());
                            mine.setEnabled(true);
                            mine.setBackgroundColor(Color.rgb(170, 165, 165));
                        }
                        if(ai.get()<mCost){
                            // Log.d("TAG - num of cookies", "" + ai.get());
                            mine.setEnabled(false);
                            mine.setBackgroundColor(Color.DKGRAY);
                        }
                    });
                }
            }
        };
        thread.start();

        grandma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gOwn++;
                new secondThread().run();
                ai.addAndGet(-gCost);
                gCost += 5;
                gcost.setText(""+gCost);
                if(ai.get()<gCost){
                    grandma.setEnabled(false);
                    grandma.setBackgroundColor(Color.DKGRAY);
                }
                perSec+=1;
                cookieGen.setText("per second: "+ perSec+" cookies");
                cookieCount.setText(ai + " cookies");

                Grandma = new ImageView(getApplicationContext());
                Grandma.setId(View.generateViewId());
                Grandma.setImageResource(R.drawable.grandmother);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                Grandma.setLayoutParams(params);

                layout.addView(Grandma);
                Grandma.setLayoutParams(params);

                Grandma.getLayoutParams().height=100;
                Grandma.getLayoutParams().width=100;
                Grandma.requestLayout();

                Animation in = new TranslateAnimation(0,0,50,0);
                Grandma.setY(count1);
                count1+=110;
                in.setDuration(1100);
                in.setFillAfter(true);
                Grandma.setVisibility(View.VISIBLE);

                Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(1000);

                grandmaAnimation = new AnimationSet(false);
                grandmaAnimation.addAnimation(in);
                grandmaAnimation.addAnimation(fadeIn);

                Grandma.startAnimation(grandmaAnimation);
            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOwn++;
                new thirdThread().run();
                ai.addAndGet(-mCost);
                mCost += 50;
                mcost.setText(""+mCost);
                if(ai.get()<mCost){
                    grandma.setEnabled(false);
                    grandma.setBackgroundColor(Color.DKGRAY);
                }
                perSec+=10;
                cookieCount.setText(ai + " cookies");
                cookieGen.setText("per second: "+ perSec+" cookies");

                Mine = new ImageView(getApplicationContext());
                Mine.setId(View.generateViewId());
                Mine.setImageResource(R.drawable.mine);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                Mine.setLayoutParams(params);

                layout.addView(Mine);
                Mine.setLayoutParams(params);

                Mine.getLayoutParams().height=100;
                Mine.getLayoutParams().width=110;
                Mine.setX(980);
                Mine.requestLayout();

                Animation in = new TranslateAnimation(0,0,50,0);
                Mine.setY(count2);
                count2+=110;
                in.setDuration(1100);
                in.setFillAfter(true);
                Mine.setVisibility(View.VISIBLE);

                Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(1000);

                mineAnimation = new AnimationSet(false);
                mineAnimation.addAnimation(in);
                mineAnimation.addAnimation(fadeIn);

                Mine.startAnimation(mineAnimation);
            }
        });

    }
    public static class MyThread extends Thread{
        public void run(){
            ai.getAndAdd(1);
        }
    }
    public static class secondThread extends Thread{
        public void run() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    ai.getAndAdd(1);
                }
            }, 0, 1000);
        }
    }
    public static class thirdThread extends Thread{
        public void run() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    ai.getAndAdd(10);
                }
            }, 0, 1000);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addTouchListener(){
        ImageView image = (ImageView)findViewById(R.id.cookie);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x= event.getX()+300;
                float y= event.getY()+610;
                //String message = String.format("Coordinates: (%.2f, %.2f)", x,y);
                //Log.d("TAG", message);

                new MyThread().run();

                ScaleAnimation increaseSize = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                increaseSize.setDuration(250);
                v.startAnimation(increaseSize);
                TVFC = new TextView(getApplicationContext());
                TVFC.setId(View.generateViewId());
                TVFC.setText("+1");
                TVFC.setTextColor(Color.WHITE);
                TVFC.setTextSize(24f);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
                TVFC.setLayoutParams(params);

                layout.addView(TVFC);
                TVFC.setLayoutParams(params);

                one = new TranslateAnimation(x,x,y,-50);
                one.setDuration(3000);
                one.setFillAfter(false);
                TVFC.startAnimation(one);
                TVFC.setVisibility(View.INVISIBLE);

                cookieCount.setText(ai + " cookies");
                Log.d("TAG","count: "+layout.getChildCount());
                return false;
            }
        });
        /*one.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.post(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layout.removeView(TVFC);
                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        //Log.d("TAG", "count: "+ layout.getChildCount());
    }
}

