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
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Thread;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.CourseCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.InformationAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.pdm.ucanet.resourceManagers.ThreadCardLayoutAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Crash on 04/06/2017.
 */

public class CourseActivity extends AppCompatActivity{
    public static final String CURRENT_COURSE = "current_curse";
    public static final String ID = "courseId";
    private TextView courseText;
    private String courseName;
    private int courseId;
    private Course currentCourse;
    private SwipeRefreshLayout swipeContainer;
    public InformationAdapter info = new InformationAdapter();

    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseText = (TextView) findViewById(R.id.courseTextView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        //SETTING THE CURRENT COURSE NAME
        courseName = getIntent().getExtras().getString(CURRENT_COURSE);
        courseId = getIntent().getExtras().getInt(ID);
        courseText.setText(courseName);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sessionManager = new SessionManager(this);
        loggedUser = sessionManager.loadSession();

        loadContent();

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
        //CREATING COURSE WITH THREADS FOR TESTING
        currentCourse = new Course(courseId, courseName);
        new LoadThreads().execute("go");


    }


private class LoadThreads extends AsyncTask<String, String, String> {
    private ProgressDialog progDailog;
    private ArrayList<Thread> threads;


    @Override
    protected String doInBackground(String... params) {
        //CHECK IF THE DATA OF THE LOGIN IS CORRECT
        try {

            threads = info.loadThreads(currentCourse.getIdCourse());
            return "did";
        }catch(IOException e){
            Toast.makeText(CourseActivity.this, "No se pudieron obtener los hilos 2", Toast.LENGTH_SHORT).show();
        }
        return "didnt";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            currentCourse.setCourseThreads(threads);
            recyclerView.setHasFixedSize(true);
            StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(straggLayoutManager);
            recyclerView.setAdapter(new ThreadCardLayoutAdapter(CourseActivity.this, currentCourse.getCourseThreads()));
        }catch (Exception e){
            Toast.makeText(CourseActivity.this, "Could not load threads", Toast.LENGTH_SHORT).show();
        }
        progDailog.dismiss();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(CourseActivity.this);
        progDailog.setMessage("Getting data...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {}

}




}


