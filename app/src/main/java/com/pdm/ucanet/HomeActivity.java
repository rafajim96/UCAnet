package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.pdm.ucanet.fragmentClasses.HelpFragment;
import com.pdm.ucanet.fragmentClasses.HomeFragment;
import com.pdm.ucanet.fragmentClasses.ProfileFragment;
import com.pdm.ucanet.fragmentClasses.ThreadFragment;
import com.pdm.ucanet.resourceManagers.SessionManager;

/**
 * Created by Crash on 26/05/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageButton profileButton;
    private ImageButton homeButton;
    private ImageButton helpButton;
    private ImageButton logOffButton;
    private ImageButton newThreadButton;
    private SessionManager sessionManager;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //INITIALIZING BUTTONS
        profileButton = (ImageButton) findViewById(R.id.imgProfileButton);
        homeButton = (ImageButton) findViewById(R.id.imgHomeButton);
        helpButton = (ImageButton) findViewById(R.id.imgHelpButton);
        logOffButton = (ImageButton) findViewById(R.id.imgLogofButton);
        newThreadButton = (ImageButton) findViewById(R.id.imgThreadButton);
        scroll = (ScrollView) findViewById(R.id.scroll);

        if (savedInstanceState == null) {
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

                android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (f != null && !(f instanceof ProfileFragment) && (f instanceof HomeFragment || f instanceof ThreadFragment)) {  //(f != null && f instanceof ProfileFragment) to avoid replacing the same fragment
                    ProfileFragment fragment = new ProfileFragment();  //CREATING AND INITIALIZING FRAGMENT
                    getSupportFragmentManager().beginTransaction()  //BEGINNING TRANSACTION TO REPLACE FRAGMENT
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)  //ANIMATION FOR THE FRAGMENT, OMIT
                            .replace(R.id.fragmentContainer, fragment) //R.id.fragmentContainer is a linear-layout with that id on the xml, and the "fragment" to insert
                            .commit();
                }


                if (f != null && !(f instanceof ProfileFragment) && f instanceof HelpFragment) {
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
                if (f != null && !(f instanceof HomeFragment)) {
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

                android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (f != null && !(f instanceof HelpFragment) && (f instanceof HomeFragment || f instanceof ProfileFragment || f instanceof ThreadFragment)) {
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

        newThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATING NEW THREAD FRAGMENT


                android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (f != null && !(f instanceof ThreadFragment)) {
                    ThreadFragment fragment = new ThreadFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragmentContainer, fragment)
                            .commit();
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);


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
            progDailog.setMessage("Cerrando sesión...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}
    }
}
