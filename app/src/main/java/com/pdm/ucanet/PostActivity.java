package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Crash on 15/06/2017.
 */

public class PostActivity extends AppCompatActivity {
    private String threadName;
    private int threadId;
    private SessionManager sessionManager;
    private User loggedUser;
    private TextView textThreadName;
    private Button savePostButton;
    private InformationAdapter info = new InformationAdapter();
    private EditText content;
    private String contentS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        threadName = getIntent().getStringExtra("THREAD_NAME");
        threadId = getIntent().getIntExtra("threadId", 0);
        sessionManager = new SessionManager(this);
        loggedUser = sessionManager.loadSession();
        textThreadName = (TextView) findViewById(R.id.textThreadName);
        savePostButton = (Button) findViewById(R.id.buttonSavePost);

        textThreadName.setText(threadName);

        //SETTING LISTENERS FOR BUTTONS
        savePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = (EditText) findViewById(R.id.editText);
                contentS = content.getText().toString();
                try {
                    new insert().execute("go");
                    content.setText("");
                    //Toast.makeText(PostActivity.this, "Si se inserto", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(PostActivity.this, "No se inserto", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private class insert extends AsyncTask<String, String, String> {
        private ProgressDialog progDailog;



        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT
            try {
                //de momento imagen es cero y null
                info.insertPost(threadId, contentS, loggedUser.getUsername(),
                        0, null);
                return "did";
            }catch(IOException e){

            }
            return "didnt";
        }

        @Override
        protected void onPostExecute(String result) {
            progDailog.dismiss();
            Snackbar.make(findViewById(R.id.postLayout), "Post added", Snackbar.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(PostActivity.this);
            progDailog.setMessage("Inserting data...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}

    }




}
