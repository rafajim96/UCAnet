package com.pdm.ucanet.concreteEntities;

import com.pdm.ucanet.abstractEntities.Course;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by frank101m on 5/16/17.
 */

public class User {
    private String name, career, faculty;
    private String username;

    private ArrayList<Course> courses;
    private ArrayList<String> coursesNames;
    private boolean photo = false;


    public User(String username){
        this.username = username;
        courses = new ArrayList<>();
        coursesNames = new ArrayList<>();
    }

    public User(String name, String career, String faculty, String username,
                ArrayList<Course> courses, boolean photo) {
        this.name = name;
        this.career = career;
        this.faculty = faculty;
        this.username = username;
        this.courses = courses;
        this.photo = photo;
        this.coursesNames = new ArrayList<>();

        for (Course courseName: courses ) {
            coursesNames.add(courseName.getCourseName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<String> getCoursesNames(){
        return coursesNames;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

}
