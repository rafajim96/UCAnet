package com.pdm.ucanet.abstractEntities;

import java.util.ArrayList;

/**
 * Created by Crash on 04/06/2017.
 */

public class Thread {
    private int id;
    private String title;
    private ArrayList<Post> posts;

    public Thread(int id, String name){
        this.id = id;
        this.title = name;
        //CREATE ARRAYLIST OF POSTS
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
}
