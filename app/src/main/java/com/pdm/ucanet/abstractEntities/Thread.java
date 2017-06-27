package com.pdm.ucanet.abstractEntities;

import java.util.ArrayList;

/**
 * Created by Crash on 04/06/2017.
 */

public class Thread {
    private int id;
    private String title;
    private ArrayList<Post> posts;
    private String user, date;

    public Thread(int id, String name){
        this.id = id;
        this.title = name;
    }

    public Thread(int id, String title, String user, String date) {
        this.id = id;
        this.title = title;

        this.user = user;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
