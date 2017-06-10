package com.pdm.ucanet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Thread;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.CourseCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.pdm.ucanet.resourceManagers.ThreadCardLayoutAdapter;

import java.util.ArrayList;

/**
 * Created by Crash on 04/06/2017.
 */

public class CourseActivity extends AppCompatActivity {
    public static final String CURRENT_COURSE = "current_curse";
    private TextView courseText;
    private String courseName;
    private Course currentCourse;
    private SwipeRefreshLayout swipeContainer;


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
                //loadContent();
                Toast.makeText(getBaseContext(), "Refresh", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });

    }

    private void loadContent(){
        //LOAD THREADS FROM DATABASE
        //CREATING COURSE WITH THREADS FOR TESTING
        currentCourse = new Course(1, courseName);
        ArrayList<Thread> testThreads = new ArrayList<>();
        testThreads.add(new Thread("Como se hace esta integral?"));
        testThreads.add(new Thread("Hay clases?"));
        testThreads.add(new Thread("Cuando es el parcial?"));
        currentCourse.setCourseThreads(testThreads);

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(straggLayoutManager);
        recyclerView.setAdapter(new ThreadCardLayoutAdapter(this, currentCourse.getCourseThreads()));
    }
}
