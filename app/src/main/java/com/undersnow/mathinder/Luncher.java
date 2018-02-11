package com.undersnow.mathinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.InjectView;
import butterknife.OnClick;

public class Luncher extends AppCompatActivity {

    private GoogleSignInApi mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        MobileAds.initialize(this, "ca-app-pub-3577536631986182~9726310816");
 /*       mGoogleSignInClient = GoogleSign.getClient(this,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());*/
     }

    public void showLeaderboard(View v) {

    }
    public void startzxc(View v){
        Log.i("TAGG" , "STARTING ");
        startActivity(new Intent(Luncher.this, MainActivity.class));
    }

    public void  shareApp(View v){

        Intent sharingIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_string, 10));
        startActivity(sharingIntent);

    }

    private static final int RC_LEADERBOARD_UI = 9004;

    private void ranking(View v) {
      /*  Game.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_id))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });*/
    }
}
