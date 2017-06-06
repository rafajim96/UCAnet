package com.pdm.ucanet.abstractEntities;

/**
 * Created by Crash on 04/06/2017.
 */

public class Post {
    private String content;

    public Post(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
