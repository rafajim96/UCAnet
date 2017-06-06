package com.pdm.ucanet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.abstractEntities.Post;
import com.pdm.ucanet.abstractEntities.Thread;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.PostCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.pdm.ucanet.resourceManagers.ThreadCardLayoutAdapter;

import java.util.ArrayList;

/**
 * Created by Crash on 06/06/2017.
 */

public class ThreadActivity extends AppCompatActivity {
    public static final String CURRENT_THREAD = "current_thread";
    private TextView threadText;
    private String threadName;
    private Thread currentThread;
    private SwipeRefreshLayout swipeContainer;

    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        threadText = (TextView) findViewById(R.id.threadTextView);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        //SETTING THE CURRENT COURSE NAME
        threadName = getIntent().getExtras().getString(CURRENT_THREAD);
        threadText.setText(threadName);

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
                Toast.makeText(getBaseContext(), "Refresh", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });

    }

    private void loadContent(){
        //LOAD THREADS FROM DATABASE
        //CREATING COURSE WITH THREADS FOR TESTING
        currentThread = new Thread(threadName);
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("dhfkjlsadfjklsadhflksjdhflskdajfhsadlkjfhsaddfjklsdhfuiwecronweriuyeotivuyertuoireynvwtoiuweryntoieruvyntogsadkjashgdkasjhdgasjkhdgsyaduweigduyweybewcuyrtbewuycrbwteuyrcxbtweuiyrtewuiycrbwteuircbtweiurcbtweiurcbytewcriuycbwtweiuyrctbweiuyrcbtweiuyrcbwetryuwebreuyrbcwetyurcibwetrcuiwebtcyrwiwteyurcbtweruycebwtiuybteriuvyntreoiutynreoitvnuretvoiumgjkgjhkg"));
        posts.add(new Post("dhfkryntoieruvyntoeriuvyntreoiutynreoitvnuretvoiumgjkgjhkg"));
        posts.add(new Post("dhfkjlsadfjklsadhflksjdhflskdajfhsadlkjfhsaddfjklsdhfuiwecronweriuyeotivuyertuoireynvyntreoiutynreoitvnuretvoiumgjkgjhkg"));
        currentThread.setPosts(posts);

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(straggLayoutManager);
        recyclerView.setAdapter(new PostCardLayoutAdapter(this, currentThread.getPosts()));
    }
}
