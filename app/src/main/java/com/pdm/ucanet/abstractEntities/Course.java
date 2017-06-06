package com.pdm.ucanet.abstractEntities;

import java.lang.*;
import java.util.ArrayList;

/**
 * Created by frank101m on 5/16/17.
 */

public class Course {
    private int idCourse;
    private String courseName;
    private ArrayList<Thread> courseThreads;

    public Course(int id, String name){
        this.idCourse = id;
        this.courseName = name;
        courseThreads = new ArrayList<>();
    }


    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Thread> getCourseThreads() {
        return courseThreads;
    }

    public void setCourseThreads(ArrayList<Thread> courseThreads) {
        this.courseThreads = courseThreads;
    }
}
