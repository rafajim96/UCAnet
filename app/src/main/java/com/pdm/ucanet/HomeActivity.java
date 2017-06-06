package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.pdm.ucanet.resourceManagers.SessionManager;

/**
 * Created by Crash on 26/05/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageButton profileButton;
    private ImageButton homeButton;
    private ImageButton helpButton;
    private ImageButton logOffButton;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //INITIALIZING BUTTONS
        profileButton = (ImageButton) findViewById(R.id.imgProfileButton);
        homeButton = (ImageButton) findViewById(R.id.imgHomeButton);
        helpButton = (ImageButton) findViewById(R.id.imgHelpButton);
        logOffButton = (ImageButton) findViewById(R.id.imgLogofButton);

        if(savedInstanceState == null){
            //SETTING THE HOME FRAGMENT AS DEFAULT
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();

        }




        //LISTENERS FOR BUTTONS
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATING PROFILE FRAGMENT
                android.support.v4.app.Fragment f =  getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(f != null && !(f instanceof ProfileFragment) && f instanceof HomeFragment){
                    ProfileFragment fragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

                if(f != null && !(f instanceof ProfileFragment) && f instanceof HelpFragment){
                    ProfileFragment fragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATING HOME FRAGMENT
                android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(f != null && !(f instanceof HomeFragment)){
                    HomeFragment fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATING HELP FRAGMENT
                android.support.v4.app.Fragment f =  getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(f != null && !(f instanceof HelpFragment) && f instanceof HomeFragment){
                    HelpFragment fragment = new HelpFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

                if(f != null && !(f instanceof HelpFragment) && f instanceof ProfileFragment){
                    HelpFragment fragment = new HelpFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

            }
        });

        logOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LOGGING OF THE APP
                sessionManager = new SessionManager(getApplicationContext());
                new LongOperation().execute();
            }
        });
    }



    private class LongOperation extends AsyncTask<String, String, String> {
        ProgressDialog progDailog = new ProgressDialog(HomeActivity.this);

        @Override
        protected String doInBackground(String... params) {
            //CLOSE THE SESSION
            for (int i = 0; i < 1; i++) {
                try {
                    sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.logOffSession();
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Logging off";
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            super.onPostExecute(result);
            if (result.equals("Logging off")){
                try {
                    progDailog.dismiss();
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();  //Kill the activity from which you will go to next activity
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Logging Off...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}
    }
}
