package com.undersnow.mathinder;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class Luncher extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        MobileAds.initialize(this, "ca-app-pub-3577536631986182~9726310816");
        init(savedInstanceState);
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






    // Client used to sign in with Google APIs
    private GoogleSignInClient mGoogleSignInClient;

    // Client variables
    private LeaderboardsClient mLeaderboardsClient;
    private PlayersClient mPlayersClient;
    // request codes we use when invoking an external activity
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    // tag for debug logging
    private static final String TAG = "TanC";

    // playing on hard mode?
    private boolean mHardMode = false;




    public void init(Bundle savedInstanceState) {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
    }
        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount account;
        try {
              account = GoogleSignIn.getLastSignedInAccount(this);

  if (GoogleSignIn.hasPermissions(account, Games.SCOPE_GAMES_LITE)) {
                onConnected(account);
            } else {
                mGoogleSignInClient
                        .silentSignIn()
                        .addOnCompleteListener(
                                this, new OnCompleteListener<GoogleSignInAccount>() {
                                    @Override
                                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                        if (task.isSuccessful()) {
                                            onConnected(task.getResult());
                                        } else {
                                            onDisconnect();
                                        }
                                    }


                                });
            }

              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              
              

        } catch (Exception e) {
            e.printStackTrace();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getMessage());
            updateUI(null);
        }
    }

    private void onDisconnect() {
    }

    private void onConnected(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "onConnected(): connected to Google APIs");
        updateUI(googleSignInAccount);
        mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
    }
    private void updateUI(GoogleSignInAccount account) {
        Toast.makeText(this, "Signed "+account!=null?account.getFamilyName(): "NULL", Toast.LENGTH_LONG ).show();
    }

    public void showLeaderboards(View v) {
        mLeaderboardsClient.getAllLeaderboardsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_UNUSED);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        handleException(e, "leaderboards_exception");
                    }
                });
    }

    private void handleException(Exception e, String details) {
        int status = 0;

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            status = apiException.getStatusCode();
        }

        String message = "status_exception_error";

        new AlertDialog.Builder(Luncher.this)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
    }

}