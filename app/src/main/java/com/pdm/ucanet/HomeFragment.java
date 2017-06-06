package com.pdm.ucanet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.SessionManager;
import com.pdm.ucanet.resourceManagers.CourseCardLayoutAdapter;

/**
 * Created by Crash on 27/05/2017.
 */

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private User loggedUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        sessionManager = new SessionManager(getContext());
        loggedUser = sessionManager.loadSession();

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(straggLayoutManager);
        recyclerView.setAdapter(new CourseCardLayoutAdapter(getActivity(), loggedUser.getCourses()));

        return view;
    }


}
