package com.pdm.ucanet.abstractEntities;

/**
 * Created by frank101m on 5/16/17.
 */

public class Course {
    private int idCourse;
    private String courseName;

    public Course(int id, String name){
        this.idCourse = id;
        this.courseName = name;
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
}
