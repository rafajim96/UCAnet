package com.pdm.ucanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.SessionManager;

/**
 * Created by Crash on 15/06/2017.
 */

public class PostActivity extends AppCompatActivity {
    private String threadName;
    private SessionManager sessionManager;
    private User loggedUser;
    private TextView textThreadName;
    private Button savePostButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        threadName = getIntent().getStringExtra("THREAD_NAME");
        sessionManager = new SessionManager(this);
        loggedUser = sessionManager.loadSession();
        textThreadName = (TextView) findViewById(R.id.textThreadName);

        textThreadName.setText(threadName);

        //SETTING LISTENERS FOR BUTTONS
        savePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAVE NEW POST

            }
        });


    }


}
