package com.undersnow.mathinder;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public TextView tvScore, tvCnt;
    int score = 0;
    private ProgressBar progressBar;
    private SwipeDeck cardStack;
    private Animation animShows, zoomIn, fadeIn;
    private int i = 0;
    boolean started = false;
    private CountDownTimer mCountDownTimer;
    private View swp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        swp =  findViewById(R.id.swp);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvCnt = (TextView) findViewById(R.id.tvCnt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        animShows = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final ArrayList<ArithmQuestion> testData = new ArrayList<>();


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/dinmdm.ttf");

        tvScore.setTypeface(custom_font);


        testData.add(new ArithmQuestion(ArithmQuestion.ADD));
        testData.add(new ArithmQuestion(ArithmQuestion.ADD));
        testData.add(new ArithmQuestion(ArithmQuestion.ADD));
        testData.add(new ArithmQuestion(ArithmQuestion.ADD));
        testData.add(new ArithmQuestion(ArithmQuestion.ADD));
        ;


        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("291361B6853EA3C74FB32BE3AB84F735")
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(testData, this, R.layout.crd_item);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);

                answer(position, false);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);

                answer(position, true);
            }

            private void answer(int position, boolean right) {
                if (!started) {
                    mCountDownTimer.start();
                    started = true;
                }

                testData.add(new ArithmQuestion(ArithmQuestion.RAND));
                boolean correct = (right && testData.get(position).isCorrect) || (!right && !testData.get(position).isCorrect);

                if (correct) {
                    score++;

                    //  blink(R.color.green, findViewById(R.id.tvScore));
                } else {
                    //  blink(R.color.red, findViewById(R.id.tvScore));
                  if(score>0)  score--;
                }
                tvScore.setText(score + "");
                adapter.notifyDataSetChanged();
                swp.setBackgroundColor( getResources().getColor(ArithmQuestion.colorByOp(testData.get(position+1).op)));

            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {
                Log.i("MainActivity", "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i("MainActivity", "cardActionUp");
            }


        });


        progressBar.setProgress(i);
        mCountDownTimer = new CountDownTimer(20000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                i++;
                makeProgress((int) i * 100 / (20000 / 100), progressBar);
                tvCnt.setText(String.format("%2d", millisUntilFinished/1000).replace(" ", "0")+" seconds");
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                progressBar.setProgress(100);
                showResults();
            }
        };

    }

    private void makeProgress(int progress, ProgressBar seekbar) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(seekbar, "progress", progress);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            seekbar.setProgress(progress);
    }

    private void showResults() {


        View v = findViewById(R.id.rlScore);
        cardStack.setVisibility(View.GONE);
        slideUp(v);

        Log.i("MainActivity", "no more cards");
       /* Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScore(getString(R.string.leaderboard_ranking), score);*/
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        zoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.tvMsg).setVisibility(View.VISIBLE);
                findViewById(R.id.tvMsg).startAnimation(fadeIn);
                findViewById(R.id.tvSubMsg).setVisibility(View.VISIBLE);
                findViewById(R.id.tvSubMsg).startAnimation(fadeIn);
                findViewById(R.id.btnranking).setVisibility(View.VISIBLE);
                findViewById(R.id.btnranking).startAnimation(fadeIn);
                findViewById(R.id.btnrestart).setVisibility(View.VISIBLE);
                findViewById(R.id.btnrestart).startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animShows.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                tvScore.startAnimation(zoomIn);
                startCountAnimation( Integer.parseInt("" + tvScore.getText()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animShows);
    }

    private void startCountAnimation(int score) {
        ValueAnimator animator = ValueAnimator.ofInt(0, score);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tvScore.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    void blink(int color, final View v) {
        int colorFrom = getResources().getColor(R.color.deflt);
        int colorTo = getResources().getColor(color);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250).setRepeatMode(ValueAnimator.REVERSE); // milliseconds
        colorAnimation.setRepeatCount(1);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                v.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("hello.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void startzxc(View v) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private static final int RC_LEADERBOARD_UI = 9004;

    public void showLeaderboard(View v) {

    }
}