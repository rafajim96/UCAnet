package com.pdm.ucanet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Post;
import com.pdm.ucanet.abstractEntities.Thread;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.PostCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.pdm.ucanet.resourceManagers.ThreadCardLayoutAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Crash on 06/06/2017.
 */

public class ThreadActivity extends AppCompatActivity {
    public static final String CURRENT_THREAD = "current_thread";
    public static final String THREAD_ID = "current_thread_id";
    private TextView threadText;
    private int threadId;
    private String threadName;
    private Thread currentThread;
    private SwipeRefreshLayout swipeContainer;
    private Button postButton;
    public InformationAdapter info = new InformationAdapter();


    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        threadText = (TextView) findViewById(R.id.threadTextView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        postButton = (Button) findViewById(R.id.buttonPost);

        //SETTING THE CURRENT COURSE NAME
        threadName = getIntent().getExtras().getString(CURRENT_THREAD);
        threadId = getIntent().getExtras().getInt(THREAD_ID);
        threadText.setText(threadName);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sessionManager = new SessionManager(this);
        loggedUser = sessionManager.loadSession();

        loadContent();

        //SETTING LISTENERS FOR BUTTONS
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NEW POST
                Intent intent = new Intent(getBaseContext(), PostActivity.class);
                intent.putExtra("THREAD_NAME", threadName);
                intent.putExtra("threadId", threadId);
                startActivity(intent);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //fetchTimelineAsync(0);
                loadContent();
                swipeContainer.setRefreshing(false);
            }
        });

    }

    private void loadContent(){
        //LOAD THREADS FROM DATABASE
        currentThread = new Thread(threadId, threadName);
        new loadPosts().execute("go");
    }

    private class loadPosts extends AsyncTask<String, String, String>{

        private ProgressDialog progDailog;
        private ArrayList<Post> posts;


        @Override
        protected String doInBackground(String... params) {
            //CHECK IF THE DATA OF THE LOGIN IS CORRECT
            try {
                posts = info.loadPosts(currentThread.getId());
                return "did";
            }catch(IOException e){
                Toast.makeText(ThreadActivity.this, "No se pudo obtener los posts ", Toast.LENGTH_SHORT).show();
            }
            return "didnt";
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                currentThread.setPosts(posts);
                recyclerView.setHasFixedSize(true);
                StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(straggLayoutManager);
                recyclerView.setAdapter(new PostCardLayoutAdapter(ThreadActivity.this, currentThread.getPosts()));
            }catch (Exception e){
                Toast.makeText(ThreadActivity.this, "No se pudo obtener los posts", Toast.LENGTH_SHORT).show();
            }
            progDailog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(ThreadActivity.this);
            progDailog.setMessage("Obteniendo información...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {}

    }

    @Override
    public void onResume() {
        super.onResume();
        loadContent();
    }
}
