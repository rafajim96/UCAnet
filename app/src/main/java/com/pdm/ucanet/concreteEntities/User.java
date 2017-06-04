package com.pdm.ucanet.concreteEntities;

import com.pdm.ucanet.abstractEntities.Course;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by frank101m on 5/16/17.
 */

public class User {
    private String firstName, id, restOfTheName;
    private Calendar birth;

    //TEST ATRIBUTES
    private String username;
    private String password;
    private ArrayList<Course> courses;

    public User(String username, String pass){
        this.username = username;
        this.password = pass;

        courses = new ArrayList<>(3);

        Course a = new Course(1, "mate1");
        Course b = new Course(2, "turismo");
        Course c = new Course(3, "analisis");

        courses.add(a);
        courses.add(b);
        courses.add(c);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
