package com.pdm.ucanet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.resourceManagers.StaggeredGridLayoutAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Crash on 27/05/2017.
 */

public class HomeFragment extends Fragment {
    private ArrayList<Course> courses;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        courses = new ArrayList<>(3);

        Course a = new Course(1, "mate1");
        Course b = new Course(2, "turismo");
        Course c = new Course(3, "analisis");

        courses.add(a);
        courses.add(b);
        courses.add(c);

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager straggLayoutManager = new StaggeredGridLayoutManager(1, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(straggLayoutManager);
        recyclerView.setAdapter(new StaggeredGridLayoutAdapter(getActivity(), courses));

        return view;
    }

    public void createRecyclerView(ArrayList<Course> courses){
        //INITIALIZING RECYCLERVIEW

        /*

        */


    }
}
