package com.pdm.ucanet.fragmentClasses;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pdm.ucanet.R;
import com.pdm.ucanet.abstractEntities.Course;
import com.pdm.ucanet.concreteEntities.User;
import com.pdm.ucanet.resourceManagers.CourseCardLayoutAdapter;
import com.pdm.ucanet.resourceManagers.SessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Crash on 09/06/2017.
 */

public class ThreadFragment extends Fragment {
    private Spinner coursesSpinner;
    private EditText titleEdit;
    private EditText contentEdit;
    private ImageView image;
    private Button uploadImage;
    private Button saveThread;
    private ArrayAdapter<String> dataAdapter;

    private SessionManager sessionManager;
    private User loggedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_thread, container, false);

        coursesSpinner = (Spinner) view.findViewById(R.id.spinnerCourses);
        titleEdit = (EditText) view.findViewById(R.id.editTitle);
        contentEdit = (EditText) view.findViewById(R.id.editContent);
        uploadImage = (Button) view.findViewById(R.id.buttonUploadImage);
        saveThread = (Button) view.findViewById(R.id.buttonSaveThread);

        sessionManager = new SessionManager(getContext());
        loggedUser = sessionManager.loadSession();

        dataAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                loggedUser.getCoursesNames());

        coursesSpinner.setAdapter(dataAdapter);

        //SETTING LISTENERS FOR BUTTONS
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPLOADING IMAGE


            }
        });

        saveThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SAVE THREAD


            }
        });





        return view;
    }
}
