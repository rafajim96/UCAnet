package com.pdm.ucanet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

/**
 * Created by Crash on 26/05/2017.
 */

public class HomeActivity extends AppCompatActivity {
    private ImageButton profileButton;
    private ImageButton messageButton;
    private ImageButton LogOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }
}
