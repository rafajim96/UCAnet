package com.pdm.ucanet;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Crash on 26/05/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageButton profileButton;
    private ImageButton homeButton;
    private ImageButton LogOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //INITIALIZING BUTTONS
        profileButton = (ImageButton) findViewById(R.id.imgProfileButton);
        homeButton = (ImageButton) findViewById(R.id.imgHomeButton);

        //SETTING THE HOME FRAGMENT AS DEFAULT
        HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();


        //LISTENERS FOR BUTTONS
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREATING PROFILE FRAGMENT
                android.support.v4.app.Fragment f =  getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(f != null && !(f instanceof ProfileFragment)){
                    ProfileFragment fragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
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

    }
}
